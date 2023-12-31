package com.fivecafe.controllers.api;

import com.fivecafe.body.material.CreateMaterial;
import com.fivecafe.body.material.MaterialResponse;
import com.fivecafe.body.material.UpdateAndDeleteMaterial;
import com.fivecafe.entities.MaterialCategories;
import com.fivecafe.entities.MaterialToProducts;
import com.fivecafe.entities.Materials;
import com.fivecafe.entities.Products;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.MaterialCategoriesFacadeLocal;
import com.fivecafe.session_beans.MaterialToProductsFacadeLocal;
import com.fivecafe.session_beans.MaterialsFacadeLocal;
import com.fivecafe.session_beans.ProductsFacadeLocal;
import com.fivecafe.supports.FileSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Material.PREFIX + "")
public class MaterialApiController {

    MaterialToProductsFacadeLocal materialToProductsFacade = lookupMaterialToProductsFacadeLocal();

    MaterialCategoriesFacadeLocal materialCategoriesFacade = lookupMaterialCategoriesFacadeLocal();
    MaterialsFacadeLocal materialsFacade = lookupMaterialsFacadeLocal();

    @GetMapping("" + UrlProvider.Material.ALL)
    public ResponseEntity<DataResponse<List<MaterialResponse>>> all(HttpServletRequest request) {
        List<Materials> allMaterial = materialsFacade.findAll();

        List<MaterialResponse> data = new ArrayList<>();

        for (Materials materials : allMaterial) {
            data.add(MaterialResponse.builder()
                    .materialID(materials.getMaterialID())
                    .materialCategoryID(materials.getMaterialCategoryID().getMaterialCategoryID())
                    .materialCategoryName(materials.getMaterialCategoryID().getName())
                    .name(materials.getName())
                    .unit(materials.getUnit())
                    .quantityInStock(materials.getQuantityInStock())
                    .image(FileSupport.perfectImg(request, "material", materials.getImage()))
                    .build());
        }

        DataResponse<List<MaterialResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all material");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @GetMapping(""+UrlProvider.Material.GETQUANTITYINSTOCK)
    public ResponseEntity<DataResponse<List<MaterialResponse>>> allQuantityInStockBelowFive(HttpServletRequest request){
        List<Materials> allMaterial = materialsFacade.getMaterialsBelowStockQuantity(5);
        
        List<MaterialResponse> data = new ArrayList<>();
        
        // Lập qua tất cả nguyên liệu có số lượng thấp (<5)
        for(Materials materials : allMaterial) {
            // Lấy tất các sản phẩm có dùng nguyên liệu này
            List<MaterialToProducts> matToPros = materialToProductsFacade.findByMaterialID(materials);
            
            // Kiểm tra từng sản phẩm
            // Miễn có 1 sản phẩm còn bán có dùng nguyên liệu này thì đủ điều kiện thông báo
            for (MaterialToProducts matToPro : matToPros) {
                // Nếu đã hợp lệ thì thêm nguyên liệu vào dữ liệu trả về và thoát khỏi vòng lặp kiểm tra
                if (matToPro.getProducts().getIsSelling()) {
                    data.add(MaterialResponse.builder()
                    .materialID(materials.getMaterialID())
                    .materialCategoryID(materials.getMaterialCategoryID().getMaterialCategoryID())
                    .materialCategoryName(materials.getMaterialCategoryID().getName())
                    .name(materials.getName())
                    .unit(materials.getUnit())
                    .quantityInStock(materials.getQuantityInStock())
                    .image(FileSupport.perfectImg(request, "material", materials.getImage()))
                    .build());
                    // Thoát khỏi vòng lặp kiểm tra sản phảm
                    break;
                }
                
            }
        }
        
        DataResponse<List<MaterialResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all quantity in stock < 5");
        res.setData(data);
        return ResponseEntity.ok(res);
    }

    @PostMapping("" + UrlProvider.Material.STORE)
    public ResponseEntity<StandardResponse> store(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @Valid @RequestPart(value = "data", required = false) CreateMaterial reqBody,
            BindingResult br,
            HttpSession session
    ) throws MethodArgumentNotValidException {
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        // Validation for iamge
        if (image == null || image.isEmpty()) {
            br.rejectValue("image", "error.image", "Image is required");
            throw new MethodArgumentNotValidException(null, br);
        }

        MaterialCategories materialCaterogies = materialCategoriesFacade.find(reqBody.getMaterialCategoryID());
        if (materialCaterogies == null) {
            br.rejectValue("materialCategoryID", "error.materialCategoryID", "MaterialCategoryID is not exist");
        }
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        Materials matAdd = new Materials();

        matAdd.setMaterialCategoryID(materialCaterogies);
        matAdd.setName(reqBody.getName());
        matAdd.setUnit(reqBody.getUnit());
        matAdd.setQuantityInStock(reqBody.getQuantityInStock());
        try {
            byte[] imageBytes = image.getBytes();
            String orginFileName = image.getOriginalFilename();
            String newImageFileName = FileSupport.saveFile(session.getServletContext().getRealPath("/"), "material", imageBytes, orginFileName);
            matAdd.setImage(newImageFileName);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeApiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // END-----------
        materialsFacade.create(matAdd);

        return ResponseEntity.ok(
                StandardResponse
                        .builder()
                        .success(true)
                        .status(200)
                        .message("Successfully create new material")
                        .build());
    }

    @PostMapping("" + UrlProvider.Material.UPDATE)
    public ResponseEntity<StandardResponse> update(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @Valid @RequestPart(value = "data", required = false) UpdateAndDeleteMaterial reqBody,
            BindingResult br,
            HttpSession session
    ) throws MethodArgumentNotValidException {
        // Validation for iamge
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        Materials matUpdate = materialsFacade.find(reqBody.getMaterialID());
        MaterialCategories materialCaterogies = materialCategoriesFacade.find(reqBody.getMaterialCategoryID());

        if (materialCaterogies == null) {
            br.rejectValue("materialCategoryID", "error.materialCategoryID", "MaterialCategoryID is not exist");
        }
        if (matUpdate == null) {
            br.rejectValue("materialID", "error.materialID", "The material ID does not exist");
        }
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        matUpdate.setMaterialCategoryID(materialCaterogies);
        matUpdate.setName(reqBody.getName());
        matUpdate.setUnit(reqBody.getUnit());
        matUpdate.setQuantityInStock(reqBody.getQuantityInStock());
        if (image != null && !image.isEmpty()) {

            try {
                FileSupport.deleteFile(session.getServletContext().getRealPath("/"), "material", matUpdate.getImage());

                byte[] imageBytes = image.getBytes();
                String orginFileName = image.getOriginalFilename();
                String newImageFileName = FileSupport.saveFile(session.getServletContext().getRealPath("/"), "material", imageBytes, orginFileName);
                matUpdate.setImage(newImageFileName);

            } catch (IOException ex) {
                Logger.getLogger(EmployeeApiController.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        materialsFacade.edit(matUpdate);

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully update data")
                        .build()
        );
    }

    @DeleteMapping("" + UrlProvider.Material.DELETE)
    public ResponseEntity<?> delete(@RequestParam("ids") String ids) {
        String[] idMate = ids.split(",");

        for (String id : idMate) {
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Materials materials = materialsFacade.find(idInt);
            if (materials != null) {
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

    @GetMapping("" + UrlProvider.Material.SEARCH)
    public ResponseEntity<DataResponse<List<MaterialResponse>>> searchMaterialByNameAndMatCategory(
            @RequestParam(name = "materialCategoryID", defaultValue = "") String materialCategoryID,
            @RequestParam(name = "name", defaultValue = "") String name,
            HttpServletRequest request) {

        MaterialCategories materialCategories = null;
        if (materialCategoryID.length() > 0) {
            try {
                int matCateIDInt = Integer.parseInt(materialCategoryID);
                materialCategories = materialCategoriesFacade.find(matCateIDInt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Materials> materialses = materialsFacade.searchMaterialByCategoryAndName(materialCategories,name);

        List<MaterialResponse> data = new ArrayList<>();

        for (Materials materials : materialses) {
            data.add(
                    MaterialResponse.builder()
                            .materialID(materials.getMaterialID())
                            .materialCategoryID(materials.getMaterialCategoryID().getMaterialCategoryID())
                            .materialCategoryName(materials.getMaterialCategoryID().getName())
                            .name(materials.getName())
                            .unit(materials.getUnit())
                            .quantityInStock(materials.getQuantityInStock())
                            .image(FileSupport.perfectImg(request, "material", materials.getImage()))
                            .build()
            );
        }

        DataResponse<List<MaterialResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully searching material");
        res.setData(data);
        return ResponseEntity.ok(res);
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

    private MaterialToProductsFacadeLocal lookupMaterialToProductsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (MaterialToProductsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/MaterialToProductsFacade!com.fivecafe.session_beans.MaterialToProductsFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

  
}
