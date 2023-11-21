package com.fivecafe.controllers.api;

import com.fivecafe.body.material.CreateMaterial;
import com.fivecafe.body.material.MaterialResponse;
import com.fivecafe.body.material.UpdateAndDeleteMaterial;
import com.fivecafe.entities.MaterialCategories;
import com.fivecafe.entities.Materials;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.MaterialCategoriesFacadeLocal;
import com.fivecafe.session_beans.MaterialsFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Material.PREFIX + "")
public class MaterialApiController {

    MaterialCategoriesFacadeLocal materialCategoriesFacade = lookupMaterialCategoriesFacadeLocal();
    MaterialsFacadeLocal materialsFacade = lookupMaterialsFacadeLocal();
    
    @GetMapping(""+UrlProvider.Material.ALL)
    public ResponseEntity<DataResponse<List<MaterialResponse>>> all(){
        List<Materials> allMaterial = materialsFacade.findAll();
        
        List<MaterialResponse> data = new ArrayList<>();
        
        for(Materials materials : allMaterial){
            data.add(MaterialResponse.builder()
                    .materialID(materials.getMaterialID())
                    .materialCategoryID(materials.getMaterialCategoryID().getMaterialCategoryID())
                    .name(materials.getName())
                    .unit(materials.getUnit())
                    .image(materials.getImage())
                    .build());
        }
        
        DataResponse<List<MaterialResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all material");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(""+UrlProvider.Material.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateMaterial reqBody, BindingResult br) throws MethodArgumentNotValidException{
       
        MaterialCategories materialCaterogies = materialCategoriesFacade.find(reqBody.getMaterialCategoryID());
        if(materialCaterogies == null){
            br.rejectValue("materialCategoryID", "error.materialCategoryID", "MaterialCategoryID is not exist");
        }
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Materials matAdd = new Materials();
        
        matAdd.setMaterialCategoryID(materialCaterogies);
        matAdd.setName(reqBody.getName());
        matAdd.setUnit(reqBody.getUnit());
        matAdd.setImage(reqBody.getImage());
        
        materialsFacade.create(matAdd);
        
        return ResponseEntity.ok(
                StandardResponse
                        .builder()
                        .success(true)
                        .status(200)
                        .message("Successfully create new material")
                        .build());
    }
    
    @PutMapping(""+UrlProvider.Material.UPDATE)
    public ResponseEntity<StandardResponse> update(
            @Valid @RequestBody UpdateAndDeleteMaterial reqBody, BindingResult br) throws MethodArgumentNotValidException{
        
         MaterialCategories materialCaterogies = materialCategoriesFacade.find(reqBody.getMaterialCategoryID());
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Materials matUpdate = materialsFacade.find(reqBody.getMaterialID());
        
        if(matUpdate == null){
            br.rejectValue("materialID", "error.materialID", "The material ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        
        matUpdate.setMaterialCategoryID(materialCaterogies);
        matUpdate.setName(reqBody.getName());
        matUpdate.setUnit(reqBody.getUnit());
        matUpdate.setImage(reqBody.getImage());
        
        materialsFacade.edit(matUpdate);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update data")
                        .build()
        );
    }
    
     @DeleteMapping(""+UrlProvider.Material.DELETE)
    public ResponseEntity<?> delete(@RequestParam("ids") String ids){
        String[] idMate= ids.split(",");
        
        for (String id : idMate){
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Materials materials= materialsFacade.find(idInt);
            if(materials != null){
                materialsFacade.remove(materials);
            }
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete material")
                        .build()
        );
    }
    
    @GetMapping(""+UrlProvider.Material.SEARCH)
    public ResponseEntity searchMaterialByName(@RequestParam("m") String name){
        List<Materials> foundMatName= materialsFacade.searchMaterialByName(name);
        if(foundMatName.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully search material")
                        .build());
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

    private MaterialCategoriesFacadeLocal lookupMaterialCategoriesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (MaterialCategoriesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/MaterialCategoriesFacade!com.fivecafe.session_beans.MaterialCategoriesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
