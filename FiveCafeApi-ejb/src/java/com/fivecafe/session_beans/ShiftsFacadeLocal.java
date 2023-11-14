/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Shifts;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface ShiftsFacadeLocal {

    void create(Shifts shifts);

    void edit(Shifts shifts);

    void remove(Shifts shifts);

    Shifts find(Object id);

    List<Shifts> findAll();

    List<Shifts> findRange(int[] range);

    int count();
    
}
