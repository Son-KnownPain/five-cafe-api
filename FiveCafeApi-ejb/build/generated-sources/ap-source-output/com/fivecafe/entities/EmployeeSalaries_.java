package com.fivecafe.entities;

import com.fivecafe.entities.EmployeeTimeKeepings;
import com.fivecafe.entities.Employees;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-10T22:06:35")
@StaticMetamodel(EmployeeSalaries.class)
public class EmployeeSalaries_ { 

    public static volatile SingularAttribute<EmployeeSalaries, Date> date;
    public static volatile SingularAttribute<EmployeeSalaries, Integer> employeeSalaryID;
    public static volatile CollectionAttribute<EmployeeSalaries, EmployeeTimeKeepings> employeeTimeKeepingsCollection;
    public static volatile SingularAttribute<EmployeeSalaries, Employees> employeeID;

}