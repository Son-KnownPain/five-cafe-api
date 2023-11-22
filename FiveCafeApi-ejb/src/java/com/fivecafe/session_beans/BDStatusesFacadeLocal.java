/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.BDStatuses;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface BDStatusesFacadeLocal {

    void create(BDStatuses bDStatuses);

    void edit(BDStatuses bDStatuses);

    void remove(BDStatuses bDStatuses);

    BDStatuses find(Object id);

    List<BDStatuses> findAll();

    List<BDStatuses> findRange(int[] range);

    int count();
    
}
