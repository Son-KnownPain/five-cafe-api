package com.fivecafe.entities;

import com.fivecafe.entities.ImportDetails;
import com.fivecafe.entities.MaterialCategories;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-15T13:39:42")
@StaticMetamodel(Materials.class)
public class Materials_ { 

    public static volatile SingularAttribute<Materials, String> image;
    public static volatile SingularAttribute<Materials, String> unit;
    public static volatile CollectionAttribute<Materials, ImportDetails> importDetailsCollection;
    public static volatile SingularAttribute<Materials, String> name;
    public static volatile SingularAttribute<Materials, MaterialCategories> materialCategoryID;
    public static volatile SingularAttribute<Materials, Integer> materialID;

}