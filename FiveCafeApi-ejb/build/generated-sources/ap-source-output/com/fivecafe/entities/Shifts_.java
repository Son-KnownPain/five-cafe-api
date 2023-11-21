package com.fivecafe.entities;

import com.fivecafe.entities.EmployeeTimeKeepings;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-11-20T15:05:28")
@StaticMetamodel(Shifts.class)
public class Shifts_ { 

    public static volatile SingularAttribute<Shifts, Double> salaryPerHour;
    public static volatile SingularAttribute<Shifts, Date> timeFrom;
    public static volatile SingularAttribute<Shifts, Integer> shiftID;
    public static volatile SingularAttribute<Shifts, Date> timeTo;
    public static volatile SingularAttribute<Shifts, String> name;
    public static volatile CollectionAttribute<Shifts, EmployeeTimeKeepings> employeeTimeKeepingsCollection;

}