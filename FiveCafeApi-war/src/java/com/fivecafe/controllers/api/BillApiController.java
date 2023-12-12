package com.fivecafe.controllers.api;

import com.fivecafe.body.bills.AddBillDetail;
import com.fivecafe.body.bills.BillDetailsResponse;
import com.fivecafe.body.bills.BillResponse;
import com.fivecafe.body.bills.CreateBill;
import com.fivecafe.body.bills.UpdateBillDetail;
import com.fivecafe.entities.BillDetails;
import com.fivecafe.entities.BillDetailsPK;
import com.fivecafe.entities.BillStatuses;
import com.fivecafe.entities.Bills;
import com.fivecafe.entities.Employees;
import com.fivecafe.entities.ImportDetails;
import com.fivecafe.entities.Products;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.BillDetailsFacadeLocal;
import com.fivecafe.session_beans.BillStatusesFacadeLocal;
import com.fivecafe.session_beans.BillsFacadeLocal;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import com.fivecafe.session_beans.ProductsFacadeLocal;
import com.fivecafe.supports.FileSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Bills.PREFIX)
public class BillApiController {

    ProductsFacadeLocal productsFacade = lookupProductsFacadeLocal();

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();

    BillsFacadeLocal billsFacade = lookupBillsFacadeLocal();

    BillStatusesFacadeLocal billStatusesFacade = lookupBillStatusesFacadeLocal();

    BillDetailsFacadeLocal billDetailsFacade = lookupBillDetailsFacadeLocal();

    @GetMapping("" + UrlProvider.Bills.ALL)
    public ResponseEntity<?> all(HttpServletRequest request) {
        List<Bills> allBills = billsFacade.findAll();
        DataResponse<List<BillResponse>> res = new DataResponse<>();
        List<BillResponse> data = new ArrayList<>();
        for (Bills billItem : allBills) {
            // Handle import details
            List<BillDetailsResponse> listDetail = new ArrayList<>();

            for (BillDetails billDetail : billDetailsFacade.findByBillID(billItem.getBillID())) {
                listDetail.add(
                        BillDetailsResponse.builder()
                                .productID(billDetail.getProducts().getProductID())
                                .name(billDetail.getProducts().getName())
                                .image(FileSupport.perfectImg(request, "products", billDetail.getProducts().getImage()))
                                .unitPrice(billDetail.getUnitPrice())
                                .quantity(billDetail.getQuantity())
                                .build()
                );
            }

            // Add item
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            data.add(
                    BillResponse.builder()
                            .billID(billItem.getBillID())
                            .employeeID(billItem.getEmployeeID().getEmployeeID())
                            .billStatusID(billItem.getBillStatusID().getBillStatusID())
                            .createDate(formatter.format(billItem.getCreatedDate()))
                            .cardCode(billItem.getCardCode())
                            .details(listDetail)
                            .build()
            );
        }

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all bills");
        res.setData(data);

        return ResponseEntity.ok(res);
    }

    @PostMapping("" + UrlProvider.Bills.STORE)
    public ResponseEntity<StandardResponse> store(
            @Valid @RequestBody CreateBill reqBody, BindingResult br
    ) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Validate details
        for (CreateBill.CreateBillDetail detail : reqBody.getDetails()) {
            Products product = productsFacade.find(detail.getProductID());
            if (product == null) {
                br.rejectValue("forError", "error.forError", "Product ID you provided is not exist");
                break;
            }

        }
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        Employees employees = employeesFacade.find(reqBody.getEmployeeID());
        if (employees == null) {
            br.rejectValue("employeeID", "error.employeeID", "Employee ID is not exist");
        }

        BillStatuses billStatuses = billStatusesFacade.find(reqBody.getBillStatusID());
        if (billStatuses == null) {
            br.rejectValue("billStatusID", "error.billStatusID", "billStatus ID is not exist");
        }
        // All params is valid, insert record right now

        Bills billRecord = new Bills();
        billRecord.setEmployeeID(employees);
        billRecord.setBillStatusID(billStatuses);
        billRecord.setCreatedDate(new Date());
        billRecord.setCardCode(reqBody.getCardCode());

        billsFacade.create(billRecord);

        // Create detail
        try {
            for (CreateBill.CreateBillDetail detail : reqBody.getDetails()) {
                Products products = productsFacade.find(detail.getProductID());

                BillDetails billDetailRecord = new BillDetails();

                BillDetailsPK idPK = new BillDetailsPK();
                idPK.setBillID(billRecord.getBillID());
                idPK.setProductID(detail.getProductID());

                billDetailRecord.setBillDetailsPK(idPK);
                billDetailRecord.setBills(billRecord);
                billDetailRecord.setProducts(products);
                billDetailRecord.setUnitPrice(products.getPrice());
                billDetailRecord.setQuantity(detail.getQuantity());

                billDetailsFacade.create(billDetailRecord);
//                
//                material.setQuantityInStock(material.getQuantityInStock() + detail.getQuantity());
                productsFacade.edit(products);
            }
        } catch (Exception e) {
            billsFacade.remove(billRecord);
        }

        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully create new bill and bill details")
                .build();

        return ResponseEntity.ok(res);
    }

    @PostMapping("" + UrlProvider.Bills.STORE_PRO_ITEM)
    public ResponseEntity<?> storeProItem(@Valid @RequestBody AddBillDetail reqBody, BindingResult br
    ) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Validate
        Bills bills = billsFacade.find(reqBody.getBillID());
        if (bills == null) {
            br.rejectValue("billID", "error.billID", "Bill ID you provided is not exist");
        }

        Products products = productsFacade.find(reqBody.getProductID());
        if (products == null) {
            br.rejectValue("productID", "error.productID", "Product ID you provided is not exist");
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        // Valid
        BillDetailsPK idPK = new BillDetailsPK();
        idPK.setBillID(reqBody.getBillID());
        idPK.setProductID(reqBody.getProductID());

        BillDetails newbillDetail = new BillDetails();

        newbillDetail.setBillDetailsPK(idPK);
        newbillDetail.setBills(bills);
        newbillDetail.setProducts(products);
        newbillDetail.setUnitPrice(products.getPrice());
        newbillDetail.setQuantity(reqBody.getQuantity());

        // Update material quantity in stock
//        products.setQuantityInStock(products.getQuantityInStock() + reqBody.getQuantity());
        billDetailsFacade.create(newbillDetail);
        productsFacade.edit(products);

        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully update bill, product item data")
                .build();

        return ResponseEntity.ok(res);
    }
    
     @PutMapping(""+UrlProvider.Bills.UPDATE_PRO_ITEM)
    public ResponseEntity<?> updateProItem(@Valid @RequestBody UpdateBillDetail reqBody, BindingResult br
    ) throws MethodArgumentNotValidException {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Validate
        Bills bills = billsFacade.find(reqBody.getBillID());
        if (bills == null) {
            br.rejectValue("billID", "error.billID", "Bill ID you provided is not exist");
        }
        
        Products products = productsFacade.find(reqBody.getProductID());
        if (products == null) {
            br.rejectValue("productID", "error.productID", "Product ID you provided is not exist");
        }
        
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Valid
        BillDetailsPK idPK = new BillDetailsPK();
        idPK.setBillID(reqBody.getBillID());
        idPK.setProductID(reqBody.getProductID());
        
        BillDetails billDetail = billDetailsFacade.find(idPK);
        
        if (billDetail != null) {
//            // Update material quantity in stock
//            products.setQuantityInStock(material.getQuantityInStock() + (reqBody.getQuantity() - importDetail.getQuantity()));
            
            // Update import detail
            billDetail.setQuantity(reqBody.getQuantity());
            
            billDetailsFacade.edit(billDetail);
        }
        
        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully update bill product item data")
                .build();
        
        return ResponseEntity.ok(res);
    }
    
     @DeleteMapping(""+UrlProvider.Bills.DELETE_PRO_ITEM)
    public ResponseEntity<?> deleteProItem(@RequestParam("productID") int productID, @RequestParam("billID") int billID) {
        
        BillDetailsPK idPK = new BillDetailsPK();
        idPK.setBillID(billID);
        idPK.setProductID(productID);
        
        BillDetails billDetail = billDetailsFacade.find(idPK);
        
        if (billDetail == null) {
            StandardResponse res = new StandardResponse();
            res.setStatus(400);
            res.setSuccess(true);
            res.setMessage("Cannot found bill detail with bill id and product id you provided");

            return ResponseEntity.ok(res);
        }
        
        billDetailsFacade.remove(billDetail);
        
        StandardResponse res = new StandardResponse();
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully delete bill detail");
            
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("" + UrlProvider.Bills.DELETE_BILL)
    public ResponseEntity<?> deleteBills(@RequestParam("ids") String ids) {
        String[] idArr = ids.split(",");

        for (String id : idArr) {
            try {
                Bills bills = billsFacade.find(Integer.parseInt(id));

                if (bills != null) {
                    billsFacade.remove(bills);
                }
            } catch (NumberFormatException e) {

            }
        }

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete bills")
                        .build()
        );
    }

    /////-----------------/////
    private BillDetailsFacadeLocal lookupBillDetailsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (BillDetailsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/BillDetailsFacade!com.fivecafe.session_beans.BillDetailsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private BillStatusesFacadeLocal lookupBillStatusesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (BillStatusesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/BillStatusesFacade!com.fivecafe.session_beans.BillStatusesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private BillsFacadeLocal lookupBillsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (BillsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/BillsFacade!com.fivecafe.session_beans.BillsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private EmployeesFacadeLocal lookupEmployeesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeesFacade!com.fivecafe.session_beans.EmployeesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ProductsFacadeLocal lookupProductsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProductsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ProductsFacade!com.fivecafe.session_beans.ProductsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
