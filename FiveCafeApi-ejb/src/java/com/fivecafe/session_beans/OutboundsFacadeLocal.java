/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Outbounds;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface OutboundsFacadeLocal {

    void create(Outbounds outbounds);

    void edit(Outbounds outbounds);

    void remove(Outbounds outbounds);

    Outbounds find(Object id);

    List<Outbounds> findAll();

    List<Outbounds> findRange(int[] range);

    int count();
    
}
