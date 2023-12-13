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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "Imports")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imports.findAll", query = "SELECT i FROM Imports i"),
    @NamedQuery(name = "Imports.findByImportID", query = "SELECT i FROM Imports i WHERE i.importID = :importID"),
    @NamedQuery(name = "Imports.findByDate", query = "SELECT i FROM Imports i WHERE i.date = :date")})
public class Imports implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ImportID")
    private Integer importID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "imports")
    private Collection<ImportDetails> importDetailsCollection;

    public Imports() {
    }

    public Imports(Integer importID) {
        this.importID = importID;
    }

    public Imports(Integer importID, Date date) {
        this.importID = importID;
        this.date = date;
    }

    public Integer getImportID() {
        return importID;
    }

    public void setImportID(Integer importID) {
        this.importID = importID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlTransient
    public Collection<ImportDetails> getImportDetailsCollection() {
        return importDetailsCollection;
    }

    public void setImportDetailsCollection(Collection<ImportDetails> importDetailsCollection) {
        this.importDetailsCollection = importDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (importID != null ? importID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imports)) {
            return false;
        }
        Imports other = (Imports) object;
        if ((this.importID == null && other.importID != null) || (this.importID != null && !this.importID.equals(other.importID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.Imports[ importID=" + importID + " ]";
    }
    
}
