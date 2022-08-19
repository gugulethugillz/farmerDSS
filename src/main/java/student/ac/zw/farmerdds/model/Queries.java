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
@Table(name = "queries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Queries.findAll", query = "SELECT q FROM Queries q")
    , @NamedQuery(name = "Queries.findById", query = "SELECT q FROM Queries q WHERE q.id = :id")
    , @NamedQuery(name = "Queries.findByQuestion", query = "SELECT q FROM Queries q WHERE q.question = :question")
    , @NamedQuery(name = "Queries.findByResponse", query = "SELECT q FROM Queries q WHERE q.response = :response")})
public class Queries implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 250)
    @Column(name = "question")
    private String question;
    @Size(max = 250)
    @Column(name = "response")
    private String response;

    public Queries() {
    }

    public Queries(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
        if (!(object instanceof Queries)) {
            return false;
        }
        Queries other = (Queries) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.ac.zw.farmerdds.entities.Queries[ id=" + id + " ]";
    }
    
}
