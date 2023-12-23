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
        String sqlQuery = "SELECT * FROM Suppliers WHERE ";
        if (!keyword.matches("^[0-9]{1,10}$")) {
            sqlQuery += "[ContactName] COLLATE Latin1_General_CI_AI LIKE N'%" + keyword + "%'";
        } else {
            sqlQuery += "[ContactNumber] LIKE '%" + keyword + "%'";
        }

        Query query = em.createNativeQuery(sqlQuery, Suppliers.class);

        List<Suppliers> resultList = query.getResultList();
        
        return resultList;
    }
}
