/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Bills;
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
public class BillsFacade extends AbstractFacade<Bills> implements BillsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BillsFacade() {
        super(Bills.class);
    }
    
    @Override
     public List<Bills> getBillByDaterange(Date dateForm, Date dateTo){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bills> cq = cb.createQuery(Bills.class);
        Root<Bills> root = cq.from(Bills.class);
        Path<Date> datePath = root.get("createDate");
        Predicate datePredicate = cb.between(datePath, dateForm, dateTo);
        
        cq.where(datePredicate);
        
        return em.createQuery(cq).getResultList();
        
    }
}
