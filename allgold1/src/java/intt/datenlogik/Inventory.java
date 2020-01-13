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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fabio
 */
@Entity
@Table(name = "inventory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i"),
    @NamedQuery(name = "Inventory.findByInventoryID", query = "SELECT i FROM Inventory i WHERE i.inventoryID = :inventoryID"),
    @NamedQuery(name = "Inventory.findByStationID", query = "SELECT i FROM Inventory i WHERE i.stationID = :stationID"),
    @NamedQuery(name = "Inventory.findByProductID", query = "SELECT i FROM Inventory i WHERE i.productID = :productID"),
    @NamedQuery(name = "Inventory.findByCurrentAmount", query = "SELECT i FROM Inventory i WHERE i.currentAmount = :currentAmount"),
    @NamedQuery(name = "Inventory.findByTargetAmount", query = "SELECT i FROM Inventory i WHERE i.targetAmount = :targetAmount")})
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "inventoryID")
    private Integer inventoryID;
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
    @Column(name = "currentAmount")
    private int currentAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "targetAmount")
    private int targetAmount;

    public Inventory() {
    }

    public Inventory(Integer inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Inventory(Integer inventoryID, int stationID, int productID, int currentAmount, int targetAmount) {
        this.inventoryID = inventoryID;
        this.stationID = stationID;
        this.productID = productID;
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
    }

    public Integer getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(Integer inventoryID) {
        this.inventoryID = inventoryID;
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

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventoryID != null ? inventoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.inventoryID == null && other.inventoryID != null) || (this.inventoryID != null && !this.inventoryID.equals(other.inventoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "intt.datenlogik.Inventory[ inventoryID=" + inventoryID + " ]";
    }
    
}
