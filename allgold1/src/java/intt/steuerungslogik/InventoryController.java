package intt.steuerungslogik;

import intt.datenlogik.Inventory;
import intt.steuerungslogik.util.JsfUtil;
import intt.steuerungslogik.util.PaginationHelper;
import intt.geschaeftslogik.InventoryFacade;

import java.io.Serializable;
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
import java.util.List;

@Named("inventoryController")
@SessionScoped
public class InventoryController implements Serializable {

    private Inventory current;
    private DataModel items = null;
    @EJB
    private intt.geschaeftslogik.InventoryFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public InventoryController() {
    }

    public Inventory getSelected() {
        if (current == null) {
            current = new Inventory();
            selectedItemIndex = -1;
        }
        return current;
    }

    private InventoryFacade getFacade() {
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
        current = (Inventory) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Inventory();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("ressources/Bundle").getString("InventoryCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("ressources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Inventory) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("ressources/Bundle").getString("InventoryUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("ressources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Inventory) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("ressources/Bundle").getString("InventoryDeleted"));
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

    public Inventory getInventory(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    //################## von uns veränderter Code ######################

   public String prepareInventoryByStation()
    {
       current = (Inventory) getItems().getRowData();
       selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       return "Stationsbestand";
    }
    
    public List<Inventory> getInventoryByStation(java.lang.Integer stationID)
    {
       return ejbFacade.inventoryByStation(stationID);
    }
    
    // Produktbestand
    
    public List<Inventory> getInventoryByProductOrder(java.lang.Integer productID)
    {
       return ejbFacade.getItemOrderBy("productID", productID, "stationID");
    }
    
    public String prepareInventoryByProduct()
    {
       current = (Inventory) getItems().getRowData();
       selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
       return "Produktbestand";
    }
    
    //Gesamtbestand eines Produktes
    public String prepareInventoryByOneProductAmount()
    {
       current = (Inventory) getItems().getRowData();
       return "GleP";
    }
    
    public Integer getSelectedProductAmount(java.lang.Integer productID)
    {
       List<Inventory> inventoryList = ejbFacade.inventoryByProduct(productID);
       
       int amount = 0;
       Iterator<Inventory> iterator = inventoryList.iterator();
       while(iterator.hasNext())
       {
          Inventory inv = (Inventory) iterator.next();
          amount += inv.getCurrentAmount();
       }
       return amount;
    }
    
    //Gesamtbestand aller Produkte
    public String prepareInventoryByAllProductAmount()
    {
       return "GlaP";
    }
    
    
    //##################################################################
    @FacesConverter(forClass = Inventory.class)
    public static class InventoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InventoryController controller = (InventoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inventoryController");
            return controller.getInventory(getKey(value));
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
            if (object instanceof Inventory) {
                Inventory o = (Inventory) object;
                return getStringKey(o.getInventoryID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Inventory.class.getName());
            }
        }

    }

}
