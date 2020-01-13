package intt.steuerungslogik;

import intt.geschaeftslogik.ProductsFacade;
import intt.datenlogik.Verkaeufe;
import intt.datenlogik.Products;
import intt.steuerungslogik.util.JsfUtil;
import intt.steuerungslogik.util.PaginationHelper;
import intt.geschaeftslogik.VerkaeufeFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;


@Named("verkaeufeController")
@SessionScoped
public class VerkaeufeController implements Serializable {

    private Verkaeufe current;
    private DataModel items = null;
    @EJB
    private intt.geschaeftslogik.VerkaeufeFacade ejbFacade;
    @EJB
    private intt.geschaeftslogik.ProductsFacade prodFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public VerkaeufeController() {
    }

    public Verkaeufe getSelected() {
        if (current == null) {
            current = new Verkaeufe();
            selectedItemIndex = -1;
        }
        return current;
    }

    private VerkaeufeFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Verkaeufe) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Verkaeufe();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("ressources/Bundle").getString("VerkaeufeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("ressources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Verkaeufe) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("ressources/Bundle").getString("VerkaeufeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("ressources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Verkaeufe) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("ressources/Bundle").getString("VerkaeufeDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("ressources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Verkaeufe getVerkaeufe(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    //################## von uns veränderter Code ######################

   public String prepareVerkaeufeByStation()
    {
       current = (Verkaeufe) getItems().getRowData();
       selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       return "Stationsbestand";
    }
    
    public List<Verkaeufe> getVerkaeufeByStation(java.lang.Integer stationID)
    {
       return ejbFacade.salesByStation(stationID);
    }
    
    // Produktbestand
    
    public List<Verkaeufe> getVerkaeufeByProductOrder(java.lang.Integer productID)
    {
       return ejbFacade.getItemOrderBy("productID", productID, "stationID");
    }
    
    public String prepareVerkaeufeByProduct()
    {
       current = (Verkaeufe) getItems().getRowData();
       selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       return "Produktbestand";
    }
    
    //Gesamtbestand eines Produktes
    public String prepareVerkaeufeByOneProductAmount()
    {
       current = (Verkaeufe) getItems().getRowData();
       return "VkeP";
    }
    
    public BigDecimal getSelectedVerkaeufeAmount(java.lang.Integer productID)
    {
       List<Verkaeufe> verkaeufeList = ejbFacade.salesByProduct(productID);
       
       BigDecimal amount = new BigDecimal(0);
       BigDecimal preis = new BigDecimal(0);
       Iterator<Verkaeufe> iterator = verkaeufeList.iterator();
       while(iterator.hasNext())
       {
          Verkaeufe inv = (Verkaeufe) iterator.next();
          amount = amount.add(new BigDecimal(inv.getAnzahl()));
       }
       
     //  List<Products> produktpreis = prodFacade.getItemOrderBy("price", productID, "productID");
     //   preis = produktpreis.get(0).getPrice();
         List<Products> produktpreis = prodFacade.priceByProduct(productID);
       Iterator<Products> iterator2 = produktpreis.iterator();
       while(iterator2.hasNext())
       {
          Products inv2 = (Products) iterator2.next();
          preis = inv2.getPrice();
       }
       return preis.multiply(amount);
    }
    
    //Gesamtbestand aller Produkte
    public String prepareInventoryByAllProductAmount()
    {
       return "GlaP";
    }
    
    
    //##################################################################

    @FacesConverter(forClass = Verkaeufe.class)
    public static class VerkaeufeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VerkaeufeController controller = (VerkaeufeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "verkaeufeController");
            return controller.getVerkaeufe(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Verkaeufe) {
                Verkaeufe o = (Verkaeufe) object;
                return getStringKey(o.getVerkaufID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Verkaeufe.class.getName());
            }
        }

    }

}
