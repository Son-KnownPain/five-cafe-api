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
 * @author ADMIN
 */
@Entity
@Table(name = "OutboundDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OutboundDetails.findAll", query = "SELECT o FROM OutboundDetails o"),
    @NamedQuery(name = "OutboundDetails.findByOutboundID", query = "SELECT o FROM OutboundDetails o WHERE o.outboundDetailsPK.outboundID = :outboundID"),
    @NamedQuery(name = "OutboundDetails.findByMaterialID", query = "SELECT o FROM OutboundDetails o WHERE o.outboundDetailsPK.materialID = :materialID"),
    @NamedQuery(name = "OutboundDetails.findByQuantity", query = "SELECT o FROM OutboundDetails o WHERE o.quantity = :quantity")})
public class OutboundDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OutboundDetailsPK outboundDetailsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity")
    private int quantity;
    @JoinColumn(name = "MaterialID", referencedColumnName = "MaterialID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materials materials;
    @JoinColumn(name = "OutboundID", referencedColumnName = "OutboundID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Outbounds outbounds;

    public OutboundDetails() {
    }

    public OutboundDetails(OutboundDetailsPK outboundDetailsPK) {
        this.outboundDetailsPK = outboundDetailsPK;
    }

    public OutboundDetails(OutboundDetailsPK outboundDetailsPK, int quantity) {
        this.outboundDetailsPK = outboundDetailsPK;
        this.quantity = quantity;
    }

    public OutboundDetails(int outboundID, int materialID) {
        this.outboundDetailsPK = new OutboundDetailsPK(outboundID, materialID);
    }

    public OutboundDetailsPK getOutboundDetailsPK() {
        return outboundDetailsPK;
    }

    public void setOutboundDetailsPK(OutboundDetailsPK outboundDetailsPK) {
        this.outboundDetailsPK = outboundDetailsPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Materials getMaterials() {
        return materials;
    }

    public void setMaterials(Materials materials) {
        this.materials = materials;
    }

    public Outbounds getOutbounds() {
        return outbounds;
    }

    public void setOutbounds(Outbounds outbounds) {
        this.outbounds = outbounds;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (outboundDetailsPK != null ? outboundDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OutboundDetails)) {
            return false;
        }
        OutboundDetails other = (OutboundDetails) object;
        if ((this.outboundDetailsPK == null && other.outboundDetailsPK != null) || (this.outboundDetailsPK != null && !this.outboundDetailsPK.equals(other.outboundDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.OutboundDetails[ outboundDetailsPK=" + outboundDetailsPK + " ]";
    }
    
}
