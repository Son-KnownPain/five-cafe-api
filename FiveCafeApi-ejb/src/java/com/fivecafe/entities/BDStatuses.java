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
@Table(name = "BDStatuses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BDStatuses.findAll", query = "SELECT b FROM BDStatuses b"),
    @NamedQuery(name = "BDStatuses.findByBDStatusID", query = "SELECT b FROM BDStatuses b WHERE b.bDStatusID = :bDStatusID"),
    @NamedQuery(name = "BDStatuses.findByBDStatusValue", query = "SELECT b FROM BDStatuses b WHERE b.bDStatusValue = :bDStatusValue")})
public class BDStatuses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BDStatusID")
    private Integer bDStatusID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BDStatusValue")
    private String bDStatusValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bDStatusID")
    private Collection<BillDetails> billDetailsCollection;

    public BDStatuses() {
    }

    public BDStatuses(Integer bDStatusID) {
        this.bDStatusID = bDStatusID;
    }

    public BDStatuses(Integer bDStatusID, String bDStatusValue) {
        this.bDStatusID = bDStatusID;
        this.bDStatusValue = bDStatusValue;
    }

    public Integer getBDStatusID() {
        return bDStatusID;
    }

    public void setBDStatusID(Integer bDStatusID) {
        this.bDStatusID = bDStatusID;
    }

    public String getBDStatusValue() {
        return bDStatusValue;
    }

    public void setBDStatusValue(String bDStatusValue) {
        this.bDStatusValue = bDStatusValue;
    }

    @XmlTransient
    public Collection<BillDetails> getBillDetailsCollection() {
        return billDetailsCollection;
    }

    public void setBillDetailsCollection(Collection<BillDetails> billDetailsCollection) {
        this.billDetailsCollection = billDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bDStatusID != null ? bDStatusID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BDStatuses)) {
            return false;
        }
        BDStatuses other = (BDStatuses) object;
        if ((this.bDStatusID == null && other.bDStatusID != null) || (this.bDStatusID != null && !this.bDStatusID.equals(other.bDStatusID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.BDStatuses[ bDStatusID=" + bDStatusID + " ]";
    }
    
}
