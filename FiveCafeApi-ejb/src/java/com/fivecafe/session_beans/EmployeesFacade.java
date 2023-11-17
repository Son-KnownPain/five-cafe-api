package com.fivecafe.session_beans;

import com.fivecafe.entities.Employees;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
}
