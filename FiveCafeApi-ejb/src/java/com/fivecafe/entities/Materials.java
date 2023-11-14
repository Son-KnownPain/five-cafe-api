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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Materials")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materials.findAll", query = "SELECT m FROM Materials m"),
    @NamedQuery(name = "Materials.findByMaterialID", query = "SELECT m FROM Materials m WHERE m.materialID = :materialID"),
    @NamedQuery(name = "Materials.findByName", query = "SELECT m FROM Materials m WHERE m.name = :name"),
    @NamedQuery(name = "Materials.findByUnit", query = "SELECT m FROM Materials m WHERE m.unit = :unit"),
    @NamedQuery(name = "Materials.findByImage", query = "SELECT m FROM Materials m WHERE m.image = :image")})
public class Materials implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MaterialID")
    private Integer materialID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Unit")
    private String unit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Image")
    private String image;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materials")
    private Collection<ImportDetails> importDetailsCollection;
    @JoinColumn(name = "MaterialCategoryID", referencedColumnName = "MaterialCategoryID")
    @ManyToOne(optional = false)
    private MaterialCategories materialCategoryID;

    public Materials() {
    }

    public Materials(Integer materialID) {
        this.materialID = materialID;
    }

    public Materials(Integer materialID, String name, String unit, String image) {
        this.materialID = materialID;
        this.name = name;
        this.unit = unit;
        this.image = image;
    }

    public Integer getMaterialID() {
        return materialID;
    }

    public void setMaterialID(Integer materialID) {
        this.materialID = materialID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @XmlTransient
    public Collection<ImportDetails> getImportDetailsCollection() {
        return importDetailsCollection;
    }

    public void setImportDetailsCollection(Collection<ImportDetails> importDetailsCollection) {
        this.importDetailsCollection = importDetailsCollection;
    }

    public MaterialCategories getMaterialCategoryID() {
        return materialCategoryID;
    }

    public void setMaterialCategoryID(MaterialCategories materialCategoryID) {
        this.materialCategoryID = materialCategoryID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materialID != null ? materialID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materials)) {
            return false;
        }
        Materials other = (Materials) object;
        if ((this.materialID == null && other.materialID != null) || (this.materialID != null && !this.materialID.equals(other.materialID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.Materials[ materialID=" + materialID + " ]";
    }
    
}
