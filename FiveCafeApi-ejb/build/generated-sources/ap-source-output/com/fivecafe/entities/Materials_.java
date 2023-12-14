package com.fivecafe.entities;

import com.fivecafe.entities.ImportDetails;
import com.fivecafe.entities.MaterialCategories;
import com.fivecafe.entities.MaterialToProducts;
import com.fivecafe.entities.OutboundDetails;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-14T03:09:54")
@StaticMetamodel(Materials.class)
public class Materials_ { 

    public static volatile SingularAttribute<Materials, Integer> quantityInStock;
    public static volatile SingularAttribute<Materials, String> image;
    public static volatile SingularAttribute<Materials, String> unit;
    public static volatile CollectionAttribute<Materials, OutboundDetails> outboundDetailsCollection;
    public static volatile CollectionAttribute<Materials, ImportDetails> importDetailsCollection;
    public static volatile CollectionAttribute<Materials, MaterialToProducts> materialToProductsCollection;
    public static volatile SingularAttribute<Materials, String> name;
    public static volatile SingularAttribute<Materials, MaterialCategories> materialCategoryID;
    public static volatile SingularAttribute<Materials, Integer> materialID;

}