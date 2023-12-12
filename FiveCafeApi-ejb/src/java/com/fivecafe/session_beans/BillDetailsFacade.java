/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.BillDetails;
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
public class BillDetailsFacade extends AbstractFacade<BillDetails> implements BillDetailsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BillDetailsFacade() {
        super(BillDetails.class);
    }
    
    @Override
    public List<BillDetails> findByBillID(int billID) {
        TypedQuery<BillDetails> query = em.createNamedQuery("BillDetails.findByBillID", BillDetails.class);
        
        query.setParameter("billID", billID);
        
        return query.getResultList();
    }
    
}
