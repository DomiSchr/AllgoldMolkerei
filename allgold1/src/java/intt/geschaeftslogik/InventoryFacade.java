/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intt.geschaeftslogik;

import intt.datenlogik.Inventory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Fabio
 */
@Stateless
public class InventoryFacade extends AbstractFacade<Inventory> {

    @PersistenceContext(unitName = "allgold1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InventoryFacade() {
        super(Inventory.class);
    }
    
    //######### von uns erstellten code ######
    public List <Inventory> inventoryByStation(java.lang.Integer stationID)
    {
        List<Inventory> inv = em.createNamedQuery("Inventory.findByStationID").setParameter("stationID", stationID).getResultList();
        return inv;
    }
    

     public List<Inventory> inventoryByProduct(java.lang.Integer productID)
    {
       List<Inventory> inv = em.createNamedQuery("Inventory.findByProductID")
               .setParameter("productID", productID)
               .getResultList();
       return inv;
    }
    
    
    
    //########################################
    
}
