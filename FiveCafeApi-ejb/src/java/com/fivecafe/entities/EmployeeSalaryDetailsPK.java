/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ADMIN
 */
@Embeddable
public class EmployeeSalaryDetailsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "EmployeeSalaryID")
    private int employeeSalaryID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TimeKeepingID")
    private int timeKeepingID;

    public EmployeeSalaryDetailsPK() {
    }

    public EmployeeSalaryDetailsPK(int employeeSalaryID, int timeKeepingID) {
        this.employeeSalaryID = employeeSalaryID;
        this.timeKeepingID = timeKeepingID;
    }

    public int getEmployeeSalaryID() {
        return employeeSalaryID;
    }

    public void setEmployeeSalaryID(int employeeSalaryID) {
        this.employeeSalaryID = employeeSalaryID;
    }

    public int getTimeKeepingID() {
        return timeKeepingID;
    }

    public void setTimeKeepingID(int timeKeepingID) {
        this.timeKeepingID = timeKeepingID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) employeeSalaryID;
        hash += (int) timeKeepingID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeSalaryDetailsPK)) {
            return false;
        }
        EmployeeSalaryDetailsPK other = (EmployeeSalaryDetailsPK) object;
        if (this.employeeSalaryID != other.employeeSalaryID) {
            return false;
        }
        if (this.timeKeepingID != other.timeKeepingID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.EmployeeSalaryDetailsPK[ employeeSalaryID=" + employeeSalaryID + ", timeKeepingID=" + timeKeepingID + " ]";
    }
    
}
