/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.BillStatuses;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class BillStatusesFacade extends AbstractFacade<BillStatuses> implements BillStatusesFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BillStatusesFacade() {
        super(BillStatuses.class);
    }
    
    @Override
    public boolean hasAnyToCheckTrue() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        
        Root<BillStatuses> root = criteriaQuery.from(BillStatuses.class);
        
        Path<Boolean> toCheckPath = root.get("toCheck");
        
        Predicate toCheckPredicate = criteriaBuilder.isTrue(toCheckPath);
        
        criteriaQuery.select(criteriaBuilder.count(root)).where(toCheckPredicate);
        
        Long count = em.createQuery(criteriaQuery).getSingleResult();
        
        return count > 0;
    }
    
    @Override
    public BillStatuses getFirstBillStatus() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BillStatuses> criteriaQuery = criteriaBuilder.createQuery(BillStatuses.class);
        
        Root<BillStatuses> root = criteriaQuery.from(BillStatuses.class);
        
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("billStatusID")));
        
        try {
            return em.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public BillStatuses getToCheckBillStatuses() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BillStatuses> criteriaQuery = criteriaBuilder.createQuery(BillStatuses.class);
        
        Root<BillStatuses> root = criteriaQuery.from(BillStatuses.class);
        
        Path<Boolean> toCheckPath = root.get("toCheck");
        
        Predicate toCheckPredicate = criteriaBuilder.isTrue(toCheckPath);
        
        criteriaQuery.where(toCheckPredicate);
        
        try {
            return em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
