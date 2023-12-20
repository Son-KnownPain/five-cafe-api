package com.fivecafe.entities;

import com.fivecafe.entities.Employees;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-15T14:48:13")
@StaticMetamodel(Roles.class)
public class Roles_ { 

    public static volatile CollectionAttribute<Roles, Employees> employeesCollection;
    public static volatile SingularAttribute<Roles, String> roleID;
    public static volatile SingularAttribute<Roles, String> roleName;

}