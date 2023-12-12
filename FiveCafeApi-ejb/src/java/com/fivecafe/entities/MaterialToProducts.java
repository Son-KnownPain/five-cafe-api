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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "MaterialToProducts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MaterialToProducts.findAll", query = "SELECT m FROM MaterialToProducts m"),
    @NamedQuery(name = "MaterialToProducts.findByMaterialID", query = "SELECT m FROM MaterialToProducts m WHERE m.materialToProductsPK.materialID = :materialID"),
    @NamedQuery(name = "MaterialToProducts.findByProductID", query = "SELECT m FROM MaterialToProducts m WHERE m.materialToProductsPK.productID = :productID"),
    @NamedQuery(name = "MaterialToProducts.findByDescription", query = "SELECT m FROM MaterialToProducts m WHERE m.description = :description")})
public class MaterialToProducts implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MaterialToProductsPK materialToProductsPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Description")
    private String description;
    @JoinColumn(name = "MaterialID", referencedColumnName = "MaterialID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materials materials;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Products products;

    public MaterialToProducts() {
    }

    public MaterialToProducts(MaterialToProductsPK materialToProductsPK) {
        this.materialToProductsPK = materialToProductsPK;
    }

    public MaterialToProducts(MaterialToProductsPK materialToProductsPK, String description) {
        this.materialToProductsPK = materialToProductsPK;
        this.description = description;
    }

    public MaterialToProducts(int materialID, int productID) {
        this.materialToProductsPK = new MaterialToProductsPK(materialID, productID);
    }

    public MaterialToProductsPK getMaterialToProductsPK() {
        return materialToProductsPK;
    }

    public void setMaterialToProductsPK(MaterialToProductsPK materialToProductsPK) {
        this.materialToProductsPK = materialToProductsPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Materials getMaterials() {
        return materials;
    }

    public void setMaterials(Materials materials) {
        this.materials = materials;
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
        hash += (materialToProductsPK != null ? materialToProductsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialToProducts)) {
            return false;
        }
        MaterialToProducts other = (MaterialToProducts) object;
        if ((this.materialToProductsPK == null && other.materialToProductsPK != null) || (this.materialToProductsPK != null && !this.materialToProductsPK.equals(other.materialToProductsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.MaterialToProducts[ materialToProductsPK=" + materialToProductsPK + " ]";
    }
    
}
