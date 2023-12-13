package com.fivecafe.entities;

import com.fivecafe.entities.Materials;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-13T21:53:14")
@StaticMetamodel(MaterialCategories.class)
public class MaterialCategories_ { 

    public static volatile CollectionAttribute<MaterialCategories, Materials> materialsCollection;
    public static volatile SingularAttribute<MaterialCategories, String> name;
    public static volatile SingularAttribute<MaterialCategories, Integer> materialCategoryID;
    public static volatile SingularAttribute<MaterialCategories, String> description;

}