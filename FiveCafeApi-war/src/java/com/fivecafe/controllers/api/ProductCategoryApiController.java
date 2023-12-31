/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.controllers.api;

import com.fivecafe.entities.ProductCategories;
import com.fivecafe.models.poductcategory.CreateProductCategory;
import com.fivecafe.models.poductcategory.UpdateAndDeleteProductCategory;
import com.fivecafe.models.poductcategory.ProductCategoryResponse;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.ProductCategoriesFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.ProductCategory.PREFIX + "")
public class ProductCategoryApiController {

    ProductCategoriesFacadeLocal productCategoriesFacade = lookupProductCategoriesFacadeLocal();

    @GetMapping("" + UrlProvider.ProductCategory.ALL)
    public ResponseEntity<DataResponse<List<ProductCategoryResponse>>> allProCategory() {

        List<ProductCategories> allProCategories = productCategoriesFacade.findAll();
        List<ProductCategoryResponse> data = new ArrayList<>();

        for (ProductCategories pc : allProCategories) {
            data.add(ProductCategoryResponse.builder()
                    .productCategoryID(pc.getProductCategoryID())
                    .name(pc.getName())
                    .description(pc.getDescription())
                    .build());
        }

        DataResponse<List<ProductCategoryResponse>> proCategories = new DataResponse<>();

        proCategories.setSuccess(true);
        proCategories.setStatus(200);
        proCategories.setMessage("Successfully get all product category");
        proCategories.setData(data);

        return ResponseEntity.ok(proCategories);
    }

    @PostMapping("" + UrlProvider.ProductCategory.STORE)
    public ResponseEntity<StandardResponse> storeProCategory(@Valid @RequestBody CreateProductCategory reqBody, BindingResult br) throws MethodArgumentNotValidException {

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        ProductCategories pro_CateNew = new ProductCategories();

        pro_CateNew.setName(reqBody.getName());
        pro_CateNew.setDescription(reqBody.getDescription());

        productCategoriesFacade.create(pro_CateNew);

        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully create new product category").build());
    }

    @PutMapping("" + UrlProvider.ProductCategory.UPDATE)
    public ResponseEntity<StandardResponse> updateProCategory(@Valid @RequestBody UpdateAndDeleteProductCategory reqBody, BindingResult br) throws MethodArgumentNotValidException {

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        ProductCategories pro_CateUpdate = productCategoriesFacade.find(reqBody.getProductCategoryID());

        if (pro_CateUpdate == null) {
            br.rejectValue("productCategoryID", "error.productCategoryID", "The product category ID does not exist");
            throw new MethodArgumentNotValidException(null, br);
        }

        pro_CateUpdate.setName(reqBody.getName());
        pro_CateUpdate.setDescription(reqBody.getDescription());

        productCategoriesFacade.edit(pro_CateUpdate);

        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully updated data").build());
    }

    @DeleteMapping("" + UrlProvider.ProductCategory.DELETE)
    public ResponseEntity<?> deleteProductCategories(@RequestParam("ids") String ids) {
        String[] idsPC = ids.split(",");

        for (String id : idsPC) {
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            ProductCategories productCategories = productCategoriesFacade.find(idInt);
            if (productCategories != null) {
                productCategoriesFacade.remove(productCategories);
            }
        }
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete product category")
                        .build()
        );
    }

    @GetMapping("" + UrlProvider.ProductCategory.SEARCH)
    public ResponseEntity<DataResponse<List<ProductCategoryResponse>>> search(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            HttpServletRequest request) {

        List<ProductCategories> allProCat = productCategoriesFacade.searchProductCategory(keyword);

        List<ProductCategoryResponse> data = new ArrayList<>();

        for (ProductCategories proCat : allProCat) {
            data.add(ProductCategoryResponse.builder()
                    .productCategoryID(proCat.getProductCategoryID())
                    .name(proCat.getName())
                    .description(proCat.getDescription())
                    .build());
        }

        DataResponse<List<ProductCategoryResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully searching product category");
        res.setData(data);
        return ResponseEntity.ok(res);
    }

    private ProductCategoriesFacadeLocal lookupProductCategoriesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProductCategoriesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ProductCategoriesFacade!com.fivecafe.session_beans.ProductCategoriesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
