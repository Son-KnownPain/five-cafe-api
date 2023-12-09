package com.fivecafe.session_beans;

import com.fivecafe.entities.Employees;
import com.fivecafe.entities.Roles;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class EmployeesFacade extends AbstractFacade<Employees> implements EmployeesFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeesFacade() {
        super(Employees.class);
    }
    
    @Override
    public Employees findByUsername(String username) {
        Query query = em.createNamedQuery("Employees.findByUsername", Employees.class);
        query.setParameter("username", username);
        try {
            return (Employees) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<Employees> searchEmployees(String keyword, Roles role) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employees> criteriaQuery = criteriaBuilder.createQuery(Employees.class);
        Root<Employees> root = criteriaQuery.from(Employees.class);
        
        Predicate combinedPredicate = criteriaBuilder.conjunction();

        if (!keyword.matches("^[0-9]{10}$")) {
            Path<String> namePath = root.get("name");
            Predicate likePredicate = criteriaBuilder.like(namePath, "%" + keyword + "%");
            combinedPredicate = criteriaBuilder.and(combinedPredicate, likePredicate);
        } else {
            Predicate phonePredicate = criteriaBuilder.equal(root.get("phone"), keyword);
            combinedPredicate = criteriaBuilder.and(combinedPredicate, phonePredicate);
        }
        
        if (role != null) {
            Predicate whereRoleID = criteriaBuilder.equal(root.get("roleID"), role);
            combinedPredicate = criteriaBuilder.and(combinedPredicate, whereRoleID);
        }
        
        criteriaQuery.where(combinedPredicate);

        // Execute the query
        List<Employees> resultList = em.createQuery(criteriaQuery).getResultList();

        return resultList;
    }
}
