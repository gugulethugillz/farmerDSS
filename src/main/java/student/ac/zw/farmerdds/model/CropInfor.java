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
@Table(name = "cropInfor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CropInfor.findAll", query = "SELECT c FROM CropInfor c")
    , @NamedQuery(name = "CropInfor.findByCropName", query = "SELECT c FROM CropInfor c WHERE c.cropName = :cropName")
    , @NamedQuery(name = "CropInfor.findBySoilType", query = "SELECT c FROM CropInfor c WHERE c.soilType = :soilType")
    , @NamedQuery(name = "CropInfor.findByPhValue", query = "SELECT c FROM CropInfor c WHERE c.phValue = :phValue")
    , @NamedQuery(name = "CropInfor.findByMoistureValue", query = "SELECT c FROM CropInfor c WHERE c.moistureValue = :moistureValue")
    , @NamedQuery(name = "CropInfor.findById", query = "SELECT c FROM CropInfor c WHERE c.id = :id")
    , @NamedQuery(name = "CropInfor.findByFarmerId", query = "SELECT c FROM CropInfor c WHERE c.farmerId = :farmerId")})

public class CropInfor implements Serializable {

    @Size(max = 50)
    @Column(name = "activity")
    private String activity;

    @Size(max = 50)
    @Column(name = "datePlanted")
    private String datePlanted;

    @Size(max = 50)
    @Column(name = "farmerId")
    private String farmerId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    private static final long serialVersionUID = 1L;
    @Size(max = 30)
    @Column(name = "cropName")
    private String cropName;
    @Size(max = 30)
    @Column(name = "soilType")
    private String soilType;
    @Size(max = 30)
    @Column(name = "phValue")
    private String phValue;
    @Size(max = 30)
    @Column(name = "moistureValue")
    private String moistureValue;
    

    public CropInfor() {
    }

  

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getPhValue() {
        return phValue;
    }

    public void setPhValue(String phValue) {
        this.phValue = phValue;
    }

    public String getMoistureValue() {
        return moistureValue;
    }

    public void setMoistureValue(String moistureValue) {
        this.moistureValue = moistureValue;
    }


    public CropInfor(Integer id) {
        this.id = id;
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
        if (!(object instanceof CropInfor)) {
            return false;
        }
        CropInfor other = (CropInfor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.ac.zw.farmerdds.entities.CropInfor[ id=" + id + " ]";
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(String datePlanted) {
        this.datePlanted = datePlanted;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    
}
