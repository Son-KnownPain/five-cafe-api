/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "ProductCategories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductCategories.findAll", query = "SELECT p FROM ProductCategories p"),
    @NamedQuery(name = "ProductCategories.findByProductCategoryID", query = "SELECT p FROM ProductCategories p WHERE p.productCategoryID = :productCategoryID"),
    @NamedQuery(name = "ProductCategories.findByName", query = "SELECT p FROM ProductCategories p WHERE p.name = :name"),
    @NamedQuery(name = "ProductCategories.findByDescription", query = "SELECT p FROM ProductCategories p WHERE p.description = :description")})
public class ProductCategories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ProductCategoryID")
    private Integer productCategoryID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productCategoryID")
    private Collection<Products> productsCollection;

    public ProductCategories() {
    }

    public ProductCategories(Integer productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public ProductCategories(Integer productCategoryID, String name, String description) {
        this.productCategoryID = productCategoryID;
        this.name = name;
        this.description = description;
    }

    public Integer getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(Integer productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productCategoryID != null ? productCategoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCategories)) {
            return false;
        }
        ProductCategories other = (ProductCategories) object;
        if ((this.productCategoryID == null && other.productCategoryID != null) || (this.productCategoryID != null && !this.productCategoryID.equals(other.productCategoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.ProductCategories[ productCategoryID=" + productCategoryID + " ]";
    }
    
}
