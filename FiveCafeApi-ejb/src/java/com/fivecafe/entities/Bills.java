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
import javax.persistence.OneToOne;
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
@Table(name = "Bills")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bills.findAll", query = "SELECT b FROM Bills b"),
    @NamedQuery(name = "Bills.findByBillID", query = "SELECT b FROM Bills b WHERE b.billID = :billID"),
    @NamedQuery(name = "Bills.findByEmployeeID", query = "SELECT b FROM Bills b WHERE b.employeeID = :employeeID"),
    @NamedQuery(name = "Bills.findByCreatedDate", query = "SELECT b FROM Bills b WHERE b.createdDate = :createdDate"),
    @NamedQuery(name = "Bills.findByCardCode", query = "SELECT b FROM Bills b WHERE b.cardCode = :cardCode")})
public class Bills implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BillID")
    private Integer billID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EmployeeID")
    private int employeeID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CardCode")
    private String cardCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bills")
    private Collection<BillDetails> billDetailsCollection;
    @JoinColumn(name = "BillStatusID", referencedColumnName = "BillStatusID")
    @ManyToOne(optional = false)
    private BillStatuses billStatusID;
    @JoinColumn(name = "BillID", referencedColumnName = "EmployeeID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Employees employees;

    public Bills() {
    }

    public Bills(Integer billID) {
        this.billID = billID;
    }

    public Bills(Integer billID, int employeeID, Date createdDate, String cardCode) {
        this.billID = billID;
        this.employeeID = employeeID;
        this.createdDate = createdDate;
        this.cardCode = cardCode;
    }

    public Integer getBillID() {
        return billID;
    }

    public void setBillID(Integer billID) {
        this.billID = billID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @XmlTransient
    public Collection<BillDetails> getBillDetailsCollection() {
        return billDetailsCollection;
    }

    public void setBillDetailsCollection(Collection<BillDetails> billDetailsCollection) {
        this.billDetailsCollection = billDetailsCollection;
    }

    public BillStatuses getBillStatusID() {
        return billStatusID;
    }

    public void setBillStatusID(BillStatuses billStatusID) {
        this.billStatusID = billStatusID;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billID != null ? billID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bills)) {
            return false;
        }
        Bills other = (Bills) object;
        if ((this.billID == null && other.billID != null) || (this.billID != null && !this.billID.equals(other.billID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.Bills[ billID=" + billID + " ]";
    }
    
}
