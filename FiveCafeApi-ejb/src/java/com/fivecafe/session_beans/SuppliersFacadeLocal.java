/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Suppliers;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface SuppliersFacadeLocal {

    void create(Suppliers suppliers);

    void edit(Suppliers suppliers);

    void remove(Suppliers suppliers);

    Suppliers find(Object id);

    List<Suppliers> findAll();

    List<Suppliers> findRange(int[] range);

    int count();

    public List<Suppliers> searchSuppliers(String keyword);
    
}
