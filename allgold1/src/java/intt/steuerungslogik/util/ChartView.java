/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intt.steuerungslogik.util;

import intt.datenlogik.Inventory;
import intt.datenlogik.Products;
import intt.datenlogik.Verkaeufe;
import intt.geschaeftslogik.VerkaeufeFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author vlabadmin
 */
@ManagedBean
public class ChartView implements Serializable {

    private PieChartModel pieModel;
    private BarChartModel barModel;
    private LineChartModel animatedModel;
    private BarChartModel barModel2;

    @EJB
    private intt.geschaeftslogik.ProductsFacade productFacade;
    @EJB
    private intt.geschaeftslogik.InventoryFacade inventoryFacade;
    @EJB
    private intt.geschaeftslogik.VerkaeufeFacade verkaufFacade;
    
    @PostConstruct
    public void init()
    {
       createPieModels();
       createBarModels();
       createAnimatedModels();
    }
    
    // Tortendiagramme
    public PieChartModel getPieModel()
    {
      return pieModel;
    }
    
    private void createPieModels()
    {
      createPieModel();
    }
    
    //Anteile eins Produktes am Gesamtlagerbestand
    private void createPieModel()
    {
      pieModel = new PieChartModel();
      
      List<Products> inventoryList = productFacade.allProducts();
      
      Iterator<Products> iterator = inventoryList.iterator();
      while(iterator.hasNext())
      {
        Products prod = (Products) iterator.next();
        pieModel.set(prod.getName(), getSelectedProductAmount(prod.getProductID()));
      }
      
      pieModel.setTitle("Anteile eines Produktes am Gesamtlagerbestand");
      pieModel.setLegendPosition("e");
      pieModel.setFill(true);
      pieModel.setShowDataLabels(true);
      pieModel.setDiameter(150);
    }
    
    //Balkendiagramme
    public BarChartModel getBarModel()
    {
      return barModel;
    }
    
    private void createBarModels()
    {
      createBarModel();
      createBarModel2();
    }
    
    private void createBarModel()
    {
      barModel = initBarModel();
      
      barModel.setTitle("Produktinformationen");
      barModel.setLegendPosition("ne");
      
      Axis xAxis = barModel.getAxis(AxisType.X);
      xAxis.setLabel("Produkte");
      
      Axis yAxis = barModel.getAxis(AxisType.Y);
      yAxis.setLabel("Preise / Kosten");
      yAxis.setMin(0);
      yAxis.setMax(4);
      
    }
    
    
    private BarChartModel initBarModel()
    {
      BarChartModel model = new BarChartModel();
      
      ChartSeries price = new ChartSeries();
      price.setLabel("Preis");
      
      ChartSeries cost = new ChartSeries();
      cost.setLabel("Herstellkosten");
      
      List<Products> inventoryList = productFacade.allProducts();
      
      Iterator<Products> iterator = inventoryList.iterator();
      while(iterator.hasNext())
      {
        Products prod = (Products) iterator.next();
        price.set(prod.getName(), prod.getPrice());
        
        //Zufallsgenerator für Produktionskosten
        String randomnumber = "";
        Random randomgenerator = new Random();
        
        for(int i=0; i<1; i++)
        {
          int zahl = randomgenerator.nextInt(2);
          randomnumber += zahl;
        }
        for(int i=0; i<1; i++)
        {
          int zahl = randomgenerator.nextInt(99);
          randomnumber += "." + zahl;  
        }
        cost.set(prod.getName(), Float.parseFloat(randomnumber));
        
      }
      model.addSeries(price);
      model.addSeries(cost);
      
      return model;              
    }
    
    // Animiertes Diagramm
    public LineChartModel getAnimatedModel()
    {
       return animatedModel;
    }
    
    private void createAnimatedModels() {
        animatedModel = initLinearModel();
        animatedModel.setTitle("Line Chart");
        animatedModel.setAnimate(true);
        animatedModel.setLegendPosition("se");
        Axis yAxis = animatedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
 
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
        model.addSeries(series1);
        model.addSeries(series2);
         
        return model;
    }

    
    //Selector
    public void itemSelect (ItemSelectEvent event)
    {
      FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                        "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    public Integer getSelectedProductAmount(java.lang.Integer productID)
    {
       List<Inventory> inventoryList = inventoryFacade.inventoryByProduct(productID);
       
       int amount = 0;
       Iterator<Inventory> iterator = inventoryList.iterator();
       while(iterator.hasNext())
       {
          Inventory inv = (Inventory) iterator.next();
          amount += inv.getCurrentAmount();
       }
       return amount;
    }
    
    public BarChartModel getBarModel2()
    {
      return barModel2;
    }
    
    private void createBarModels2()
    {
      createBarModel2();
    }
    
    private void createBarModel2()
    {
      barModel2 = initBarModel2();
      
      barModel2.setTitle("Produktumsatz");
      barModel2.setLegendPosition("ne");
      
      Axis xAxis = barModel2.getAxis(AxisType.X);
      xAxis.setLabel("Produkte");
      
      Axis yAxis = barModel2.getAxis(AxisType.Y);
      yAxis.setLabel("Umsatz");
      yAxis.setMin(0);
      yAxis.setMax(500);
      
    }
    
    
    private BarChartModel initBarModel2()
    {
      BarChartModel model = new BarChartModel();
      
      ChartSeries price = new ChartSeries();
      price.setLabel("Umsatz");
      
      
      List<Products> inventoryList = productFacade.allProducts();
      
      Iterator<Products> iterator = inventoryList.iterator();
      while(iterator.hasNext())
      {
        Products prod = (Products) iterator.next();
        price.set(prod.getName(), getSelectedVerkaeufeAmount(prod.getProductID()));
        
     
      }
      model.addSeries(price);

      
      return model;              
    }
    
     public BigDecimal getSelectedVerkaeufeAmount(java.lang.Integer productID)
    {
       List<Verkaeufe> verkaeufeList = verkaufFacade.salesByProduct(productID);
       
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
         List<Products> produktpreis = productFacade.priceByProduct(productID);
       Iterator<Products> iterator2 = produktpreis.iterator();
       while(iterator2.hasNext())
       {
          Products inv2 = (Products) iterator2.next();
          preis = inv2.getPrice();
       }
       return preis.multiply(amount);
    }

      
}
