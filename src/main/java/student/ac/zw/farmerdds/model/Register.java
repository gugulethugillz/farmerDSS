/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.ac.zw.farmerdds.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gugsy
 */
@Entity
@Table(name = "register")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Register.findAll", query = "SELECT r FROM Register r")
    , @NamedQuery(name = "Register.findBySalutation", query = "SELECT r FROM Register r WHERE r.salutation = :salutation")
    , @NamedQuery(name = "Register.findByFname", query = "SELECT r FROM Register r WHERE r.fname = :fname")
    , @NamedQuery(name = "Register.findBySurname", query = "SELECT r FROM Register r WHERE r.surname = :surname")
    , @NamedQuery(name = "Register.findByEmail", query = "SELECT r FROM Register r WHERE r.email = :email")
    , @NamedQuery(name = "Register.findByPassword", query = "SELECT r FROM Register r WHERE r.password = :password")
    , @NamedQuery(name = "Register.findByAddress", query = "SELECT r FROM Register r WHERE r.address = :address")
    , @NamedQuery(name = "Register.findByCity", query = "SELECT r FROM Register r WHERE r.city = :city")
    , @NamedQuery(name = "Register.findByFarmerId", query = "SELECT r FROM Register r WHERE r.farmerId = :farmerId")})
public class Register implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 30)
    @Column(name = "salutation")
    private String salutation;
    @Size(max = 30)
    @Column(name = "fname")
    private String fname;
    @Size(max = 30)
    @Column(name = "surname")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "email")
    private String email;
    @Size(max = 30)
    @Column(name = "password")
    private String password;
    @Size(max = 30)
    @Column(name = "address")
    private String address;
    @Size(max = 30)
    @Column(name = "city")
    private String city;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "farmerId")
    private String farmerId;

    public Register() {
    }

    public Register(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (farmerId != null ? farmerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Register)) {
            return false;
        }
        Register other = (Register) object;
        if ((this.farmerId == null && other.farmerId != null) || (this.farmerId != null && !this.farmerId.equals(other.farmerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.ac.zw.farmerdds.entities.Register[ farmerId=" + farmerId + " ]";
    }
    
}
