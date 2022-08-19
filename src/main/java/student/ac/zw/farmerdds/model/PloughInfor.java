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
@Table(name = "ploughInfor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PloughInfor.findAll", query = "SELECT p FROM PloughInfor p")
    , @NamedQuery(name = "PloughInfor.findById", query = "SELECT p FROM PloughInfor p WHERE p.id = :id")
    , @NamedQuery(name = "PloughInfor.findByCropName", query = "SELECT p FROM PloughInfor p WHERE p.cropName = :cropName")
    , @NamedQuery(name = "PloughInfor.findByDatePlanted", query = "SELECT p FROM PloughInfor p WHERE p.datePlanted = :datePlanted")
    , @NamedQuery(name = "PloughInfor.findByFarmerId", query = "SELECT p FROM PloughInfor p WHERE p.farmerId = :farmerId")})
public class PloughInfor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "cropName")
    private String cropName;
    @Size(max = 30)
    @Column(name = "datePlanted")
    private String datePlanted;
    @Size(max = 30)
    @Column(name = "farmerId")
    private String farmerId;

    public PloughInfor() {
    }

    public PloughInfor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(String datePlanted) {
        this.datePlanted = datePlanted;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PloughInfor)) {
            return false;
        }
        PloughInfor other = (PloughInfor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.ac.zw.farmerdds.entities.PloughInfor[ id=" + id + " ]";
    }
    
}
