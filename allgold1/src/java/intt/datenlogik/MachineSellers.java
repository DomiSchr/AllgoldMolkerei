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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fabio
 */
@Entity
@Table(name = "machine_sellers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MachineSellers.findAll", query = "SELECT m FROM MachineSellers m"),
    @NamedQuery(name = "MachineSellers.findByMachineId", query = "SELECT m FROM MachineSellers m WHERE m.machineId = :machineId"),
    @NamedQuery(name = "MachineSellers.findByLocation", query = "SELECT m FROM MachineSellers m WHERE m.location = :location"),
    @NamedQuery(name = "MachineSellers.findByFirstDay", query = "SELECT m FROM MachineSellers m WHERE m.firstDay = :firstDay"),
    @NamedQuery(name = "MachineSellers.findByStatus", query = "SELECT m FROM MachineSellers m WHERE m.status = :status")})
public class MachineSellers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "machine_id")
    private Integer machineId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "modell")
    private String modell;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "firstDay")
    @Temporal(TemporalType.DATE)
    private Date firstDay;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "status")
    private String status;

    public MachineSellers() {
    }

    public MachineSellers(Integer machineId) {
        this.machineId = machineId;
    }

    public MachineSellers(Integer machineId, String modell, String location, Date firstDay, String status) {
        this.machineId = machineId;
        this.modell = modell;
        this.location = location;
        this.firstDay = firstDay;
        this.status = status;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Date firstDay) {
        this.firstDay = firstDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (machineId != null ? machineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MachineSellers)) {
            return false;
        }
        MachineSellers other = (MachineSellers) object;
        if ((this.machineId == null && other.machineId != null) || (this.machineId != null && !this.machineId.equals(other.machineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "intt.datenlogik.MachineSellers[ machineId=" + machineId + " ]";
    }
    
}
