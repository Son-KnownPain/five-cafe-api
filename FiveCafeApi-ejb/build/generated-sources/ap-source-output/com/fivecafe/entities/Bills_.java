package com.fivecafe.entities;

import com.fivecafe.entities.BillDetails;
import com.fivecafe.entities.BillStatuses;
import com.fivecafe.entities.Employees;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-29T16:04:54")
@StaticMetamodel(Bills.class)
public class Bills_ { 

    public static volatile SingularAttribute<Bills, Date> createdDate;
    public static volatile CollectionAttribute<Bills, BillDetails> billDetailsCollection;
    public static volatile SingularAttribute<Bills, Integer> billID;
    public static volatile SingularAttribute<Bills, String> cardCode;
    public static volatile SingularAttribute<Bills, BillStatuses> billStatusID;
    public static volatile SingularAttribute<Bills, Integer> employeeID;
    public static volatile SingularAttribute<Bills, Employees> employees;

}