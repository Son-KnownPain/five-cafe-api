/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.EmployeeSalaryDetails;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface EmployeeSalaryDetailsFacadeLocal {

    void create(EmployeeSalaryDetails employeeSalaryDetails);

    void edit(EmployeeSalaryDetails employeeSalaryDetails);

    void remove(EmployeeSalaryDetails employeeSalaryDetails);

    EmployeeSalaryDetails find(Object id);

    List<EmployeeSalaryDetails> findAll();

    List<EmployeeSalaryDetails> findRange(int[] range);

    int count();

    public List<EmployeeSalaryDetails> findByEmpSalaryID(int empSalaryID);
    
}
