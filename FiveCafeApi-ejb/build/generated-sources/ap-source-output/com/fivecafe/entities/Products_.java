package com.fivecafe.entities;

import com.fivecafe.entities.BillDetails;
import com.fivecafe.entities.ProductCategories;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-16T15:50:11")
@StaticMetamodel(Products.class)
public class Products_ { 

    public static volatile SingularAttribute<Products, String> image;
    public static volatile SingularAttribute<Products, ProductCategories> productCategoryID;
    public static volatile SingularAttribute<Products, Integer> productID;
    public static volatile SingularAttribute<Products, Double> price;
    public static volatile CollectionAttribute<Products, BillDetails> billDetailsCollection;
    public static volatile SingularAttribute<Products, String> name;
    public static volatile SingularAttribute<Products, Boolean> isSelling;

}