package com.fivecafe.controllers.api;

import com.fivecafe.body.imports.ImportDetailRes;
import com.fivecafe.body.imports.ImportRes;
import com.fivecafe.entities.ImportDetails;
import com.fivecafe.entities.Imports;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.ImportDetailsFacadeLocal;
import com.fivecafe.session_beans.ImportsFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Import.PREFIX)
public class ImportApiController {

    ImportsFacadeLocal importsFacade = lookupImportsFacadeLocal();
    
    
    @GetMapping(""+UrlProvider.Import.ALL)
    public ResponseEntity<?> all() {
        List<Imports> allImports = importsFacade.findAll();
        DataResponse<List<ImportRes>> res  = new DataResponse<>();
        List<ImportRes> data = new ArrayList<>();
        for (Imports importItem : allImports) {
            // Handle import details
            List<ImportDetailRes> listDetail = new ArrayList<>();
            
            for (ImportDetails importDetail : importItem.getImportDetailsCollection()) {
                listDetail.add(
                    ImportDetailRes.builder()
                            .materialID(importDetail.getMaterials().getMaterialID())
                            .materialName(importDetail.getMaterials().getName())
                            .unit(importDetail.getMaterials().getUnit())
                            .materialImage(importDetail.getMaterials().getImage())
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
            data.add(
                ImportRes.builder()
                    .importID(importItem.getImportID())
                    .importDate(importItem.getDate())
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

    private ImportsFacadeLocal lookupImportsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ImportsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ImportsFacade!com.fivecafe.session_beans.ImportsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
