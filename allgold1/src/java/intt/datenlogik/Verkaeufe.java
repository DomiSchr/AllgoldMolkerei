/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intt.datenlogik;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fabio
 */
@Entity
@Table(name = "verkaeufe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Verkaeufe.findAll", query = "SELECT v FROM Verkaeufe v"),
    @NamedQuery(name = "Verkaeufe.findByVerkaufID", query = "SELECT v FROM Verkaeufe v WHERE v.verkaufID = :verkaufID"),
    @NamedQuery(name = "Verkaeufe.findByStationID", query = "SELECT v FROM Verkaeufe v WHERE v.stationID = :stationID"),
    @NamedQuery(name = "Verkaeufe.findByProductID", query = "SELECT v FROM Verkaeufe v WHERE v.productID = :productID"),
    @NamedQuery(name = "Verkaeufe.findByAnzahl", query = "SELECT v FROM Verkaeufe v WHERE v.anzahl = :anzahl"),
    @NamedQuery(name = "Verkaeufe.findByTime", query = "SELECT v FROM Verkaeufe v WHERE v.time = :time")})
public class Verkaeufe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "verkaufID")
    private Integer verkaufID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stationID")
    private int stationID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "productID")
    private int productID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anzahl")
    private int anzahl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Verkaeufe() {
    }

    public Verkaeufe(Integer verkaufID) {
        this.verkaufID = verkaufID;
    }

    public Verkaeufe(Integer verkaufID, int stationID, int productID, int anzahl, Date time) {
        this.verkaufID = verkaufID;
        this.stationID = stationID;
        this.productID = productID;
        this.anzahl = anzahl;
        this.time = time;
    }

    public Integer getVerkaufID() {
        return verkaufID;
    }

    public void setVerkaufID(Integer verkaufID) {
        this.verkaufID = verkaufID;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (verkaufID != null ? verkaufID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Verkaeufe)) {
            return false;
        }
        Verkaeufe other = (Verkaeufe) object;
        if ((this.verkaufID == null && other.verkaufID != null) || (this.verkaufID != null && !this.verkaufID.equals(other.verkaufID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "intt.datenlogik.Verkaeufe[ verkaufID=" + verkaufID + " ]";
    }
    
}
