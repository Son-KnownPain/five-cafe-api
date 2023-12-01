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
@Table(name = "ImportDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImportDetails.findAll", query = "SELECT i FROM ImportDetails i"),
    @NamedQuery(name = "ImportDetails.findByImportID", query = "SELECT i FROM ImportDetails i WHERE i.importDetailsPK.importID = :importID"),
    @NamedQuery(name = "ImportDetails.findByMaterialID", query = "SELECT i FROM ImportDetails i WHERE i.importDetailsPK.materialID = :materialID"),
    @NamedQuery(name = "ImportDetails.findByUnitPrice", query = "SELECT i FROM ImportDetails i WHERE i.unitPrice = :unitPrice"),
    @NamedQuery(name = "ImportDetails.findByQuantity", query = "SELECT i FROM ImportDetails i WHERE i.quantity = :quantity")})
public class ImportDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ImportDetailsPK importDetailsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UnitPrice")
    private double unitPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity")
    private int quantity;
    @JoinColumn(name = "ImportID", referencedColumnName = "ImportID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Imports imports;
    @JoinColumn(name = "MaterialID", referencedColumnName = "MaterialID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materials materials;
    @JoinColumn(name = "SupplierID", referencedColumnName = "SupplierID")
    @ManyToOne(optional = false)
    private Suppliers supplierID;

    public ImportDetails() {
    }

    public ImportDetails(ImportDetailsPK importDetailsPK) {
        this.importDetailsPK = importDetailsPK;
    }

    public ImportDetails(ImportDetailsPK importDetailsPK, double unitPrice, int quantity) {
        this.importDetailsPK = importDetailsPK;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public ImportDetails(int importID, int materialID) {
        this.importDetailsPK = new ImportDetailsPK(importID, materialID);
    }

    public ImportDetailsPK getImportDetailsPK() {
        return importDetailsPK;
    }

    public void setImportDetailsPK(ImportDetailsPK importDetailsPK) {
        this.importDetailsPK = importDetailsPK;
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

    public Imports getImports() {
        return imports;
    }

    public void setImports(Imports imports) {
        this.imports = imports;
    }

    public Materials getMaterials() {
        return materials;
    }

    public void setMaterials(Materials materials) {
        this.materials = materials;
    }

    public Suppliers getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Suppliers supplierID) {
        this.supplierID = supplierID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (importDetailsPK != null ? importDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImportDetails)) {
            return false;
        }
        ImportDetails other = (ImportDetails) object;
        if ((this.importDetailsPK == null && other.importDetailsPK != null) || (this.importDetailsPK != null && !this.importDetailsPK.equals(other.importDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.ImportDetails[ importDetailsPK=" + importDetailsPK + " ]";
    }
    
}
