/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "EmployeeTimeKeepings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeTimeKeepings.findAll", query = "SELECT e FROM EmployeeTimeKeepings e"),
    @NamedQuery(name = "EmployeeTimeKeepings.findByTimeKeepingID", query = "SELECT e FROM EmployeeTimeKeepings e WHERE e.timeKeepingID = :timeKeepingID"),
    @NamedQuery(name = "EmployeeTimeKeepings.findByDate", query = "SELECT e FROM EmployeeTimeKeepings e WHERE e.date = :date"),
    @NamedQuery(name = "EmployeeTimeKeepings.findBySalary", query = "SELECT e FROM EmployeeTimeKeepings e WHERE e.salary = :salary"),
    @NamedQuery(name = "EmployeeTimeKeepings.findByIsPaid", query = "SELECT e FROM EmployeeTimeKeepings e WHERE e.isPaid = :isPaid")})
public class EmployeeTimeKeepings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TimeKeepingID")
    private Integer timeKeepingID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Salary")
    private double salary;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsPaid")
    private boolean isPaid;
    @ManyToMany(mappedBy = "employeeTimeKeepingsCollection")
    private Collection<EmployeeSalaries> employeeSalariesCollection;
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    @ManyToOne(optional = false)
    private Employees employeeID;
    @JoinColumn(name = "ShiftID", referencedColumnName = "ShiftID")
    @ManyToOne(optional = false)
    private Shifts shiftID;

    public EmployeeTimeKeepings() {
    }

    public EmployeeTimeKeepings(Integer timeKeepingID) {
        this.timeKeepingID = timeKeepingID;
    }

    public EmployeeTimeKeepings(Integer timeKeepingID, Date date, double salary, boolean isPaid) {
        this.timeKeepingID = timeKeepingID;
        this.date = date;
        this.salary = salary;
        this.isPaid = isPaid;
    }

    public Integer getTimeKeepingID() {
        return timeKeepingID;
    }

    public void setTimeKeepingID(Integer timeKeepingID) {
        this.timeKeepingID = timeKeepingID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @XmlTransient
    public Collection<EmployeeSalaries> getEmployeeSalariesCollection() {
        return employeeSalariesCollection;
    }

    public void setEmployeeSalariesCollection(Collection<EmployeeSalaries> employeeSalariesCollection) {
        this.employeeSalariesCollection = employeeSalariesCollection;
    }

    public Employees getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employees employeeID) {
        this.employeeID = employeeID;
    }

    public Shifts getShiftID() {
        return shiftID;
    }

    public void setShiftID(Shifts shiftID) {
        this.shiftID = shiftID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timeKeepingID != null ? timeKeepingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeTimeKeepings)) {
            return false;
        }
        EmployeeTimeKeepings other = (EmployeeTimeKeepings) object;
        if ((this.timeKeepingID == null && other.timeKeepingID != null) || (this.timeKeepingID != null && !this.timeKeepingID.equals(other.timeKeepingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.EmployeeTimeKeepings[ timeKeepingID=" + timeKeepingID + " ]";
    }
    
}
