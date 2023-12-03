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
 * @author Admin
 */
@Embeddable
public class ImportDetailsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ImportID")
    private int importID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MaterialID")
    private int materialID;

    public ImportDetailsPK() {
    }

    public ImportDetailsPK(int importID, int materialID) {
        this.importID = importID;
        this.materialID = materialID;
    }

    public int getImportID() {
        return importID;
    }

    public void setImportID(int importID) {
        this.importID = importID;
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
        hash += (int) importID;
        hash += (int) materialID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImportDetailsPK)) {
            return false;
        }
        ImportDetailsPK other = (ImportDetailsPK) object;
        if (this.importID != other.importID) {
            return false;
        }
        if (this.materialID != other.materialID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.ImportDetailsPK[ importID=" + importID + ", materialID=" + materialID + " ]";
    }
    
}
