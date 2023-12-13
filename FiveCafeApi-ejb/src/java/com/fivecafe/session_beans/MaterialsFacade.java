/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.MaterialCategories;
import com.fivecafe.entities.Materials;
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
public class MaterialsFacade extends AbstractFacade<Materials> implements MaterialsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterialsFacade() {
        super(Materials.class);
    }
    
    @Override
    public List<Materials> searchMaterialByCategoryAndName(MaterialCategories materialCategoriesId, String matName) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Materials> criteriaQuery = criteriaBuilder.createQuery(Materials.class);
        Root<Materials> root = criteriaQuery.from(Materials.class);
        
        // Tạo đối tượng Predicate để thêm điều kiện tìm kiếm
        Predicate predicate = criteriaBuilder.conjunction();
        
        // Thêm điều kiện tìm kiếm theo danh mục nguyên liệu
        if (materialCategoriesId != null) {
            Predicate whereProCatID = criteriaBuilder.equal(root.get("materialCategoryID"), materialCategoriesId);
            predicate = criteriaBuilder.and(predicate, whereProCatID);
        }
        
        // Thêm điều kiện tìm kiếm theo tên nguyên liệu
        if (matName != null && !matName.isEmpty()) {
            Path<String> namePath = root.get("name");
            Predicate likePredicate = criteriaBuilder.like(namePath, "%" + matName + "%");
            predicate = criteriaBuilder.and(predicate, likePredicate);
        }
        
        criteriaQuery.where(predicate);
        List<Materials> resultList = em.createQuery(criteriaQuery).getResultList();
        
        return resultList;
    }
}
