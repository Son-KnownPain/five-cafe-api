package com.fivecafe.entities;

import com.fivecafe.entities.EmployeeSalaryDetails;
import com.fivecafe.entities.Employees;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-15T01:05:37")
@StaticMetamodel(EmployeeSalaries.class)
public class EmployeeSalaries_ { 

    public static volatile SingularAttribute<EmployeeSalaries, Date> date;
    public static volatile CollectionAttribute<EmployeeSalaries, EmployeeSalaryDetails> employeeSalaryDetailsCollection;
    public static volatile SingularAttribute<EmployeeSalaries, Integer> employeeSalaryID;
    public static volatile SingularAttribute<EmployeeSalaries, Employees> employeeID;

}