/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.BillStatuses;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface BillStatusesFacadeLocal {

    void create(BillStatuses billStatuses);

    void edit(BillStatuses billStatuses);

    void remove(BillStatuses billStatuses);

    BillStatuses find(Object id);

    List<BillStatuses> findAll();

    List<BillStatuses> findRange(int[] range);

    int count();

    public boolean hasAnyToCheckTrue();

    public BillStatuses getFirstBillStatus();

    public BillStatuses getToCheckBillStatuses();
    
}
