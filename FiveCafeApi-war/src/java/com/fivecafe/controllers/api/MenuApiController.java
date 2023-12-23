package com.fivecafe.controllers.api;

import com.fivecafe.body.product.MenuResponse;
import com.fivecafe.entities.ProductCategories;
import com.fivecafe.entities.Products;
import com.fivecafe.models.responses.DataResponse;
import com.fivecafe.providers.UrlProvider;
import com.fivecafe.session_beans.ProductCategoriesFacadeLocal;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.Menu.PREFIX)
public class MenuApiController {

    ProductCategoriesFacadeLocal productCategoriesFacade = lookupProductCategoriesFacadeLocal();

    ProductsFacadeLocal productsFacade = lookupProductsFacadeLocal();
    
    @GetMapping(""+UrlProvider.Menu.SELLING)
    public ResponseEntity<?> selling(HttpServletRequest request) {
        List<ProductCategories> categories = productCategoriesFacade.findAll();
        
        DataResponse<List<MenuResponse>> res = new DataResponse<>();
        
        List<MenuResponse> data = new ArrayList<>();
        
        for (ProductCategories category : categories) {
            List<Products> products = productsFacade.findByCategory(category);
            
            List<MenuResponse.MenuItem> items = new ArrayList<>();
            
            for (Products product : products) {
                items.add(MenuResponse.MenuItem.builder()
                        .image(FileSupport.perfectImg(request, "products", product.getImage()))
                        .name(product.getName())
                        .price(product.getPrice())
                        .build());
            }
            
            data.add(MenuResponse.builder()
                    .categoryName(category.getName())
                    .products(items)
                    .build()
            );
        }
        
        res.setStatus(200);
        res.setSuccess(true);
        res.setMessage("Successfully get menu");
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
