/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import static stockmarket.Main.timeUnit;

/**
 *An instance of <code>Share</code> is created when a company issues shares or
 * an investor wants to buy them.
 * 
 */
public class Share extends Asset {
    private double value;
    private Company company;
    private int numberLeftToSell;
    private Exchange exchange;
    private int number;
    
    /**
     *Creates an instance of <code>Share</code>.
     * @param company   the company
     * @param exchange  the exchange
     * @param name      the name
     * @param number    the number
     */
    public Share(Company company, Exchange exchange, String name, int number) {
        super(name);
        this.value = company.getCurrentPrice()*number;
        this.company = company;
        this.numberLeftToSell = number;
        this.exchange=exchange;
        this.number = number;
    }

    public double getValue() {
        return value;
    }

    public void generateValue() {
        value = company.getCurrentPrice()/company.getNumberOfShares();
        addChartEntry(value, timeUnit);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company partnership) {
        this.company = partnership;
    }

    public int getNumberLeftToSell() {
        return numberLeftToSell;
    }

    public void setNumberLeftToSell(int numberLeftToSell) {
        this.numberLeftToSell = numberLeftToSell;
    }

    
    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
