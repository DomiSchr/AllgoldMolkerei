/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intt.geschaeftslogik;

import intt.datenlogik.Inventory;
import intt.datenlogik.Products;
import intt.datenlogik.Verkaeufe;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Fabio
 */
@Stateless
public class VerkaeufeFacade extends AbstractFacade<Verkaeufe> {

    @PersistenceContext(unitName = "allgold1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VerkaeufeFacade() {
        super(Verkaeufe.class);
    }
    
       public List<Verkaeufe>allVerkaeufe()
    {
      List<Verkaeufe> prod = em.createNamedQuery("Verkaeufe.findAll").getResultList();
      return prod;
    }
       
          public List <Verkaeufe> salesByStation(java.lang.Integer stationID)
    {
        List<Verkaeufe> inv = em.createNamedQuery("Verkaeufe.findByStationID").setParameter("stationID", stationID).getResultList();
        return inv;
    }
       
            public List<Verkaeufe> salesByProduct(java.lang.Integer productID)
    {
       List<Verkaeufe> inv = em.createNamedQuery("Verkaeufe.findByProductID")
               .setParameter("productID", productID)
               .getResultList();
       return inv;
    }
    
}
