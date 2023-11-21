/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Materials;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface MaterialsFacadeLocal {

    void create(Materials materials);

    void edit(Materials materials);

    void remove(Materials materials);

    Materials find(Object id);

    List<Materials> findAll();

    List<Materials> findRange(int[] range);

    int count();
    
}
