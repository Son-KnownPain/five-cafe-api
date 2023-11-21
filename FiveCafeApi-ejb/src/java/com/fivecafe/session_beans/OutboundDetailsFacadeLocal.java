/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.OutboundDetails;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface OutboundDetailsFacadeLocal {

    void create(OutboundDetails outboundDetails);

    void edit(OutboundDetails outboundDetails);

    void remove(OutboundDetails outboundDetails);

    OutboundDetails find(Object id);

    List<OutboundDetails> findAll();

    List<OutboundDetails> findRange(int[] range);

    int count();
    
}
