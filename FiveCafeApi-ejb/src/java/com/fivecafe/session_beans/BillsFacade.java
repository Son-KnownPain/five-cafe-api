/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Bills;
import com.fivecafe.entities.Employees;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public List<Bills> getBillByDaterange(Date dateForm, Date dateTo) throws ParseException{
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bills> cq = cb.createQuery(Bills.class);
        Root<Bills> root = cq.from(Bills.class);
        Path<Date> datePath = root.get("createdDate");
        Predicate datePredicate = cb.between(datePath, dateForm, dateTo);
        
        cq.where(datePredicate);
        
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public List<Bills> findByEmployeeID(Employees emp) {
        Query query = em.createNamedQuery("Bills.findByEmployeeID", Bills.class);
        query.setParameter("employeeID", emp);
        
        return query.getResultList();
    }
}
