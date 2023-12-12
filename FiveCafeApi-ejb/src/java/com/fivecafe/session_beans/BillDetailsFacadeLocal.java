/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.BillDetails;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface BillDetailsFacadeLocal {

    void create(BillDetails billDetails);

    void edit(BillDetails billDetails);

    void remove(BillDetails billDetails);

    BillDetails find(Object id);

    List<BillDetails> findAll();

    List<BillDetails> findRange(int[] range);

    int count();

    public List<BillDetails> findByBillID(int billID);
    
}
