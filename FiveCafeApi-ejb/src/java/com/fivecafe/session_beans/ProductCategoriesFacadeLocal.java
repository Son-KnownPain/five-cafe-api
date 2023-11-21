/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.ProductCategories;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface ProductCategoriesFacadeLocal {

    void create(ProductCategories productCategories);

    void edit(ProductCategories productCategories);

    void remove(ProductCategories productCategories);

    ProductCategories find(Object id);

    List<ProductCategories> findAll();

    List<ProductCategories> findRange(int[] range);

    int count();
    
}
