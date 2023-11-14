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
 * @author ADMIN
 */
@Entity
@Table(name = "MaterialCategories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MaterialCategories.findAll", query = "SELECT m FROM MaterialCategories m"),
    @NamedQuery(name = "MaterialCategories.findByMaterialCategoryID", query = "SELECT m FROM MaterialCategories m WHERE m.materialCategoryID = :materialCategoryID"),
    @NamedQuery(name = "MaterialCategories.findByName", query = "SELECT m FROM MaterialCategories m WHERE m.name = :name"),
    @NamedQuery(name = "MaterialCategories.findByDescription", query = "SELECT m FROM MaterialCategories m WHERE m.description = :description")})
public class MaterialCategories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MaterialCategoryID")
    private Integer materialCategoryID;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialCategoryID")
    private Collection<Materials> materialsCollection;

    public MaterialCategories() {
    }

    public MaterialCategories(Integer materialCategoryID) {
        this.materialCategoryID = materialCategoryID;
    }

    public MaterialCategories(Integer materialCategoryID, String name, String description) {
        this.materialCategoryID = materialCategoryID;
        this.name = name;
        this.description = description;
    }

    public Integer getMaterialCategoryID() {
        return materialCategoryID;
    }

    public void setMaterialCategoryID(Integer materialCategoryID) {
        this.materialCategoryID = materialCategoryID;
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
    public Collection<Materials> getMaterialsCollection() {
        return materialsCollection;
    }

    public void setMaterialsCollection(Collection<Materials> materialsCollection) {
        this.materialsCollection = materialsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialCategoryID != null ? materialCategoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaterialCategories)) {
            return false;
        }
        MaterialCategories other = (MaterialCategories) object;
        if ((this.materialCategoryID == null && other.materialCategoryID != null) || (this.materialCategoryID != null && !this.materialCategoryID.equals(other.materialCategoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.MaterialCategories[ materialCategoryID=" + materialCategoryID + " ]";
    }
    
}
