package com.fivecafe.entities;

import com.fivecafe.entities.BillDetails;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-15T13:39:42")
@StaticMetamodel(BDStatuses.class)
public class BDStatuses_ { 

    public static volatile SingularAttribute<BDStatuses, Integer> bDStatusID;
    public static volatile SingularAttribute<BDStatuses, String> bDStatusValue;
    public static volatile CollectionAttribute<BDStatuses, BillDetails> billDetailsCollection;

}