package com.fivecafe.entities;

import com.fivecafe.entities.Bills;
import com.fivecafe.entities.EmployeeSalaries;
import com.fivecafe.entities.EmployeeTimeKeepings;
import com.fivecafe.entities.Outbounds;
import com.fivecafe.entities.Roles;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-12T12:22:52")
@StaticMetamodel(Employees.class)
public class Employees_ { 

    public static volatile CollectionAttribute<Employees, Outbounds> outboundsCollection;
    public static volatile SingularAttribute<Employees, String> image;
    public static volatile CollectionAttribute<Employees, EmployeeSalaries> employeeSalariesCollection;
    public static volatile SingularAttribute<Employees, String> password;
    public static volatile SingularAttribute<Employees, String> phone;
    public static volatile SingularAttribute<Employees, Roles> roleID;
    public static volatile SingularAttribute<Employees, String> name;
    public static volatile CollectionAttribute<Employees, Bills> billsCollection;
    public static volatile CollectionAttribute<Employees, EmployeeTimeKeepings> employeeTimeKeepingsCollection;
    public static volatile SingularAttribute<Employees, Integer> employeeID;
    public static volatile SingularAttribute<Employees, String> username;

}