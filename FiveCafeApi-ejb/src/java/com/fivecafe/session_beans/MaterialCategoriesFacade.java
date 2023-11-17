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
    public List<MaterialCategories> searchMaterialCategoryByName(String name){
        Query query = em.createNamedQuery("MaterialCategories.findByName", MaterialCategories.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    
}
