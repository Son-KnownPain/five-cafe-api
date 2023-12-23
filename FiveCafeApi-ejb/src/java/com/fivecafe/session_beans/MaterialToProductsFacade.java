/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.MaterialToProducts;
import com.fivecafe.entities.Materials;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author ADMIN
 */
@Stateless
public class MaterialToProductsFacade extends AbstractFacade<MaterialToProducts> implements MaterialToProductsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialToProductsFacade() {
        super(MaterialToProducts.class);
    }
    
    @Override
    public List<MaterialToProducts> findByProductID(int productID) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<MaterialToProducts> criteriaQuery = criteriaBuilder.createQuery(MaterialToProducts.class);
        Root<MaterialToProducts> root = criteriaQuery.from(MaterialToProducts.class);

        // Sử dụng đường dẫn để truy cập thuộc tính trong khóa chính nhóm
        Predicate whereProductIDPredicate = criteriaBuilder.equal(root.get("materialToProductsPK").get("productID"), productID);

        criteriaQuery.select(root).where(whereProductIDPredicate);

        try {
            return em.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<MaterialToProducts> findByMaterialID(Materials materialID) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<MaterialToProducts> criteriaQuery = criteriaBuilder.createQuery(MaterialToProducts.class);
        Root<MaterialToProducts> root = criteriaQuery.from(MaterialToProducts.class);
        
        Predicate materialIPredicate = criteriaBuilder.equal(root.get("materials"), materialID);
        
        criteriaQuery.where(materialIPredicate);
        
        return em.createQuery(criteriaQuery).getResultList();
    }
}
