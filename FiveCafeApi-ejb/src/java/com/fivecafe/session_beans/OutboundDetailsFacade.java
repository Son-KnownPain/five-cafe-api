/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.OutboundDetails;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ADMIN
 */
@Stateless
public class OutboundDetailsFacade extends AbstractFacade<OutboundDetails> implements OutboundDetailsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OutboundDetailsFacade() {
        super(OutboundDetails.class);
    }
    
    @Override
    public List<OutboundDetails> findByOutboundID(int outboundID) {
        TypedQuery<OutboundDetails> query = em.createNamedQuery("OutboundDetails.findByOutboundID", OutboundDetails.class);
        
        query.setParameter("outboundID", outboundID);
        
        return query.getResultList();
    }
    
}
