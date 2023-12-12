package com.fivecafe.controllers.api;

import com.fivecafe.entities.ProductCategories;
import com.fivecafe.entities.Products;
import com.fivecafe.models.product.CreateProductReq;
import com.fivecafe.models.product.ProductResponse;
import com.fivecafe.models.product.UpdateProductReq;
import com.fivecafe.models.responses.DataResponse;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
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
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Product.PREFIX + "")
public class ProductApiController {

    ProductCategoriesFacadeLocal productCategoriesFacade = lookupProductCategoriesFacadeLocal();

    ProductsFacadeLocal productsFacade = lookupProductsFacadeLocal();

    @GetMapping("" + UrlProvider.Product.ALL)
    public ResponseEntity<DataResponse<List<ProductResponse>>> allPro(HttpServletRequest request) {
        List<Products> allProduct = productsFacade.findAll();
        List<ProductResponse> data = new ArrayList<>();

        for (Products p : allProduct) {
            data.add(
                    ProductResponse.builder()
                            .productID(p.getProductID())
                            .productCategoryID(p.getProductCategoryID().getProductCategoryID())
                            .productCategoryName(p.getProductCategoryID().getName())
                            .name(p.getName())
                            .price(p.getPrice())
                            .isSelling(p.getIsSelling())
                            .image(FileSupport.perfectImg(request, "products", p.getImage()))
                            .build()
            );
        }

        DataResponse<List<ProductResponse>> proResponse = new DataResponse<>();

        proResponse.setSuccess(true);
        proResponse.setStatus(200);
        proResponse.setMessage("Successfully get all product");
        proResponse.setData(data);

        return ResponseEntity.ok(proResponse);
    }

    @PostMapping("" + UrlProvider.Product.STORE)
    public ResponseEntity<StandardResponse> storeProduct(
            @RequestPart(value = "image", required = false) MultipartFile image, 
            @RequestPart(value = "data", required = false) @Valid CreateProductReq createPro_res, 
            HttpSession session, BindingResult br
    ) 
            throws MethodArgumentNotValidException 
    {

        //Validation for image
        if (image == null || image.isEmpty()) {
            br.rejectValue("image", "error.image", "Image is required");
            throw new MethodArgumentNotValidException(null, br);
        }

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

        try {
            // Handle avatar here
            byte[] imageBytes = image.getBytes();
            String originalFileName = image.getOriginalFilename();
            String newImage = FileSupport.saveFile(session.getServletContext().getRealPath("/"), "products", imageBytes, originalFileName);
            pro.setImage(newImage);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(StandardResponse.builder().success(false).status(400).message("Invalid image data").build());
        }

        productsFacade.create(pro);

        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully create new product").build());
    }

    @PostMapping(""+UrlProvider.Product.UPDATE)
    public ResponseEntity<StandardResponse> updateProduct(
            @RequestPart(value = "image", required = false) MultipartFile image, 
            @RequestPart(value = "data", required = false) @Valid UpdateProductReq updatePro_res, 
            HttpSession session, 
            BindingResult br
    ) 
            throws MethodArgumentNotValidException 
    {
        
        ProductCategories proCategory = productCategoriesFacade.find(updatePro_res.getProductCategoryID());

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }

        if (proCategory == null) {
            br.rejectValue("productCategoryID", "error.productCategoryID", "The product category ID does not exist.");
            throw new MethodArgumentNotValidException(null, br);
        }

        Products pro = productsFacade.find(updatePro_res.getProductID());

        if (pro == null) {
            br.rejectValue("productID", "error.productID", "The product ID does not exist.");
            throw new MethodArgumentNotValidException(null, br);
        }

        if (br.hasErrors()) {
            throw new MethodArgumentNotValidException(null, br);
        }
        
        pro.setProductCategoryID(proCategory);
        pro.setName(updatePro_res.getName());
        pro.setPrice(updatePro_res.getPrice());
        pro.setIsSelling(updatePro_res.isSelling());
        
        if (image != null && !image.isEmpty()) {
            try {
               FileSupport.deleteFile(session.getServletContext().getRealPath("/"), "products", pro.getImage());
                
               byte[] imageBytes = image.getBytes();
               String originFileName = image.getOriginalFilename();
               String newImageFileName = FileSupport.saveFile(session.getServletContext().getRealPath("/"), "products", imageBytes, originFileName);
               pro.setImage(newImageFileName);
           } catch (IOException ex) {
               Logger.getLogger(EmployeeApiController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }

        productsFacade.edit(pro);

        return ResponseEntity.ok(StandardResponse.builder().success(true).status(200).message("Successfully edit product").build());

    }

    @DeleteMapping("" + UrlProvider.Product.DELETE)
    public ResponseEntity<?> deleteProduct(@RequestParam("ids") String ids, HttpSession session) {
        String[] idsPC = ids.split(",");

        for (String id : idsPC) {
            int idInt;
            try {
                idInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
            Products product = productsFacade.find(idInt);
            if (product != null) {
                try {
                    FileSupport.deleteFile(session.getServletContext().getRealPath("/"), "products", product.getImage());
                } catch (IOException ex) {
                    Logger.getLogger(EmployeeApiController.class.getName()).log(Level.SEVERE, null, ex);
                }
                productsFacade.remove(product);
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

    @GetMapping("" + UrlProvider.Product.SEARCH)
    public ResponseEntity<DataResponse<List<ProductResponse>>> searchProductByProCatIDAndProName(
            @RequestParam(name = "productCategoryID", defaultValue = "") int productCategoryID,
            @RequestParam(name = "name", defaultValue = "") String name,
            HttpServletRequest request) {

        ProductCategories productCategories = null;
        if(productCategories != null){
            productCategories = productCategoriesFacade.find(productCategoryID);
        }
        
        List<Products> productses = productsFacade.searchProductsByCategoryAndName(productCategories, name);
        
        List<ProductResponse> data = new ArrayList<>();

        for (Products products : productses) {
            data.add(
                    ProductResponse.builder()
                            .productID(products.getProductID())
                            .productCategoryID(products.getProductCategoryID().getProductCategoryID())
                            .name(products.getName())
                            .isSelling(products.getIsSelling())
                            .price(products.getPrice())
                            .image(products.getImage())
                            .build()
            );
        }

        DataResponse<List<ProductResponse>> res = new DataResponse<>();

        res.setSuccess(true);
        res.setStatus(200);
        res.setMessage("Successfully searching product");
        res.setData(data);
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
