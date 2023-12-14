/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.EmployeeTimeKeepings;
import com.fivecafe.entities.Employees;
import java.text.ParseException;
import java.util.Date;
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
public class EmployeeTimeKeepingsFacade extends AbstractFacade<EmployeeTimeKeepings> implements EmployeeTimeKeepingsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeTimeKeepingsFacade() {
        super(EmployeeTimeKeepings.class);
    }
    
     @Override
    public List<EmployeeTimeKeepings> findByEmployeeID(Employees emp) {
        Query query = em.createNamedQuery("EmployeeTimeKeepings.findByEmployeeID", EmployeeTimeKeepings.class);
        query.setParameter("employeeID", emp);

        return query.getResultList();
    }
    
    @Override
    public List<EmployeeTimeKeepings> searchEmployeeTimeKeepingByDate(Date dateFrom, Date dateTo) throws ParseException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EmployeeTimeKeepings> criteriaQuery = criteriaBuilder.createQuery(EmployeeTimeKeepings.class);
        Root<EmployeeTimeKeepings> root = criteriaQuery.from(EmployeeTimeKeepings.class);

        // Tạo biểu thức cho ngày bắt đầu và kết thúc
        Path<Date> datePath = root.get("date");
        Predicate datePredicate = criteriaBuilder.between(datePath, dateFrom, dateTo);

        // Thêm biểu thức vào câu truy vấn
        criteriaQuery.where(datePredicate);

        // Thực hiện truy vấn
        List<EmployeeTimeKeepings> results = em.createQuery(criteriaQuery).getResultList();

        return results;
    }
}
