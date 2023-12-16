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
@Table(name = "Outbounds")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Outbounds.findAll", query = "SELECT o FROM Outbounds o"),
    @NamedQuery(name = "Outbounds.findByEmployeeID", query = "SELECT o FROM Outbounds o WHERE o.employeeID = :employeeID"),
    @NamedQuery(name = "Outbounds.findByOutboundID", query = "SELECT o FROM Outbounds o WHERE o.outboundID = :outboundID"),
    @NamedQuery(name = "Outbounds.findByDate", query = "SELECT o FROM Outbounds o WHERE o.date = :date")})
public class Outbounds implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OutboundID")
    private Integer outboundID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    @ManyToOne(optional = false)
    private Employees employeeID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "outbounds")
    private Collection<OutboundDetails> outboundDetailsCollection;

    public Outbounds() {
    }

    public Outbounds(Integer outboundID) {
        this.outboundID = outboundID;
    }

    public Outbounds(Integer outboundID, Date date) {
        this.outboundID = outboundID;
        this.date = date;
    }

    public Integer getOutboundID() {
        return outboundID;
    }

    public void setOutboundID(Integer outboundID) {
        this.outboundID = outboundID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employees getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employees employeeID) {
        this.employeeID = employeeID;
    }

    @XmlTransient
    public Collection<OutboundDetails> getOutboundDetailsCollection() {
        return outboundDetailsCollection;
    }

    public void setOutboundDetailsCollection(Collection<OutboundDetails> outboundDetailsCollection) {
        this.outboundDetailsCollection = outboundDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (outboundID != null ? outboundID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Outbounds)) {
            return false;
        }
        Outbounds other = (Outbounds) object;
        if ((this.outboundID == null && other.outboundID != null) || (this.outboundID != null && !this.outboundID.equals(other.outboundID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.Outbounds[ outboundID=" + outboundID + " ]";
    }
    
}
