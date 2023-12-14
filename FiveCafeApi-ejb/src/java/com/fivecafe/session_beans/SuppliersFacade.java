/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.Suppliers;
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
public class SuppliersFacade extends AbstractFacade<Suppliers> implements SuppliersFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SuppliersFacade() {
        super(Suppliers.class);
    }
    
    @Override
    public List<Suppliers> searchSuppliers(String keyword) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Suppliers> criteriaQuery = criteriaBuilder.createQuery(Suppliers.class);
        Root<Suppliers> root = criteriaQuery.from(Suppliers.class);
        
        Predicate combinedPredicate = criteriaBuilder.conjunction();

        if (!keyword.matches("^[0-9]{10}$")) {
            Path<String> namePath = root.get("contactName");
            Predicate likePredicate = criteriaBuilder.like(namePath, "%" + keyword + "%");
            combinedPredicate = criteriaBuilder.and(combinedPredicate, likePredicate);
        } else {
            Predicate phonePredicate = criteriaBuilder.equal(root.get("contactNumber"), keyword);
            combinedPredicate = criteriaBuilder.and(combinedPredicate, phonePredicate);
        }
    
        criteriaQuery.where(combinedPredicate);

        // Execute the query
        List<Suppliers> resultList = em.createQuery(criteriaQuery).getResultList();

        return resultList;
    }
}
