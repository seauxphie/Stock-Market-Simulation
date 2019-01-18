/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package stockmarket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


import java.util.HashMap;
/**
 *The parent class of <code>Investor</code> and <code>Func</code>.
 * 
 */
public abstract class MarketInvestor implements Serializable {
    protected String fullName; //individualinvestor - imięi nazwisko, fundusz - zarządca
    protected double budget=0.0;
    protected HashMap<Asset, Double> capital=new HashMap<>();
    protected HashMap<RawMaterial, Double>materials=new HashMap<>();
    protected HashMap<Currency, Double>currencies=new HashMap<>();
    
    
    //for saving
    private ArrayList<RawMaterial> myMaterials = new ArrayList<>();
    private ArrayList<Double> materialAmounts = new ArrayList<>();
    private ArrayList<Currency> myCurrencies = new ArrayList<>();
    private ArrayList<Double> currencyAmounts = new ArrayList<>();
    
    protected ArrayList<Share>shares = new ArrayList<>();
    
    /**
     *Creates an object with the name; it it is a fund, the fullName is the name
     * of the manager; in case of an investor - it is the name of the investor
     * @param fullName  the name
     */
    public MarketInvestor(String fullName) {
        if ("".equals(fullName)) this.fullName = Main.nameGenerator.generateInvestorName();
        else this.fullName = fullName;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }


    public double getBudget() {
        return budget;
    }

    public HashMap<Asset, Double> getCapital() {
        return capital;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public HashMap<RawMaterial, Double> getMaterials() {
        return materials;
    }

    public HashMap<Currency, Double> getCurrencies() {
        return currencies;
    }
    
    public ArrayList<Share> getShares() {
        return shares;
    }
    
    public abstract String getName();
    
    /**
     * Buys some raw material. Immediately exchanges the basic currency for the
     * raw material's currency.
     * @param m                     the raw material
     * @param amount                the amount
     * @throws NoMoneyException     when the investor does not have enough money
     * @throws DoesntExistException when the raw material doesn't exist.
     * @throws NotEnoughException   when the amount the market investor is
     *                              trying to buy is bigger than the amount
     *                              of the raw material on the market.
     */
    public void buy(RawMaterial m, double amount)
            throws NoMoneyException, DoesntExistException, NotEnoughException {
        if (!Main.materialMarket.getMaterials().contains(m)) 
                throw new DoesntExistException("Can't find " + m.getName());
        
        double tempPrice = amount*m.getValue()*(1+Main.materialMarket.markup);
        double price = tempPrice*m.getCurrency().getBasicRate();
           
        if (this.budget<price) throw new NoMoneyException(m);

        synchronized (this) {
            try {
                m.sell(amount);
                this.capital.put(m, amount);
                this.materials.put(m, amount);
                this.budget-=price;
            }
            catch (NotEnoughException e) {
                throw e;
            }

        }
        
    }
     
    /**
     *Buys some shares.
     * @param s                         the Share object
     * @param number                    number of shares
     * @param e                         the exchange
     * @throws NoMoneyException         when the investor does not have enough money
     * @throws DoesntExistException     when the Company or Share object does not exist
     *               
     */
    public void buy(Share s, int number, Exchange e) throws NoMoneyException, DoesntExistException {
        double price = s.getValue()*number;
        
        if (this.budget<price) throw new NoMoneyException(s.getCompany());
        if(!e.getCompanies().contains(s.getCompany()))
            throw new DoesntExistException("Can't find " + s.getCompany().getName() + " in " + e.getName());
        
        Share myShare = new Share(s.getCompany(), e, s.getName(), number);
        synchronized(this) {
        try {
                e.sellShare(s, number, this);
                this.capital.put(myShare, (double)number);
                this.shares.add(myShare);
                this.budget-=price;
        }
        catch (DoesntExistException ex) {
            throw ex;   
        }
        
        }

    }

    /**
     *Buys some currency using the basic currency
     * @param c                     the currency
     * @param amount                the amount
     * @throws NoMoneyException     when the market ivestor ddoes not have enough money
     * @throws DoesntExistException when the currency doesn't exist
     */
    public void buy(Currency c, double amount)
            throws NoMoneyException, DoesntExistException {
        if (!Main.currencyMarket.getCurrencies().contains(c))
            throw new DoesntExistException("Can't find " + c.getName());
        if (this.budget<amount*c.getBasicRate()*(1+Main.currencyMarket.markup))
            throw new NoMoneyException(c);
        
        synchronized(this) {
            try {
                c.sell();
                this.budget-=amount*c.getBasicRate()*(1+Main.currencyMarket.markup);
                this.capital.put(c, amount);
                this.currencies.put(c, amount);
            }
            catch (Exception e) {
                //System.out.println(e.getMessage());
            }
            
        }
        
        
    }

    /**
     *Sells some raw material.
     * @param m                        the raw material
     * @param amount                    the amount
     * @throws DoesntExistException     when the investor does not have this raw material.
     */
    public void sell(RawMaterial m, double amount) throws DoesntExistException{
        if (!this.capital.containsKey(m)) throw new DoesntExistException ("Nothing to sell!");

        this.budget+=amount*m.getValue()*(1-Main.materialMarket.getMarkup());
        this.capital.remove(m);
        this.materials.remove(m);
        m.buy(amount);
        
        
    }
     
    /**
     *Sells all of the shares of a company to a company
     * @param s the shares.
     */
    public void sell(Share s) {
        try {
             Double price = s.getCompany().getCurrentPrice()*(new Random().nextDouble()+0.5)*s.getNumber();
             Exchange e = s.getExchange();
             Share exchangeShare = null;
             for (int i=0; i<e.getShares().size(); i++) {
                 if (e.getShares().get(i).getCompany() == s.getCompany()) {
                     exchangeShare = e.getShares().get(i); break;
                 } 
             }
             if (exchangeShare!=null) {
                synchronized(s){
                    s.getCompany().buyShares(s, s.getNumber(), price);
                    exchangeShare.setNumber(exchangeShare.getNumber()-s.getNumber());
                    this.budget+=price;
                    this.capital.remove(s);
                    this.shares.remove(s);
                }
             }
        }
        catch (Exception ex)
        {
            
        }

    }
    
    /**
     *Sells some currency.
     * @param c                     the currency
     * @param amount                the amount
     * @throws DoesntExistException when the investor does not have this currency
     */
    public void sell(Currency c, double amount) throws DoesntExistException
    {
        if (!this.capital.containsKey(c)) throw new DoesntExistException ("Nothing to sell!");
        this.budget+=amount*c.getBasicRate()*(1-Main.currencyMarket.getMarkup());
        this.capital.remove(c);
        this.currencies.remove(c);
        c.buy();
    }
    
    /**
     *Saves the content of hashmaps to a arraylists in order to prevent
     * loss of assets while saving to file.
     */
    public void saveAssets() {
        int size = Main.materialList.size();
        for (int i=0; i<size; i++) {
            RawMaterial m = Main.materialList.get(i);
            if (materials.containsKey(m)) {
                myMaterials.add(m);
                materialAmounts.add(materials.get(m));
            }
        }
        size = Main.currencyList.size();
        for (int i=0; i<size; i++) {
            Currency c = Main.currencyList.get(i);
            if (currencies.containsKey(c)) {
                myCurrencies.add(c);
                currencyAmounts.add(currencies.get(c));
            }
        }
    }
    
    /**
     *Saves assets to hashmaps after reading data from a file.
     */
    public void restoreAssets() {
        for (int i=0; i<myMaterials.size(); i++) {
            materials.put(myMaterials.get(i), materialAmounts.get(i));
        }
        for (int i=0; i<myCurrencies.size(); i++) {
            currencies.put(myCurrencies.get(i), currencyAmounts.get(i));
        }
    }
    
    
}
