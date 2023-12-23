/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.ProductCategories;
import com.fivecafe.entities.Products;
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
public class ProductsFacade extends AbstractFacade<Products> implements ProductsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }
    
    @Override
    public List<Products> searchProductByName(String name) {
        Query query = em.createNamedQuery("Products.findByName", Products.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    @Override
    public List<Products> searchProductsByCategoryAndName(ProductCategories productCategoryId, String proName, String selling) {
        String sqlQuery = "SELECT * FROM Products WHERE ";
        if (productCategoryId != null) {
            sqlQuery += "ProductCategoryID = " + productCategoryId.getProductCategoryID() + " AND ";
        }
        sqlQuery += "[Name] COLLATE Latin1_General_CI_AI LIKE N'%" + proName + "%' AND ";
        if (selling != null) {
            sqlQuery += "IsSelling = 1 AND ";
        }
        sqlQuery += "1 = 1"; // Dummy condition to complete the query

        Query query = em.createNativeQuery(sqlQuery, Products.class);

        List<Products> resultList = query.getResultList();
        return resultList;
    }
    
    @Override
    public List<Products> findActiveProducts() {
        String jpql = "SELECT p FROM Products p WHERE p.isSelling = :isSelling";
        
        Query query = em.createQuery(jpql, Products.class);
        query.setParameter("isSelling", true);

        return query.getResultList();
    }
    
    @Override
    public List<Products> findByCategory(ProductCategories category) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Products> criteriaQuery = criteriaBuilder.createQuery(Products.class);
        
        Root<Products> root = criteriaQuery.from(Products.class);
        
        Predicate categoryPredicate = criteriaBuilder.equal(root.get("productCategoryID"), category);
        
        Path<Boolean> isSellingPath = root.get("isSelling");
        Predicate sellingPredicate = criteriaBuilder.isTrue(isSellingPath);
        
        Predicate combinedPredicate = criteriaBuilder.and(categoryPredicate, sellingPredicate);
        
        criteriaQuery.where(combinedPredicate);
        
        return em.createQuery(criteriaQuery).getResultList();
    }
}
