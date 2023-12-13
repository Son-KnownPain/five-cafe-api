/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Outbounds;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author ADMIN
 */
@Stateless
public class OutboundsFacade extends AbstractFacade<Outbounds> implements OutboundsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OutboundsFacade() {
        super(Outbounds.class);
    }
    
    @Override
    public List<Outbounds> getOutboundsByDaterange(Date dateForm, Date dateTo){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Outbounds> cq = cb.createQuery(Outbounds.class);
        Root<Outbounds> root = cq.from(Outbounds.class);
        Path<Date> datePath = root.get("date");
        Predicate datePredicate = cb.between(datePath, dateForm, dateTo);
        
        cq.where(datePredicate);
        
        return em.createQuery(cq).getResultList();
        
    }

    
}
