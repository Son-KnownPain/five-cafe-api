/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.MaterialCategories;
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
public class MaterialCategoriesFacade extends AbstractFacade<MaterialCategories> implements MaterialCategoriesFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialCategoriesFacade() {
        super(MaterialCategories.class);
    }
    
    @Override
    public List<MaterialCategories> searchMaterialCategory(String keyword) {
        String sqlQuery = "SELECT * FROM MaterialCategories "
                + "WHERE [Name] COLLATE Latin1_General_CI_AI LIKE N'%" + keyword + "%'";
        
        Query query = em.createNativeQuery(sqlQuery, MaterialCategories.class);

        return query.getResultList();
    }
}
