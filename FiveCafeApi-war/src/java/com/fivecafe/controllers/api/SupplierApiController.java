package com.fivecafe.controllers.api;

import com.fivecafe.body.supplier.CreateSupplier;
import com.fivecafe.body.supplier.SupplierResponse;
import com.fivecafe.body.supplier.UpdateAndDeleteSuplier;
import com.fivecafe.entities.Suppliers;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.SuppliersFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Supplier.PREFIX + "")
public class SupplierApiController {

    SuppliersFacadeLocal suppliersFacade = lookupSuppliersFacadeLocal();

    @GetMapping(""+UrlProvider.Supplier.ALL)
    public ResponseEntity<DataResponse<List<SupplierResponse>>> allSupplier(){
        
        List<Suppliers> allSup = suppliersFacade.findAll();
        List<SupplierResponse> data = new ArrayList<>();
        
        for(Suppliers supp : allSup){
            data.add(SupplierResponse.builder().supplierID(supp.getSupplierID()).contactName(supp.getContactName()).contactNumber(supp.getContactNumber()).address(supp.getAddress()).build());
        }
        
        DataResponse<List<SupplierResponse>> sup_res = new DataResponse<>();
        
        sup_res.setSuccess(true);
        sup_res.setStatus(200);
        sup_res.setMessage("Successfully get all supplier");
        sup_res.setData(data);
        
        return ResponseEntity.ok(sup_res);
    }
    
    @PostMapping(""+UrlProvider.Supplier.STORE)
    public ResponseEntity<StandardResponse> storeSupplier(@Valid @RequestBody CreateSupplier createReponse, BindingResult br) throws MethodArgumentNotValidException{
        
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Suppliers newSuppliers = new Suppliers();
        
        newSuppliers.setContactName(createReponse.getContactName());
        newSuppliers.setContactNumber(createReponse.getContactNumber());
        newSuppliers.setAddress(createReponse.getAddress());
        
        suppliersFacade.create(newSuppliers);
        
        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully create new supplier").build());
    }
    
    @PutMapping(""+UrlProvider.Supplier.UPDATE)
    public ResponseEntity<StandardResponse> updateSupplier(@Valid @RequestBody UpdateAndDeleteSuplier update_res, BindingResult br) throws MethodArgumentNotValidException{
        
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Suppliers updateSup = suppliersFacade.find(update_res.getSupplierID());
        
        if(updateSup == null){
            br.rejectValue("supplierID", "error.supplierID", "The supplier ID does not exist");
        }
        
        updateSup.setContactName(update_res.getContactName());
        updateSup.setContactNumber(update_res.getContactNumber());
        updateSup.setAddress(update_res.getAddress());
        
        suppliersFacade.edit(updateSup);
        
        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully updated data").build());
    }
    
    @DeleteMapping(""+UrlProvider.Supplier.DELETE)
    public ResponseEntity<?> deleteSupplier(@RequestParam("ids") String ids){
        
        String[] idSupp = ids.split(",");
        
        for(String id: idSupp){
            int idIntSupp;
            try{
                idIntSupp = Integer.parseInt(id);
            }catch(NumberFormatException e){
                e.printStackTrace();
                continue;
            }
            
            Suppliers suppliers = suppliersFacade.find(idIntSupp);
            if(suppliers!=null){
                suppliersFacade.remove(suppliers);
            }
        }
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete supplier")
                        .build()
        );
    }
    
    private SuppliersFacadeLocal lookupSuppliersFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (SuppliersFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/SuppliersFacade!com.fivecafe.session_beans.SuppliersFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
   
    
}
