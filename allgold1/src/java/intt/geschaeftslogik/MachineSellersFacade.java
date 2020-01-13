/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intt.geschaeftslogik;

import intt.datenlogik.MachineSellers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Fabio
 */
@Stateless
public class MachineSellersFacade extends AbstractFacade<MachineSellers> {

    @PersistenceContext(unitName = "allgold1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MachineSellersFacade() {
        super(MachineSellers.class);
    }
    
}
