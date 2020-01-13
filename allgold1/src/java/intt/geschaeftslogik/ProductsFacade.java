/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intt.geschaeftslogik;

import intt.datenlogik.Products;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Fabio
 */
@Stateless
public class ProductsFacade extends AbstractFacade<Products> {

    @PersistenceContext(unitName = "allgold1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }
    
    public List<Products>allProducts()
    {
      List<Products> prod = em.createNamedQuery("Products.findAll").getResultList();
      return prod;
    }
    

    public List<Products>priceByProduct(java.lang.Integer productID)
    {
       List<Products> inv = em.createNamedQuery("Products.findByProductID")
               .setParameter("productID", productID)
               .getResultList();
       return inv;
    }
   
    
    
}
