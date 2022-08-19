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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gugsy
 */
@Entity
@Table(name = "timelines")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timelines.findAll", query = "SELECT t FROM Timelines t")
    , @NamedQuery(name = "Timelines.findByCropName", query = "SELECT t FROM Timelines t WHERE t.cropName = :cropName")
    , @NamedQuery(name = "Timelines.findByDays", query = "SELECT t FROM Timelines t WHERE t.days = :days")
    , @NamedQuery(name = "Timelines.findByAction", query = "SELECT t FROM Timelines t WHERE t.action = :action")
    , @NamedQuery(name = "Timelines.findById", query = "SELECT t FROM Timelines t WHERE t.id = :id")})
public class Timelines implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "cropName")
    private String cropName;
    @Column(name = "days")
    private Integer days;
    @Size(max = 50)
    @Column(name = "action")
    private String action;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Timelines() {
    }

    public Timelines(Integer id) {
        this.id = id;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Timelines)) {
            return false;
        }
        Timelines other = (Timelines) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.ac.zw.farmerdds.entities.Timelines[ id=" + id + " ]";
    }
    
}
