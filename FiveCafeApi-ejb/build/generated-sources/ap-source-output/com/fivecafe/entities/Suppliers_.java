package com.fivecafe.entities;

import com.fivecafe.entities.ImportDetails;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-30T18:44:21")
@StaticMetamodel(Suppliers.class)
public class Suppliers_ { 

    public static volatile SingularAttribute<Suppliers, Integer> supplierID;
    public static volatile SingularAttribute<Suppliers, String> address;
    public static volatile SingularAttribute<Suppliers, String> contactName;
    public static volatile CollectionAttribute<Suppliers, ImportDetails> importDetailsCollection;
    public static volatile SingularAttribute<Suppliers, String> contactNumber;

}