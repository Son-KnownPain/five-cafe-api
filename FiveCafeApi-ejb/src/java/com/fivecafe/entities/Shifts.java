/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "Shifts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shifts.findAll", query = "SELECT s FROM Shifts s"),
    @NamedQuery(name = "Shifts.findByShiftID", query = "SELECT s FROM Shifts s WHERE s.shiftID = :shiftID"),
    @NamedQuery(name = "Shifts.findByName", query = "SELECT s FROM Shifts s WHERE s.name = :name"),
    @NamedQuery(name = "Shifts.findBySalary", query = "SELECT s FROM Shifts s WHERE s.salary = :salary"),
    @NamedQuery(name = "Shifts.findByTimeFrom", query = "SELECT s FROM Shifts s WHERE s.timeFrom = :timeFrom"),
    @NamedQuery(name = "Shifts.findByTimeTo", query = "SELECT s FROM Shifts s WHERE s.timeTo = :timeTo")})
public class Shifts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ShiftID")
    private Integer shiftID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Salary")
    private double salary;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TimeFrom")
    @Temporal(TemporalType.TIME)
    private Date timeFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TimeTo")
    @Temporal(TemporalType.TIME)
    private Date timeTo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shiftID")
    private Collection<EmployeeTimeKeepings> employeeTimeKeepingsCollection;

    public Shifts() {
    }

    public Shifts(Integer shiftID) {
        this.shiftID = shiftID;
    }

    public Shifts(Integer shiftID, String name, double salary, Date timeFrom, Date timeTo) {
        this.shiftID = shiftID;
        this.name = name;
        this.salary = salary;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public Integer getShiftID() {
        return shiftID;
    }

    public void setShiftID(Integer shiftID) {
        this.shiftID = shiftID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }

    @XmlTransient
    public Collection<EmployeeTimeKeepings> getEmployeeTimeKeepingsCollection() {
        return employeeTimeKeepingsCollection;
    }

    public void setEmployeeTimeKeepingsCollection(Collection<EmployeeTimeKeepings> employeeTimeKeepingsCollection) {
        this.employeeTimeKeepingsCollection = employeeTimeKeepingsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shiftID != null ? shiftID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shifts)) {
            return false;
        }
        Shifts other = (Shifts) object;
        if ((this.shiftID == null && other.shiftID != null) || (this.shiftID != null && !this.shiftID.equals(other.shiftID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.Shifts[ shiftID=" + shiftID + " ]";
    }
    
}
