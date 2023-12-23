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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
        String sqlQuery = "SELECT * FROM Materials WHERE ";
        if (materialCategoriesId != null) {
            sqlQuery += "MaterialCategoryID = " + materialCategoriesId.getMaterialCategoryID() + " AND ";
        }
        sqlQuery += "[Name] COLLATE Latin1_General_CI_AI LIKE N'%" + matName + "%'";

        Query query = em.createNativeQuery(sqlQuery, Materials.class);

        List<Materials> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<Materials> getMaterialsBelowStockQuantity(int stockQuantityThreshold) {
        // Khoi tao CriteriaBuilder
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        // Tạo CriteriaQuery với kiểu trả về là Materials
        CriteriaQuery<Materials> criteriaQuery = criteriaBuilder.createQuery(Materials.class);

        // Xác định đối tượng gốc (root) và tên bảng
        Root<Materials> root = criteriaQuery.from(Materials.class);
        criteriaQuery.select(root);

        Path quantityInStock = root.get("quantityInStock");
        
        // Tạo tiêu chí (criteria) cho cột QuantityInStock dưới 5
        Predicate condition = criteriaBuilder.lessThan(quantityInStock, stockQuantityThreshold);
        criteriaQuery.where(condition);

        // Tạo truy vấn từ CriteriaQuery
        TypedQuery<Materials> query = em.createQuery(criteriaQuery);

        // Thực thi truy vấn và nhận kết quả
        List<Materials> results = query.getResultList();

        return results;
    }
}
