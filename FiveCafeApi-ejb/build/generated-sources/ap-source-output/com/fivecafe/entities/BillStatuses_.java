package com.fivecafe.entities;

import com.fivecafe.entities.Bills;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-23T16:03:46")
@StaticMetamodel(BillStatuses.class)
public class BillStatuses_ { 

    public static volatile CollectionAttribute<BillStatuses, Bills> billsCollection;
    public static volatile SingularAttribute<BillStatuses, Integer> billStatusID;
    public static volatile SingularAttribute<BillStatuses, String> billStatusValue;

}