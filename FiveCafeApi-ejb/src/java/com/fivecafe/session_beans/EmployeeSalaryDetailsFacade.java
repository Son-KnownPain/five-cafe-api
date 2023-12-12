/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.EmployeeSalaryDetails;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ADMIN
 */
@Stateless
public class EmployeeSalaryDetailsFacade extends AbstractFacade<EmployeeSalaryDetails> implements EmployeeSalaryDetailsFacadeLocal {

    @PersistenceContext(unitName = "FiveCafeApi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeSalaryDetailsFacade() {
        super(EmployeeSalaryDetails.class);
    }
    
    @Override
    public List<EmployeeSalaryDetails> findByEmpSalaryID(int empSalaryID) {
        TypedQuery<EmployeeSalaryDetails> query = em.createNamedQuery("EmployeeSalaryDetails.findByEmployeeSalaryID", EmployeeSalaryDetails.class);
        
        query.setParameter("employeeSalaryID", empSalaryID);
        
        return query.getResultList();
    }
}
