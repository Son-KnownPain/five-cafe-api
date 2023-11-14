/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "EmployeeSalaries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeSalaries.findAll", query = "SELECT e FROM EmployeeSalaries e"),
    @NamedQuery(name = "EmployeeSalaries.findByEmployeeSalaryID", query = "SELECT e FROM EmployeeSalaries e WHERE e.employeeSalaryID = :employeeSalaryID"),
    @NamedQuery(name = "EmployeeSalaries.findByDate", query = "SELECT e FROM EmployeeSalaries e WHERE e.date = :date"),
    @NamedQuery(name = "EmployeeSalaries.findBySalary", query = "SELECT e FROM EmployeeSalaries e WHERE e.salary = :salary"),
    @NamedQuery(name = "EmployeeSalaries.findByBonus", query = "SELECT e FROM EmployeeSalaries e WHERE e.bonus = :bonus")})
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "Salary")
    private double salary;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Bonus")
    private double bonus;
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    @ManyToOne(optional = false)
    private Employees employeeID;

    public EmployeeSalaries() {
    }

    public EmployeeSalaries(Integer employeeSalaryID) {
        this.employeeSalaryID = employeeSalaryID;
    }

    public EmployeeSalaries(Integer employeeSalaryID, Date date, double salary, double bonus) {
        this.employeeSalaryID = employeeSalaryID;
        this.date = date;
        this.salary = salary;
        this.bonus = bonus;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public Employees getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employees employeeID) {
        this.employeeID = employeeID;
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
