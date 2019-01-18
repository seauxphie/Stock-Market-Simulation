package stockmarket;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * the parent class of raw materials, currecies, shares and participation units.
 * 
 */
public class Asset implements Serializable {
    protected String name;
    /**
     * Przechowuje argumenty funkcji zmiany wartości elementu w czasie
     */
    private ArrayList<Long>chartTimes;
    /**
     * Przechowuje wartości funkcji zmiany wartości elementu w czasie
     */
    private ArrayList<Double>chartValues;
    
    public Asset(String name) {
        this.name = name;
        chartTimes = new ArrayList<>();
        chartValues = new ArrayList<>();
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void buy() {
        
    }
    public void sell() {
        
    }
    
    public StringProperty getNameProperty() {
        return new SimpleStringProperty(this, name);
    }

    public ArrayList<Long> getChartTimes() {
        return chartTimes;
    }

    public ArrayList<Double> getChartValues() {
        return chartValues;
    }

    protected void addChartEntry(Double v, long t) {
        if (chartTimes.size()>5000) {
            chartTimes.remove(0);
            chartValues.remove(0);
        }
        chartTimes.add(t);
        chartValues.add(v);
    }
    
    
    
}
