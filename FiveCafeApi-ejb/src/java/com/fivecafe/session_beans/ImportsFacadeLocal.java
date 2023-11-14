/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Imports;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface ImportsFacadeLocal {

    void create(Imports imports);

    void edit(Imports imports);

    void remove(Imports imports);

    Imports find(Object id);

    List<Imports> findAll();

    List<Imports> findRange(int[] range);

    int count();
    
}
