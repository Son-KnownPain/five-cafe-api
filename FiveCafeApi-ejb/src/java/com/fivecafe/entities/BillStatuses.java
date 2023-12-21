/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "BillStatuses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BillStatuses.findAll", query = "SELECT b FROM BillStatuses b"),
    @NamedQuery(name = "BillStatuses.findByBillStatusID", query = "SELECT b FROM BillStatuses b WHERE b.billStatusID = :billStatusID"),
    @NamedQuery(name = "BillStatuses.findByBillStatusValue", query = "SELECT b FROM BillStatuses b WHERE b.billStatusValue = :billStatusValue"),
    @NamedQuery(name = "BillStatuses.findByToCheck", query = "SELECT b FROM BillStatuses b WHERE b.toCheck = :toCheck")})
public class BillStatuses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BillStatusID")
    private Integer billStatusID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BillStatusValue")
    private String billStatusValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ToCheck")
    private boolean toCheck;

    public BillStatuses() {
    }

    public BillStatuses(Integer billStatusID) {
        this.billStatusID = billStatusID;
    }

    public BillStatuses(Integer billStatusID, String billStatusValue, boolean toCheck) {
        this.billStatusID = billStatusID;
        this.billStatusValue = billStatusValue;
        this.toCheck = toCheck;
    }

    public Integer getBillStatusID() {
        return billStatusID;
    }

    public void setBillStatusID(Integer billStatusID) {
        this.billStatusID = billStatusID;
    }

    public String getBillStatusValue() {
        return billStatusValue;
    }

    public void setBillStatusValue(String billStatusValue) {
        this.billStatusValue = billStatusValue;
    }

    public boolean getToCheck() {
        return toCheck;
    }

    public void setToCheck(boolean toCheck) {
        this.toCheck = toCheck;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billStatusID != null ? billStatusID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BillStatuses)) {
            return false;
        }
        BillStatuses other = (BillStatuses) object;
        if ((this.billStatusID == null && other.billStatusID != null) || (this.billStatusID != null && !this.billStatusID.equals(other.billStatusID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.BillStatuses[ billStatusID=" + billStatusID + " ]";
    }
    
}
