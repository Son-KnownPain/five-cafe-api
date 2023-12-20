/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fivecafe.session_beans;

import com.fivecafe.entities.EmployeeTimeKeepings;
import java.text.ParseException;
import java.util.Date;
import com.fivecafe.entities.Employees;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ADMIN
 */
@Local
public interface EmployeeTimeKeepingsFacadeLocal {

    void create(EmployeeTimeKeepings employeeTimeKeepings);

    void edit(EmployeeTimeKeepings employeeTimeKeepings);

    void remove(EmployeeTimeKeepings employeeTimeKeepings);

    EmployeeTimeKeepings find(Object id);

    List<EmployeeTimeKeepings> findAll();

    List<EmployeeTimeKeepings> findRange(int[] range);

    int count();

    public List<EmployeeTimeKeepings> searchEmployeeTimeKeepingByDate(Date dateFrom, Date dateTo) throws ParseException;
    
    public List<EmployeeTimeKeepings> findByEmployeeID(Employees emp);
    
}
