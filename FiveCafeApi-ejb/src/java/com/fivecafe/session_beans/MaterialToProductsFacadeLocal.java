/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.MaterialToProducts;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface MaterialToProductsFacadeLocal {

    void create(MaterialToProducts materialToProducts);

    void edit(MaterialToProducts materialToProducts);

    void remove(MaterialToProducts materialToProducts);

    MaterialToProducts find(Object id);

    List<MaterialToProducts> findAll();

    List<MaterialToProducts> findRange(int[] range);

    int count();
    
}
