/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.EmployeeSalaries;
import com.fivecafe.entities.Employees;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface EmployeeSalariesFacadeLocal {

    void create(EmployeeSalaries employeeSalaries);

    void edit(EmployeeSalaries employeeSalaries);

    void remove(EmployeeSalaries employeeSalaries);

    EmployeeSalaries find(Object id);

    List<EmployeeSalaries> findAll();

    List<EmployeeSalaries> findRange(int[] range);

    int count();

    public List<EmployeeSalaries> searchEmployeeSalariesByDate(Date dateFrom, Date dateTo) throws ParseException;

    public List<EmployeeSalaries> findByEmployeeID(Employees emp);

    
}
