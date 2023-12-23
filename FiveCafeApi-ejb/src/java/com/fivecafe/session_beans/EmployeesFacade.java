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
        String sqlQuery = "SELECT * FROM Employees WHERE ";
        
        if (!keyword.matches("^[0-9]{1,10}$")) {
            sqlQuery += "[Name] COLLATE Latin1_General_CI_AI LIKE N'%" + keyword + "%'";
        } else {
            sqlQuery += "[Phone] LIKE '%" + keyword + "%'";
        }
        
        if (role != null) {
            sqlQuery += " AND [RoleID] = '" + role.getRoleID() + "'";
        }
        
        Query query = em.createNativeQuery(sqlQuery, Employees.class);

        return query.getResultList();
    }
}
