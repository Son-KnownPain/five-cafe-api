/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.MaterialCategories;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface MaterialCategoriesFacadeLocal {

    void create(MaterialCategories materialCategories);

    void edit(MaterialCategories materialCategories);

    void remove(MaterialCategories materialCategories);

    MaterialCategories find(Object id);

    List<MaterialCategories> findAll();

    List<MaterialCategories> findRange(int[] range);

    int count();
    
}
