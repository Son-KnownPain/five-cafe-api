package com.fivecafe.entities;

import com.fivecafe.entities.ImportDetails;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-29T16:04:54")
@StaticMetamodel(Imports.class)
public class Imports_ { 

    public static volatile SingularAttribute<Imports, Date> date;
    public static volatile SingularAttribute<Imports, Integer> importID;
    public static volatile CollectionAttribute<Imports, ImportDetails> importDetailsCollection;

}