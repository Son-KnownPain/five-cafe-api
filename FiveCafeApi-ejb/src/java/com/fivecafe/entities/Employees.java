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
@Table(name = "Employees")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e"),
    @NamedQuery(name = "Employees.findByEmployeeID", query = "SELECT e FROM Employees e WHERE e.employeeID = :employeeID"),
    @NamedQuery(name = "Employees.findByName", query = "SELECT e FROM Employees e WHERE e.name = :name"),
    @NamedQuery(name = "Employees.findByPhone", query = "SELECT e FROM Employees e WHERE e.phone = :phone"),
    @NamedQuery(name = "Employees.findByImage", query = "SELECT e FROM Employees e WHERE e.image = :image"),
    @NamedQuery(name = "Employees.findByUsername", query = "SELECT e FROM Employees e WHERE e.username = :username"),
    @NamedQuery(name = "Employees.findByPassword", query = "SELECT e FROM Employees e WHERE e.password = :password")})
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EmployeeID")
    private Integer employeeID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Name")
    private String name;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Phone")
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Image")
    private String image;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Password")
    private String password;
    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID")
    @ManyToOne(optional = false)
    private Roles roleID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeID")
    private Collection<Outbounds> outboundsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeID")
    private Collection<EmployeeSalaries> employeeSalariesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeID")
    private Collection<EmployeeTimeKeepings> employeeTimeKeepingsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeID")
    private Collection<Bills> billsCollection;

    public Employees() {
    }

    public Employees(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public Employees(Integer employeeID, String name, String phone, String image, String username, String password) {
        this.employeeID = employeeID;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.username = username;
        this.password = password;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoleID() {
        return roleID;
    }

    public void setRoleID(Roles roleID) {
        this.roleID = roleID;
    }

    @XmlTransient
    public Collection<Outbounds> getOutboundsCollection() {
        return outboundsCollection;
    }

    public void setOutboundsCollection(Collection<Outbounds> outboundsCollection) {
        this.outboundsCollection = outboundsCollection;
    }

    @XmlTransient
    public Collection<EmployeeSalaries> getEmployeeSalariesCollection() {
        return employeeSalariesCollection;
    }

    public void setEmployeeSalariesCollection(Collection<EmployeeSalaries> employeeSalariesCollection) {
        this.employeeSalariesCollection = employeeSalariesCollection;
    }

    @XmlTransient
    public Collection<EmployeeTimeKeepings> getEmployeeTimeKeepingsCollection() {
        return employeeTimeKeepingsCollection;
    }

    public void setEmployeeTimeKeepingsCollection(Collection<EmployeeTimeKeepings> employeeTimeKeepingsCollection) {
        this.employeeTimeKeepingsCollection = employeeTimeKeepingsCollection;
    }

    @XmlTransient
    public Collection<Bills> getBillsCollection() {
        return billsCollection;
    }

    public void setBillsCollection(Collection<Bills> billsCollection) {
        this.billsCollection = billsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeID != null ? employeeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.employeeID == null && other.employeeID != null) || (this.employeeID != null && !this.employeeID.equals(other.employeeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fivecafe.entities.Employees[ employeeID=" + employeeID + " ]";
    }
    
}
