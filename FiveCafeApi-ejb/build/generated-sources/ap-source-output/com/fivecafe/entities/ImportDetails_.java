package com.fivecafe.entities;

import com.fivecafe.entities.ImportDetailsPK;
import com.fivecafe.entities.Imports;
import com.fivecafe.entities.Materials;
import com.fivecafe.entities.Suppliers;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-14T03:09:54")
@StaticMetamodel(ImportDetails.class)
public class ImportDetails_ { 

    public static volatile SingularAttribute<ImportDetails, Double> unitPrice;
    public static volatile SingularAttribute<ImportDetails, Integer> quantity;
    public static volatile SingularAttribute<ImportDetails, Imports> imports;
    public static volatile SingularAttribute<ImportDetails, Suppliers> supplierID;
    public static volatile SingularAttribute<ImportDetails, ImportDetailsPK> importDetailsPK;
    public static volatile SingularAttribute<ImportDetails, Materials> materials;

}