/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intt.datenlogik;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fabio
 */
@Entity
@Table(name = "station")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Station.findAll", query = "SELECT s FROM Station s"),
    @NamedQuery(name = "Station.findByStationID", query = "SELECT s FROM Station s WHERE s.stationID = :stationID"),
    @NamedQuery(name = "Station.findByCoordsA", query = "SELECT s FROM Station s WHERE s.coordsA = :coordsA"),
    @NamedQuery(name = "Station.findByCoordsL", query = "SELECT s FROM Station s WHERE s.coordsL = :coordsL"),
    @NamedQuery(name = "Station.findByLocation", query = "SELECT s FROM Station s WHERE s.location = :location"),
    @NamedQuery(name = "Station.findByType", query = "SELECT s FROM Station s WHERE s.type = :type"),
    @NamedQuery(name = "Station.findByDescription", query = "SELECT s FROM Station s WHERE s.description = :description")})
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stationID")
    private Integer stationID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "coordsA")
    private String coordsA;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "coordsL")
    private String coordsL;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "description")
    private String description;

    public Station() {
    }

    public Station(Integer stationID) {
        this.stationID = stationID;
    }

    public Station(Integer stationID, String coordsA, String coordsL, String location, String type, String description) {
        this.stationID = stationID;
        this.coordsA = coordsA;
        this.coordsL = coordsL;
        this.location = location;
        this.type = type;
        this.description = description;
    }

    public Integer getStationID() {
        return stationID;
    }

    public void setStationID(Integer stationID) {
        this.stationID = stationID;
    }

    public String getCoordsA() {
        return coordsA;
    }

    public void setCoordsA(String coordsA) {
        this.coordsA = coordsA;
    }

    public String getCoordsL() {
        return coordsL;
    }

    public void setCoordsL(String coordsL) {
        this.coordsL = coordsL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stationID != null ? stationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Station)) {
            return false;
        }
        Station other = (Station) object;
        if ((this.stationID == null && other.stationID != null) || (this.stationID != null && !this.stationID.equals(other.stationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "intt.datenlogik.Station[ stationID=" + stationID + " ]";
    }
    
}
