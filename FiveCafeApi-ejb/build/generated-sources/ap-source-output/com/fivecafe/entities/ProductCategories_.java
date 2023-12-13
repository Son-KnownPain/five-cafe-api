package com.fivecafe.entities;

import com.fivecafe.entities.Products;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-12-10T10:54:39")
@StaticMetamodel(ProductCategories.class)
public class ProductCategories_ { 

    public static volatile SingularAttribute<ProductCategories, Integer> productCategoryID;
    public static volatile CollectionAttribute<ProductCategories, Products> productsCollection;
    public static volatile SingularAttribute<ProductCategories, String> name;
    public static volatile SingularAttribute<ProductCategories, String> description;

}