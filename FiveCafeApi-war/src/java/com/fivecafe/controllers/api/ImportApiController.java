package com.fivecafe.controllers.api;

import com.fivecafe.body.imports.AddImportDetailReq;
import com.fivecafe.body.imports.CreateImportReq;
import com.fivecafe.body.imports.ImportDetailRes;
import com.fivecafe.body.imports.ImportRes;
import com.fivecafe.body.imports.UpdateImportDetailReq;
import com.fivecafe.entities.ImportDetails;
import com.fivecafe.entities.ImportDetailsPK;
import com.fivecafe.entities.Imports;
import com.fivecafe.entities.Materials;
import com.fivecafe.entities.Suppliers;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.ImportDetailsFacadeLocal;
import com.fivecafe.session_beans.ImportsFacadeLocal;
import com.fivecafe.session_beans.MaterialsFacadeLocal;
import com.fivecafe.session_beans.SuppliersFacadeLocal;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Import.PREFIX)
public class ImportApiController {

    ImportDetailsFacadeLocal importDetailsFacade = lookupImportDetailsFacadeLocal();

    SuppliersFacadeLocal suppliersFacade = lookupSuppliersFacadeLocal();

    MaterialsFacadeLocal materialsFacade = lookupMaterialsFacadeLocal();

    ImportsFacadeLocal importsFacade = lookupImportsFacadeLocal();
    
    
    @GetMapping(""+UrlProvider.Import.ALL)
    public ResponseEntity<?> all(HttpServletRequest request) {
        List<Imports> allImports = importsFacade.findAll();
        DataResponse<List<ImportRes>> res  = new DataResponse<>();
        List<ImportRes> data = new ArrayList<>();
        for (Imports importItem : allImports) {
            // Handle import details
            List<ImportDetailRes> listDetail = new ArrayList<>();
            
            for (ImportDetails importDetail : importDetailsFacade.findByImportID(importItem.getImportID())) {
                listDetail.add(
                    ImportDetailRes.builder()
                            .materialID(importDetail.getMaterials().getMaterialID())
                            .materialName(importDetail.getMaterials().getName())
                            .unit(importDetail.getMaterials().getUnit())
                            .materialImage(FileSupport.perfectImg(request, "material", importDetail.getMaterials().getImage()))
                            .supplierID(importDetail.getSupplierID().getSupplierID())
                            .supplierContactName(importDetail.getSupplierID().getContactName())
                            .supplierContactNumber(importDetail.getSupplierID().getContactNumber())
                            .suppplierAddress(importDetail.getSupplierID().getAddress())
                            .unitPrice(importDetail.getUnitPrice())
                            .quantity(importDetail.getQuantity())
                            .build()
                );
            }
            
            // Add item
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            data.add(
                ImportRes.builder()
                    .importID(importItem.getImportID())
                    .importDate(formatter.format(importItem.getDate()))
                    .details(listDetail)
                    .build()
            );
        }
        
        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all imports");
        res.setData(data);
        
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(""+UrlProvider.Import.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateImportReq reqBody, BindingResult br) throws MethodArgumentNotValidException {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Validate details
        for (CreateImportReq.CreateImportDetailReq detail : reqBody.getDetails()) {
            Materials material = materialsFacade.find(detail.getMaterialID());
            
            if (material == null) {
                br.rejectValue("forError", "error.forError", "Material ID you provided is not exist");
                break;
            }
            
            Suppliers supplier = suppliersFacade.find(detail.getSupplierID());
            
            if (supplier == null) {
                br.rejectValue("forError", "error.forError", "Supplier ID you provided is not exist");
                break;
            }
        }
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // All params is valid, insert record right now
        Imports importRecord = new Imports();
        importRecord.setDate(new Date());
        
        importsFacade.create(importRecord);
        
        // Create detail
        try {
            for (CreateImportReq.CreateImportDetailReq detail : reqBody.getDetails()) {
                Materials material = materialsFacade.find(detail.getMaterialID());
                Suppliers supplier = suppliersFacade.find(detail.getSupplierID());
                
                ImportDetails importDetailRecord = new ImportDetails();
                
                ImportDetailsPK idPK = new ImportDetailsPK();
                idPK.setImportID(importRecord.getImportID());
                idPK.setMaterialID(detail.getMaterialID());
                
                importDetailRecord.setImportDetailsPK(idPK);
                importDetailRecord.setImports(importRecord);
                importDetailRecord.setMaterials(material);
                importDetailRecord.setSupplierID(supplier);
                importDetailRecord.setUnitPrice(detail.getUnitPrice());
                importDetailRecord.setQuantity(detail.getQuantity());
                
                importDetailsFacade.create(importDetailRecord);
                
                material.setQuantityInStock(material.getQuantityInStock() + detail.getQuantity());
                materialsFacade.edit(material);
            }
        } catch (Exception e) {
             importsFacade.remove(importRecord);
        }
        
        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully create new import and import details")
                .build();
        
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(""+UrlProvider.Import.STORE_MAT_ITEM)
    public ResponseEntity<?> storeMatItem(@Valid @RequestBody AddImportDetailReq reqBody, BindingResult br) throws MethodArgumentNotValidException {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Validate
        Imports imports = importsFacade.find(reqBody.getImportID());
        if (imports == null) {
            br.rejectValue("importID", "error.importID", "Import ID you provided is not exist");
        }
        
        Materials material = materialsFacade.find(reqBody.getMaterialID());
        if (material == null) {
            br.rejectValue("materialID", "error.materialID", "Material ID you provided is not exist");
        }

        Suppliers supplier = suppliersFacade.find(reqBody.getSupplierID());
        if (supplier == null) {
            br.rejectValue("supplierID", "error.supplierID", "Supplier ID you provided is not exist");
        }
        
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Valid
        ImportDetailsPK idPK = new ImportDetailsPK();
        idPK.setImportID(reqBody.getImportID());
        idPK.setMaterialID(reqBody.getMaterialID());
        
        ImportDetails newImportDetail = new ImportDetails();
        
        newImportDetail.setImportDetailsPK(idPK);
        newImportDetail.setImports(imports);
        newImportDetail.setMaterials(material);
        newImportDetail.setSupplierID(supplier);
        newImportDetail.setUnitPrice(reqBody.getUnitPrice());
        newImportDetail.setQuantity(reqBody.getQuantity());
        
        // Update material quantity in stock
        material.setQuantityInStock(material.getQuantityInStock() + reqBody.getQuantity());

        importDetailsFacade.create(newImportDetail);
        materialsFacade.edit(material);
        
        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully update import material item data")
                .build();
        
        return ResponseEntity.ok(res);
    }
    
    @PutMapping(""+UrlProvider.Import.UPDATE_MAT_ITEM)
    public ResponseEntity<?> updateMatItem(@Valid @RequestBody UpdateImportDetailReq reqBody, BindingResult br) throws MethodArgumentNotValidException {
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Validate
        Imports imports = importsFacade.find(reqBody.getImportID());
        if (imports == null) {
            br.rejectValue("importID", "error.importID", "Import ID you provided is not exist");
        }
        
        Materials material = materialsFacade.find(reqBody.getMaterialID());
        if (material == null) {
            br.rejectValue("materialID", "error.materialID", "Material ID you provided is not exist");
        }

        Suppliers supplier = suppliersFacade.find(reqBody.getSupplierID());
        if (supplier == null) {
            br.rejectValue("supplierID", "error.supplierID", "Supplier ID you provided is not exist");
        }
        
        if (br.hasErrors()) throw new MethodArgumentNotValidException(null, br);
        
        // Valid
        ImportDetailsPK idPK = new ImportDetailsPK();
        idPK.setImportID(reqBody.getImportID());
        idPK.setMaterialID(reqBody.getMaterialID());
        
        ImportDetails importDetail = importDetailsFacade.find(idPK);
        
        if (importDetail != null) {
            // Update material quantity in stock
            material.setQuantityInStock(material.getQuantityInStock() + (reqBody.getQuantity() - importDetail.getQuantity()));
            
            // Update import detail
            importDetail.setSupplierID(supplier);
            importDetail.setUnitPrice(reqBody.getUnitPrice());
            importDetail.setQuantity(reqBody.getQuantity());
            
            importDetailsFacade.edit(importDetail);
            materialsFacade.edit(material);
        }
        
        StandardResponse res = StandardResponse.builder()
                .status(200)
                .success(true)
                .message("Successfully update import material item data")
                .build();
        
        return ResponseEntity.ok(res);
    }
    
    @DeleteMapping(""+UrlProvider.Import.DELETE_MAT_ITEM)
    public ResponseEntity<?> deleteMatItem(@RequestParam("matID") int matID, @RequestParam("impID") int impID) {
        
        ImportDetailsPK idPK = new ImportDetailsPK();
        idPK.setImportID(impID);
        idPK.setMaterialID(matID);
        
        ImportDetails importDetail = importDetailsFacade.find(idPK);
        
        if (importDetail == null) {
            StandardResponse res = new StandardResponse();
            res.setStatus(400);
            res.setSuccess(true);
            res.setMessage("Cannot found import detail with import id and material id you provided");

            return ResponseEntity.badRequest().body(res);
        }
        
        importDetailsFacade.remove(importDetail);
        
        StandardResponse res = new StandardResponse();
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully delete import detail");
            
        return ResponseEntity.ok(res);
    }
    
    @DeleteMapping(""+UrlProvider.Import.DELETE_IMPORT)
    public ResponseEntity<?> deleteImports(@RequestParam("ids") String ids) {
        String[] idArr = ids.split(",");
        
        for (String id : idArr) {
            try {
                Imports imports = importsFacade.find(Integer.parseInt(id));
                
                if (imports != null) {
                    importsFacade.remove(imports);
                }
            } catch (NumberFormatException e) {
                
            }
        }
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete imports")
                        .build()
        );
    }

    private ImportsFacadeLocal lookupImportsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ImportsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ImportsFacade!com.fivecafe.session_beans.ImportsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MaterialsFacadeLocal lookupMaterialsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (MaterialsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/MaterialsFacade!com.fivecafe.session_beans.MaterialsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

    private ImportDetailsFacadeLocal lookupImportDetailsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ImportDetailsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ImportDetailsFacade!com.fivecafe.session_beans.ImportDetailsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
