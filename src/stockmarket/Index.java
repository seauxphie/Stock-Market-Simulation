/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *An instance of this class contains all of the parameters of an exchange index.
 * @author Zosia
 */
public class Index implements Serializable {
    private String name;
    private ArrayList<Company> companies;
    private ArrayList<Integer>companyPerformance;
    private double value;
    private Exchange exchange;
    private int maxCompanies;

    /**
     *Creates an instance of <code>Index</code>.
     * @param name                  the name; if mpty, generated by
     *                              the <code>NameGenerator</code>
     * @throws NoExchangesException when there are no exchanges
     * @throws NoNamesException     when there are no new names
     */
    public Index (String name) throws NoExchangesException, NoNamesException {
        if (Main.exchangeList.isEmpty()) throw new NoExchangesException();
        this.exchange = Main.exchangeList.get(new Random().nextInt(Main.exchangeList.size()));
        this.name=Main.nameGenerator.generateName(this);
        this.companies = new ArrayList<>();
        Main.indexList.add(this);
        maxCompanies = new Random().nextInt(8)+3;
        this.value = 1;
        companyPerformance = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPartnerships(ArrayList<Company> partnerships) {
        this.companies = partnerships;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public double getValue() {
        return value;
    }
    
    /**
     *Adds a company
     * @param c                 the company
     * @throws CopyException    if the company is already in the index.
     */
    public void assignCompany (Company c) throws CopyException {
        if (this.companies.contains(c)) throw new CopyException();
        if (companies.size()<maxCompanies) {
            this.companies.add(c);
            c.addToIndex(this);
            this.companyPerformance.add(0);
        }
        else {
            double minValue = c.getCurrentPrice();
            int minIndex=-1;
            for (int i=0; i<companies.size(); i++) {
                if(companies.get(i).getCurrentPrice()<minValue){
                    minIndex=i;
                    minValue = companies.get(i).getCurrentPrice();
                }
            }
            if(minIndex>=0) {
                companies.remove(minIndex);
                companies.add(c);
                c.addToIndex(this);
                this.companyPerformance.add(0);
            }
        }
    
    }
    
    /**
     *Calculates the value based on the companies' performance.
     */
    public void generateValue () {
        if (companies.size()>1) {
            double turnovers=0;
            double soldShares = 0.0;
            for (int i=0; i<companies.size(); i++) {
                Company c = companies.get(i);
                turnovers+=c.getTurnover();
                soldShares+=companyPerformance.get(i);
                companyPerformance.set(i, 1);
            }
            turnovers/=(companies.size()+1);

            soldShares/=(companies.size()+1);
            Double tempValue=turnovers/soldShares;
            if(tempValue<=0 || tempValue==Double.NaN) tempValue=1.0;
            if (tempValue>1000000) tempValue/=100;

            value = tempValue;
        }
        
    }
    
    /**
     * Increments the "performance" property of a company when its shares
     * are bought
     * @param c the company
     */
    public void setCompanyPerformance(Company c) {
        int i = companies.indexOf(c);
        int x = companyPerformance.get(i);
        companyPerformance.set(i, x+1);
    }
    
    
    
    
    
    
}