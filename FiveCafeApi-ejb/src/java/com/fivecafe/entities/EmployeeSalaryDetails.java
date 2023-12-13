/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "EmployeeSalaryDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeSalaryDetails.findAll", query = "SELECT e FROM EmployeeSalaryDetails e"),
    @NamedQuery(name = "EmployeeSalaryDetails.findByEmployeeSalaryID", query = "SELECT e FROM EmployeeSalaryDetails e WHERE e.employeeSalaryDetailsPK.employeeSalaryID = :employeeSalaryID"),
    @NamedQuery(name = "EmployeeSalaryDetails.findByTimeKeepingID", query = "SELECT e FROM EmployeeSalaryDetails e WHERE e.employeeSalaryDetailsPK.timeKeepingID = :timeKeepingID"),
    @NamedQuery(name = "EmployeeSalaryDetails.findByBonus", query = "SELECT e FROM EmployeeSalaryDetails e WHERE e.bonus = :bonus"),
    @NamedQuery(name = "EmployeeSalaryDetails.findByDeduction", query = "SELECT e FROM EmployeeSalaryDetails e WHERE e.deduction = :deduction")})
public class EmployeeSalaryDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmployeeSalaryDetailsPK employeeSalaryDetailsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Bonus")
    private double bonus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Deduction")
    private double deduction;
    @JoinColumn(name = "EmployeeSalaryID", referencedColumnName = "EmployeeSalaryID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EmployeeSalaries employeeSalaries;
    @JoinColumn(name = "TimeKeepingID", referencedColumnName = "TimeKeepingID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EmployeeTimeKeepings employeeTimeKeepings;

    public EmployeeSalaryDetails() {
    }

    public EmployeeSalaryDetails(EmployeeSalaryDetailsPK employeeSalaryDetailsPK) {
        this.employeeSalaryDetailsPK = employeeSalaryDetailsPK;
    }

    public EmployeeSalaryDetails(EmployeeSalaryDetailsPK employeeSalaryDetailsPK, double bonus, double deduction) {
        this.employeeSalaryDetailsPK = employeeSalaryDetailsPK;
        this.bonus = bonus;
        this.deduction = deduction;
    }

    public EmployeeSalaryDetails(int employeeSalaryID, int timeKeepingID) {
        this.employeeSalaryDetailsPK = new EmployeeSalaryDetailsPK(employeeSalaryID, timeKeepingID);
    }

    public EmployeeSalaryDetailsPK getEmployeeSalaryDetailsPK() {
        return employeeSalaryDetailsPK;
    }

    public void setEmployeeSalaryDetailsPK(EmployeeSalaryDetailsPK employeeSalaryDetailsPK) {
        this.employeeSalaryDetailsPK = employeeSalaryDetailsPK;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public EmployeeSalaries getEmployeeSalaries() {
        return employeeSalaries;
    }

    public void setEmployeeSalaries(EmployeeSalaries employeeSalaries) {
        this.employeeSalaries = employeeSalaries;
    }

    public EmployeeTimeKeepings getEmployeeTimeKeepings() {
        return employeeTimeKeepings;
    }

    public void setEmployeeTimeKeepings(EmployeeTimeKeepings employeeTimeKeepings) {
        this.employeeTimeKeepings = employeeTimeKeepings;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeSalaryDetailsPK != null ? employeeSalaryDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeSalaryDetails)) {
            return false;
        }
        EmployeeSalaryDetails other = (EmployeeSalaryDetails) object;
        if ((this.employeeSalaryDetailsPK == null && other.employeeSalaryDetailsPK != null) || (this.employeeSalaryDetailsPK != null && !this.employeeSalaryDetailsPK.equals(other.employeeSalaryDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.EmployeeSalaryDetails[ employeeSalaryDetailsPK=" + employeeSalaryDetailsPK + " ]";
    }
    
}
