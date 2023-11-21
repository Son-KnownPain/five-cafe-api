package com.fivecafe.entities;

import com.fivecafe.entities.Employees;
import com.fivecafe.entities.Shifts;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-17T16:36:49")
@StaticMetamodel(EmployeeTimeKeepings.class)
public class EmployeeTimeKeepings_ { 

    public static volatile SingularAttribute<EmployeeTimeKeepings, Date> date;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Double> salaryPerHour;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Double> atualHours;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Boolean> isPaid;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Shifts> shiftID;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Integer> timeKeepingID;
    public static volatile SingularAttribute<EmployeeTimeKeepings, Employees> employeeID;

}