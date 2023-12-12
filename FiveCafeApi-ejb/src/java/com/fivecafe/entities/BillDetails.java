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
 * @author Admin
 */
@Entity
@Table(name = "BillDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BillDetails.findAll", query = "SELECT b FROM BillDetails b"),
    @NamedQuery(name = "BillDetails.findByBillID", query = "SELECT b FROM BillDetails b WHERE b.billDetailsPK.billID = :billID"),
    @NamedQuery(name = "BillDetails.findByProductID", query = "SELECT b FROM BillDetails b WHERE b.billDetailsPK.productID = :productID"),
    @NamedQuery(name = "BillDetails.findByUnitPrice", query = "SELECT b FROM BillDetails b WHERE b.unitPrice = :unitPrice"),
    @NamedQuery(name = "BillDetails.findByQuantity", query = "SELECT b FROM BillDetails b WHERE b.quantity = :quantity")})
public class BillDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BillDetailsPK billDetailsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UnitPrice")
    private double unitPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity")
    private int quantity;
    @JoinColumn(name = "BillID", referencedColumnName = "BillID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bills bills;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Products products;

    public BillDetails() {
    }

    public BillDetails(BillDetailsPK billDetailsPK) {
        this.billDetailsPK = billDetailsPK;
    }

    public BillDetails(BillDetailsPK billDetailsPK, double unitPrice, int quantity) {
        this.billDetailsPK = billDetailsPK;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public BillDetails(int billID, int productID) {
        this.billDetailsPK = new BillDetailsPK(billID, productID);
    }

    public BillDetailsPK getBillDetailsPK() {
        return billDetailsPK;
    }

    public void setBillDetailsPK(BillDetailsPK billDetailsPK) {
        this.billDetailsPK = billDetailsPK;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Bills getBills() {
        return bills;
    }

    public void setBills(Bills bills) {
        this.bills = bills;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billDetailsPK != null ? billDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BillDetails)) {
            return false;
        }
        BillDetails other = (BillDetails) object;
        if ((this.billDetailsPK == null && other.billDetailsPK != null) || (this.billDetailsPK != null && !this.billDetailsPK.equals(other.billDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.BillDetails[ billDetailsPK=" + billDetailsPK + " ]";
    }
    
}
