/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.controllers.api;

import com.fivecafe.entities.ProductCategories;
import com.fivecafe.entities.Products;
import com.fivecafe.models.product.CreateProductResponse;
import com.fivecafe.models.product.UpdateProductResponse;
import com.fivecafe.models.product.ProductResponse;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.models.responses.InvalidResponse;
import com.fivecafe.models.responses.StandardResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.ProductCategoriesFacadeLocal;
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
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Product.PREFIX + "")
public class ProductApiController {

    ProductCategoriesFacadeLocal productCategoriesFacade = lookupProductCategoriesFacadeLocal();

    ProductsFacadeLocal productsFacade = lookupProductsFacadeLocal();

    @GetMapping("" + UrlProvider.Product.ALL)
    public ResponseEntity<DataResponse<List<ProductResponse>>> allPro() {
        List<Products> allProduct = productsFacade.findAll();
        List<ProductResponse> data = new ArrayList<>();

        for (Products p : allProduct) {
            data.add(ProductResponse.builder().productID(p.getProductID()).productCategoryID(p.getProductCategoryID().getProductCategoryID()).name(p.getName()).price(p.getPrice()).isSelling(p.getIsSelling()).image(p.getImage()).build());
        }

        DataResponse<List<ProductResponse>> proResponse = new DataResponse<>();

        proResponse.setSuccess(true);
        proResponse.setStatus(200);
        proResponse.setMessage("Successfully get all product");
        proResponse.setData(data);

        return ResponseEntity.ok(proResponse);
    }

//    @PostMapping(""+UrlProvider.Product.STORE)
//    public ResponseEntity<StandardResponse> storeProduct(@Valid @RequestBody CreateProductResponse createPro_res, @RequestPart(value = "image", required = false) MultipartFile image, HttpSession session, BindingResult br) throws MethodArgumentNotValidException {
//
//        ProductCategories proCategory = productCategoriesFacade.find(createPro_res.getProductCategoryID());
//
//        if (proCategory == null) {
//            br.rejectValue("productCategoryID", "error.productCategoryID", "The product category ID does not exist.");
//        }
//
//        if (br.hasErrors()) {
//            throw new MethodArgumentNotValidException(null, br);
//        }
//
//        try {
//            
//            Products pro = new Products();
//
//            pro.setProductCategoryID(proCategory);
//            pro.setName(createPro_res.getName());
//            pro.setPrice(createPro_res.getPrice());
//            pro.setIsSelling(createPro_res.isSelling());
//            
//            if (image != null && !image.isEmpty()) {
//                // Handle avatar here
//                byte[] avatarBytes = image.getBytes();
//                String originalFileName = image.getOriginalFilename();
//                String newImage = FileSupport.saveFile(session.getServletContext().getRealPath("/"), "pro", avatarBytes, originalFileName);
//                pro.setImage(newImage);
//            }
//            productsFacade.create(pro);
//
//            return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully create new product").build());
//            
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(StandardResponse.builder().success(false).status(400).message("Invalid image data").build());
//        }
//       
//    }
    @PostMapping("" + UrlProvider.Product.STORE)
    public ResponseEntity<StandardResponse> storeProduct(@Valid @RequestBody CreateProductResponse createPro_res, HttpSession session, BindingResult br) throws MethodArgumentNotValidException {

        ProductCategories proCategory = productCategoriesFacade.find(createPro_res.getProductCategoryID());

        if (proCategory == null) {
            br.rejectValue("productCategoryID", "error.productCategoryID", "The product category ID does not exist.");
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        Products pro = new Products();

        pro.setProductCategoryID(proCategory);
        pro.setName(createPro_res.getName());
        pro.setPrice(createPro_res.getPrice());
        pro.setIsSelling(createPro_res.isSelling());
        pro.setImage(createPro_res.getImage());
        
        productsFacade.create(pro);

        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully create new product").build());

    }
    
    @PutMapping("" + UrlProvider.Product.UPDATE)
    public ResponseEntity<StandardResponse> updateProduct(@Valid @RequestBody UpdateProductResponse updatePro_res, HttpSession session, BindingResult br) throws MethodArgumentNotValidException {

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        Products pro = productsFacade.find(updatePro_res.getProductID());

        if(pro == null){
             br.rejectValue("productID", "error.productID", "The product ID does not exist.");
             throw new MethodArgumentNotValidException(null, br);
        }
        
        ProductCategories proCategory = productCategoriesFacade.find(updatePro_res.getProductCategoryID());
        
        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        if (proCategory == null) {
            br.rejectValue("productCategoryID", "error.productCategoryID", "The product category ID does not exist.");
            throw new MethodArgumentNotValidException(null, br);
        }

        pro.setProductCategoryID(proCategory);
        pro.setName(updatePro_res.getName());
        pro.setPrice(updatePro_res.getPrice());
        pro.setIsSelling(updatePro_res.isSelling());
        pro.setImage(updatePro_res.getImage());
        
        productsFacade.edit(pro);

        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully edit product").build());

    }
    
    @DeleteMapping("" + UrlProvider.ProductCategory.DELETE)
    public ResponseEntity<?> deleteProductCategories(@RequestParam("ids") List<Integer> ids) {
        List<String> errors = new ArrayList<>();
        List<Integer> deletedIds = new ArrayList<>();

        for (int id : ids) {
            Products product = productsFacade.find(id);

            if (product != null) {
                productsFacade.remove(product);
                deletedIds.add(id);
            } else {
                errors.add("The product ID in the path does not exist");

                InvalidResponse res = new InvalidResponse();
                res.setSuccess(false);
                res.setStatus(400);
                res.setMessage("Bad request path ID");
                res.setInvalid(true);
                res.setErrors(errors);

                return ResponseEntity.badRequest().body(res);
            }
        }

        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully delete product")
                        .build()
        );
    }
    
    @GetMapping(""+UrlProvider.Product.SEARCH)
    public ResponseEntity searchProduct(@RequestParam("q") String name){
        List<Products> findProduct = productsFacade.searchProductByName(name);
        List<String> error = new ArrayList<>();
        
        if (findProduct.isEmpty()) {
            error.add("The product name you are looking for does not exist");

            InvalidResponse res = new InvalidResponse();
            res.setSuccess(false);
            res.setStatus(400);
            res.setMessage("The requested product name is invalid");
            res.setInvalid(true);
            res.setErrors(error);

            return ResponseEntity.badRequest().body(res);
        }
        
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .status(200)
                        .message("Successfully search product")
                        .build()
        );       
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
