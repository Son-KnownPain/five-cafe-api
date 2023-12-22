package com.fivecafe.controllers.api;

import com.fivecafe.body.mattopro.CreateMatToPro;
import com.fivecafe.body.mattopro.MatToProResponse;
import com.fivecafe.body.mattopro.UpdateAndDeleteMatToPro;
import com.fivecafe.entities.MaterialToProducts;
import com.fivecafe.entities.MaterialToProductsPK;
import com.fivecafe.entities.Materials;
import com.fivecafe.entities.Products;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.MaterialToProductsFacadeLocal;
import com.fivecafe.session_beans.MaterialsFacadeLocal;
import com.fivecafe.session_beans.ProductsFacadeLocal;
import com.fivecafe.supports.FileSupport;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.MatToPro.PREFIX)
public class MatToProApiController {

    MaterialToProductsFacadeLocal materialToProductsFacade = lookupMaterialToProductsFacadeLocal();

    MaterialsFacadeLocal materialsFacade = lookupMaterialsFacadeLocal();

    ProductsFacadeLocal productsFacade = lookupProductsFacadeLocal();

    @GetMapping("" + UrlProvider.MatToPro.ALL)
    public ResponseEntity<DataResponse<List<MatToProResponse>>> all(HttpServletRequest request) {
        List<MaterialToProducts> allMatToPro = materialToProductsFacade.findAll();

        List<MatToProResponse> data = new ArrayList<>();

        for (MaterialToProducts matToPro : allMatToPro) {
            data.add(MatToProResponse.builder()
                    .materialID(matToPro.getMaterials().getMaterialID())
                    .materialName(matToPro.getMaterials().getName())
                    .image(FileSupport.perfectImg(request, "material", matToPro.getMaterials().getImage()))
                    .productID(matToPro.getProducts().getProductID())
                    .description(matToPro.getDescription())
                    .build());
        }

        DataResponse<List<MatToProResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all material to product");
        res.setData(data);
        return ResponseEntity.ok(res);
    }
    
    @GetMapping("" + UrlProvider.MatToPro.BY_PRODUCT_ID)
    public ResponseEntity<DataResponse<List<MatToProResponse>>> byProductID(@PathVariable("id") int productID) {
        List<MaterialToProducts> allMatToPro = materialToProductsFacade.findByProductID(productID);

        List<MatToProResponse> data = new ArrayList<>();

        for (MaterialToProducts matToPro : allMatToPro) {
            data.add(MatToProResponse.builder()
                    .materialID(matToPro.getMaterials().getMaterialID())
                    .materialName(matToPro.getMaterials().getName())
                    .productID(matToPro.getProducts().getProductID())
                    .description(matToPro.getDescription())
                    .build());
        }

        DataResponse<List<MatToProResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully get all material to product");
        res.setData(data);
        return ResponseEntity.ok(res);
    }

    @PostMapping("" + UrlProvider.MatToPro.STORE)
    public ResponseEntity<StandardResponse> store(@Valid @RequestBody CreateMatToPro reqBody, BindingResult br)
            throws MethodArgumentNotValidException {
        Materials materials = materialsFacade.find(reqBody.getMaterialID());
        if (materials == null) {
            br.rejectValue("materialID", "error.materialID", "Material ID is not exist");
        }

        Products products = productsFacade.find(reqBody.getProductID());
        if (products == null) {
            br.rejectValue("productID", "error.productID", "Product ID is not exist");
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        MaterialToProducts mTPNew = new MaterialToProducts();

        MaterialToProductsPK idPK = new MaterialToProductsPK();
        idPK.setMaterialID(reqBody.getMaterialID());
        idPK.setProductID(reqBody.getProductID());

        mTPNew.setMaterialToProductsPK(idPK);
        mTPNew.setMaterials(materials);
        mTPNew.setProducts(products);
        mTPNew.setDescription(reqBody.getDescription());

        materialToProductsFacade.create(mTPNew);

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully create new Material To Products")
                        .build()
        );
    }

    @PutMapping("" + UrlProvider.MatToPro.UPDATE)
    public ResponseEntity<StandardResponse> updateMatToPro(@Valid @RequestBody UpdateAndDeleteMatToPro reqBody, BindingResult br
    ) throws MethodArgumentNotValidException {

        Materials matUpdate = materialsFacade.find(reqBody.getMaterialID());
        Products proUpdate = productsFacade.find(reqBody.getProductID());

        MaterialToProductsPK idPK = new MaterialToProductsPK();
        idPK.setMaterialID(reqBody.getMaterialID());
        idPK.setProductID(reqBody.getProductID());

        MaterialToProducts mTPUpdate = materialToProductsFacade.find(idPK);

        if (proUpdate == null) {
            br.rejectValue("productID", "error.productID", "The product ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        if (matUpdate == null) {
            br.rejectValue("materialID", "error.materialID", "The material ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }
        
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        mTPUpdate.setMaterialToProductsPK(idPK);
        mTPUpdate.setMaterials(matUpdate);
        mTPUpdate.setProducts(proUpdate);
        mTPUpdate.setDescription(reqBody.getDescription());

        materialToProductsFacade.edit(mTPUpdate);

        return ResponseEntity.ok(StandardResponse
                .builder()
                .success(true)
                .status(200)
                .message("Successfully updated data").build());
    }
    
     @DeleteMapping(""+UrlProvider.MatToPro.DELETE)
    public ResponseEntity<?> deleteProItem(@RequestParam("productID") int productID, @RequestParam("materialID") int materialID) {
        
        MaterialToProductsPK idPK = new MaterialToProductsPK();
        idPK.setMaterialID(materialID);
        idPK.setProductID(productID);
        
        MaterialToProducts materialToProducts= materialToProductsFacade.find(idPK);
        if (materialToProducts == null) {
            StandardResponse res = new StandardResponse();
            res.setStatus(400);
            res.setSuccess(true);
            res.setMessage("Cannot found material to product");

            return ResponseEntity.ok(res);
        }
        materialToProductsFacade.remove(materialToProducts);
        
        StandardResponse res = new StandardResponse();
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully delete material to product");
            
        return ResponseEntity.ok(res);
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

    private MaterialsFacadeLocal lookupMaterialsFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (MaterialsFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/MaterialsFacade!com.fivecafe.session_beans.MaterialsFacadeLocal");
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
