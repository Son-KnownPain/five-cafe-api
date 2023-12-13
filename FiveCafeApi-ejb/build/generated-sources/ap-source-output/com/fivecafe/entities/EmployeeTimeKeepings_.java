package com.fivecafe.entities;

import com.fivecafe.entities.EmployeeSalaryDetails;
import com.fivecafe.entities.Employees;
import com.fivecafe.entities.Shifts;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-14T00:01:17")
@StaticMetamodel(EmployeeTimeKeepings.class)
public class EmployeeTimeKeepings_ { 

    public static volatile SingularAttribute<EmployeeTimeKeepings, Date> date;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Boolean> isPaid;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Shifts> shiftID;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Integer> timeKeepingID;
    public static volatile CollectionAttribute<EmployeeTimeKeepings, EmployeeSalaryDetails> employeeSalaryDetailsCollection;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Employees> employeeID;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Double> salary;

}