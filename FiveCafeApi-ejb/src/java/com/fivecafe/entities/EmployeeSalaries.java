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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "EmployeeSalaries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeSalaries.findAll", query = "SELECT e FROM EmployeeSalaries e"),
    @NamedQuery(name = "EmployeeSalaries.findByEmployeeID", query = "SELECT e FROM EmployeeSalaries e WHERE e.employeeID = :employeeID"),
    @NamedQuery(name = "EmployeeSalaries.findByEmployeeSalaryID", query = "SELECT e FROM EmployeeSalaries e WHERE e.employeeSalaryID = :employeeSalaryID"),
    @NamedQuery(name = "EmployeeSalaries.findByDate", query = "SELECT e FROM EmployeeSalaries e WHERE e.date = :date")})
public class EmployeeSalaries implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EmployeeSalaryID")
    private Integer employeeSalaryID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    @ManyToOne(optional = false)
    private Employees employeeID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeSalaries")
    private Collection<EmployeeSalaryDetails> employeeSalaryDetailsCollection;

    public EmployeeSalaries() {
    }

    public EmployeeSalaries(Integer employeeSalaryID) {
        this.employeeSalaryID = employeeSalaryID;
    }

    public EmployeeSalaries(Integer employeeSalaryID, Date date) {
        this.employeeSalaryID = employeeSalaryID;
        this.date = date;
    }

    public Integer getEmployeeSalaryID() {
        return employeeSalaryID;
    }

    public void setEmployeeSalaryID(Integer employeeSalaryID) {
        this.employeeSalaryID = employeeSalaryID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employees getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employees employeeID) {
        this.employeeID = employeeID;
    }

    @XmlTransient
    public Collection<EmployeeSalaryDetails> getEmployeeSalaryDetailsCollection() {
        return employeeSalaryDetailsCollection;
    }

    public void setEmployeeSalaryDetailsCollection(Collection<EmployeeSalaryDetails> employeeSalaryDetailsCollection) {
        this.employeeSalaryDetailsCollection = employeeSalaryDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeSalaryID != null ? employeeSalaryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeSalaries)) {
            return false;
        }
        EmployeeSalaries other = (EmployeeSalaries) object;
        if ((this.employeeSalaryID == null && other.employeeSalaryID != null) || (this.employeeSalaryID != null && !this.employeeSalaryID.equals(other.employeeSalaryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.EmployeeSalaries[ employeeSalaryID=" + employeeSalaryID + " ]";
    }
    
}
