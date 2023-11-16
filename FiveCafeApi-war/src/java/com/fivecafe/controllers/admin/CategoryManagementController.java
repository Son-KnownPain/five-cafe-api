///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.fivecafe.controllers.admin;
//
//import com.fivecafe.entities.ProductCategories;
//import com.fivecafe.providers.UrlProvider;
//import com.fivecafe.session_beans.ProductCategoriesFacadeLocal;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping(UrlProvider.API_PREFIX + UrlProvider.ProductCategory.PREFIX + "")
//public class CategoryManagementController {
//
//    ProductCategoriesFacadeLocal productCategoriesFacade = lookupProductCategoriesFacadeLocal();
//
//    @GetMapping({UrlProvider.ProductCategory.INDEX1, UrlProvider.ProductCategory.INDEX2, UrlProvider.ProductCategory.INDEX3})
//    public String index(Model model){
//        List<ProductCategories> productCategories = productCategoriesFacade.findAll();
//        model.addAttribute("productCategories", productCategories);
//
//        return "/admin/category/index";
//    }
//    
//    private ProductCategoriesFacadeLocal lookupProductCategoriesFacadeLocal() {
//        try {
//            Context c = new InitialContext();
//            return (ProductCategoriesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/ProductCategoriesFacade!com.fivecafe.session_beans.ProductCategoriesFacadeLocal");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }
//    
//}
