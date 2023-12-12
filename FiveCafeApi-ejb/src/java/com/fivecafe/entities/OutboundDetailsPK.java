/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fivecafe.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ADMIN
 */
@Embeddable
public class OutboundDetailsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "OutboundID")
    private int outboundID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MaterialID")
    private int materialID;

    public OutboundDetailsPK() {
    }

    public OutboundDetailsPK(int outboundID, int materialID) {
        this.outboundID = outboundID;
        this.materialID = materialID;
    }

    public int getOutboundID() {
        return outboundID;
    }

    public void setOutboundID(int outboundID) {
        this.outboundID = outboundID;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) outboundID;
        hash += (int) materialID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OutboundDetailsPK)) {
            return false;
        }
        OutboundDetailsPK other = (OutboundDetailsPK) object;
        if (this.outboundID != other.outboundID) {
            return false;
        }
        if (this.materialID != other.materialID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.OutboundDetailsPK[ outboundID=" + outboundID + ", materialID=" + materialID + " ]";
    }
    
}
