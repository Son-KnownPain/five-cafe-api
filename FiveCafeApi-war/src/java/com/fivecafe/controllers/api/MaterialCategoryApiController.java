package com.fivecafe.controllers.api;

import com.fivecafe.body.materialcategory.CreateMaterialCaterogy;
import com.fivecafe.body.materialcategory.MaterialCategoryResponse;
import com.fivecafe.body.materialcategory.UpdateAndDeleteMaterialCategory;
import com.fivecafe.entities.MaterialCategories;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.InvalidResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.MaterialCategoriesFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.MaterialCategory.PREFIX + "")
public class MaterialCategoryApiController {

    MaterialCategoriesFacadeLocal materialCategoriesFacade = lookupMaterialCategoriesFacadeLocal();
    
    @GetMapping(""+UrlProvider.MaterialCategory.ALL)
    public ResponseEntity<DataResponse<List<MaterialCategoryResponse>>> all(){
        List<MaterialCategories> allMaterialCategories = materialCategoriesFacade.findAll();
        
        List<MaterialCategoryResponse> data = new ArrayList<>();
        
        for(MaterialCategories materialCategories : allMaterialCategories){
            data.add(MaterialCategoryResponse.builder()
                    .materialCategoryID(materialCategories.getMaterialCategoryID())
                    .name(materialCategories.getName())
                    .description(materialCategories.getDescription())
                    .build());
        }
        
        DataResponse<List<MaterialCategoryResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all material category");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @PostMapping(""+UrlProvider.MaterialCategory.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateMaterialCaterogy reqBody, BindingResult br) throws MethodArgumentNotValidException{
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        MaterialCategories matCategoryAdd = new MaterialCategories();
        
        matCategoryAdd.setName(reqBody.getName());
        matCategoryAdd.setDescription(reqBody.getDescription());
        
        materialCategoriesFacade.create(matCategoryAdd);
        
        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully create new material category").build());
    }
    
    
    @PutMapping(""+UrlProvider.MaterialCategory.UPDATE)
    public ResponseEntity<StandardResponse> update(@Valid @RequestBody UpdateAndDeleteMaterialCategory reqBody, BindingResult br) throws MethodArgumentNotValidException{
        if(br.hasErrors()){
            throw new MethodArgumentNotValidException(null, br);
        }
        
        MaterialCategories matCateUpdate = materialCategoriesFacade.find(reqBody.getMaterialCategoryID());
        
        if(matCateUpdate == null){
            br.rejectValue("materialCaterogyID", "error.materialCategoryID", "The material category ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        
        matCateUpdate.setName(reqBody.getName());
        matCateUpdate.setDescription(reqBody.getDescription());
        
        materialCategoriesFacade.edit(matCateUpdate);
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update data")
                        .build()
        );
    }
    
    @DeleteMapping(""+UrlProvider.MaterialCategory.DELETE)
    public ResponseEntity<?> delete(@RequestParam("ids") String ids){
        String[] idMat= ids.split(",");
        
        for (String id : idMat){
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            MaterialCategories materialCategories= materialCategoriesFacade.find(idInt);
            if(materialCategories != null){
                materialCategoriesFacade.remove(materialCategories);
            }
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete material category")
                        .build()
        );

    }
    
@GetMapping("" + UrlProvider.MaterialCategory.SEARCH)
    public ResponseEntity<DataResponse<List<MaterialCategoryResponse>>> search(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            HttpServletRequest request) {

        List<MaterialCategories> allMatCat = materialCategoriesFacade.searchMaterialCategory(keyword);

        List<MaterialCategoryResponse> data = new ArrayList<>();

        for (MaterialCategories matPro : allMatCat) {
            data.add(MaterialCategoryResponse.builder()
                    .materialCategoryID(matPro.getMaterialCategoryID())
                    .name(matPro.getName())
                    .description(matPro.getDescription())
                    .build());
        }

        DataResponse<List<MaterialCategoryResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully searching material category");
        res.setData(data);
        return ResponseEntity.ok(res);
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
