package com.fivecafe.entities;

import com.fivecafe.entities.BDStatuses;
import com.fivecafe.entities.BillDetailsPK;
import com.fivecafe.entities.Bills;
import com.fivecafe.entities.Products;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-17T11:03:18")
@StaticMetamodel(BillDetails.class)
public class BillDetails_ { 

    public static volatile SingularAttribute<BillDetails, Double> unitPrice;
    public static volatile SingularAttribute<BillDetails, BDStatuses> bDStatusID;
    public static volatile SingularAttribute<BillDetails, BillDetailsPK> billDetailsPK;
    public static volatile SingularAttribute<BillDetails, Integer> quantity;
    public static volatile SingularAttribute<BillDetails, Bills> bills;
    public static volatile SingularAttribute<BillDetails, Products> products;

}