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
    public List<Products> searchProductsByCategoryAndName(ProductCategories productCategoryId, String proName) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Products> criteriaQuery = criteriaBuilder.createQuery(Products.class);
        Root<Products> productRoot = criteriaQuery.from(Products.class);
        
        // Tạo đối tượng Predicate để thêm điều kiện tìm kiếm
        Predicate predicate = criteriaBuilder.conjunction();
        
        // Thêm điều kiện tìm kiếm theo danh mục sản phẩm
        if (productCategoryId != null) {
            Predicate whereProCatID = criteriaBuilder.equal(productRoot.get("productCategoryID"), productCategoryId);
            predicate = criteriaBuilder.and(predicate, whereProCatID);
        }
        
        // Thêm điều kiện tìm kiếm theo tên sản phẩm
        if (proName != null && !proName.isEmpty()) {
            Path<String> namePath = productRoot.get("name");
            Predicate likePredicate = criteriaBuilder.like(namePath, "%" + proName + "%");
            predicate = criteriaBuilder.and(predicate, likePredicate);
        }
        
        criteriaQuery.where(predicate);
        List<Products> resultList = em.createQuery(criteriaQuery).getResultList();
        
        return resultList;
    }
}
