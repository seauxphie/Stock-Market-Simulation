/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;
import java.util.Random;



/**
 *A Company object stores all of the necessary information about a company.
 */
public class Company implements Serializable {
    private String name = "Default Name";
    private LocalDate dateOfFirstEvaluation;
    private double openingPrice;
    private double currentPrice;
    private double minPrice;
    private double maxPrice;
    private int numberOfShares;
    private int numberOfSharesOutstanding;
    private int numberOfTreasuryShares;
    private double profit;
    private double revenue;
    private double equity;
    private double shareCapital;
    private int volume;
    private double turnover;
    private ArrayList<Exchange>exchanges = new ArrayList<>();
    private ArrayList<Index> indexes = new ArrayList<>();
    private ArrayList<Share>shares = new ArrayList<>();

    private int sharesOut=0;
    private int sharesBack=0;
   
    public Company(String name) throws NoExchangesException, NoNamesException, DoesntExistException {
        if (Main.exchangeList.isEmpty()) throw new NoExchangesException();
        if (Main.indexList.isEmpty()) throw new DoesntExistException("Add an index first");
        if ("".equals(name)) this.name = Main.nameGenerator.generateName(this);
        else this.name = name;
        this.dateOfFirstEvaluation = LocalDate.now();
        this.openingPrice = new Random().nextDouble()*1000;
        this.currentPrice = openingPrice;
        this.minPrice = openingPrice;
        this.maxPrice = openingPrice;
        this.numberOfShares = 5000;
        this.profit = new Random().nextDouble()*1000; //przychód - liczba sprzedanych akcji * wartość
        this.revenue = profit*new Random().nextDouble()*1000; //dochód - przychód pomnijeszony o losowe koszty
        this.equity = new Random().nextDouble()*1000;
        this.shareCapital = new Random().nextDouble()*1000;
        this.volume = new Random().nextInt(1000);
        this.turnover = new Random().nextDouble()*10000;
        this.numberOfSharesOutstanding=0;
        this.numberOfTreasuryShares=numberOfShares;
        int i;
        i=new Random().nextInt(Main.exchangeList.size());
        Exchange e = Main.exchangeList.get(i);
        Share s =new Share(this, e, this.name + " shares - " + e.getName(), 5000);
        this.exchanges.add(e);
        e.addCompany(this,s);
        this.shares.add(s);
        Main.shareList.add(s);
        e.getShares().add(s);


    }


    public void setDateOfFirstEvaluation(LocalDate dateOfFirstEvaluation) {
        this.dateOfFirstEvaluation = dateOfFirstEvaluation;
    }

    public void setOpeningPrice(double openingPrice) {
        this.openingPrice = openingPrice;
    }

    public void setShareCapital(double shareCapital) {
        this.shareCapital = shareCapital;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfFirstEvaluation() {
        return dateOfFirstEvaluation;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public double getProfit() {
        return profit;
    }

    public double getRevenue() {
        return revenue;
    }

    public double getEquity() {
        return equity;
    }

    public double getShareCapital() {
        return shareCapital;
    }

    public int getVolume() {
        return volume;
    }

    public double getTurnover() {
        return turnover;
    }

    public int getNumberOfSharesOutstanding() {
        return numberOfSharesOutstanding;
    }

    public int getNumberOfTreasuryShares() {
        return numberOfTreasuryShares;
    }  
      
    /**
     *Sells shares to an investor.
     * 
     * @param s         the shares to be sold
     * @param number    the number of shares
     * @param e         the exchange
     */
    public synchronized void sellShares(Share s, int number, Exchange e) {
        this.numberOfSharesOutstanding+=number;
        this.numberOfTreasuryShares-=number;
        this.turnover+=this.currentPrice*(1-e.getMarkup())*number;
        volume++;
        sharesOut+=number;
        
    } 
    
    /**
     * Buys the shares from the investor who wants t return them.
     *
     * @param s         the shares to be returned
     * @param number    the number of shares
     * @param price     the price 
     */
    public synchronized void buyShares(Share s, int number, double price) {
        numberOfSharesOutstanding-=number;
        numberOfShares-=number;
        revenue-=price;
        turnover-=price;
        sharesBack+=number;
    }  
    
    /**Issues a random number of shares [0-9] if few shares have been returned.
     *
     * @param e                     the exchange where the shares are to be issued
     * @throws DoesntExistException when the company is not in that exchange
     */
    public void issueShares(Exchange e) throws DoesntExistException {
        if(!exchanges.contains(e)) throw new DoesntExistException();
        if (sharesBack<sharesOut+10) {
            int n = new Random().nextInt(10);
            this.numberOfShares+=n;
            this.numberOfTreasuryShares+=n;
            

            
            e.addShares(this, n);

        }
    }
    
    public void generateProfit() {
        profit = numberOfSharesOutstanding*currentPrice;        
    }
    
    public void generateRevenue() {
        revenue = profit*new Random().nextDouble();
    }
    
    /**
     *Calculates the current price of the company based on the value
     * of its indexes.
     *
     * @return current price
     */
    public double generatePrice() {
        double tempPrice;
        tempPrice=0;
        for (int i=0; i<indexes.size();i++) tempPrice+=indexes.get(i).getValue();
        tempPrice/=(indexes.size()+1);
        if (tempPrice<0.01 || tempPrice == Double.NaN) tempPrice = 0.1;
        if (tempPrice>1000000) tempPrice=999999+new Random().nextInt(100);
        if (tempPrice>maxPrice) maxPrice=tempPrice;
        if (tempPrice<minPrice) minPrice = tempPrice;
        
        sharesBack=0;
        sharesOut=0;
        
        currentPrice = tempPrice;

        return currentPrice;
    }

    public ArrayList<Exchange> getExchanges() {
        return exchanges;
    }
    
    
    public void addToIndex(Index i) {
        indexes.add(i);
    }
    
    public double getSalesRatio() {
        return sharesOut/sharesBack;
    }
    
    /**
     *Joins the specified exchange and issues shares on it.
     * @param e the exchange which the company is trying o join
     * @throws CopyException when it already is a member of that exchange
     */
    public void joinExchange (Exchange e) throws CopyException {
        if (this.exchanges.contains(e)) throw new CopyException();
        this.exchanges.add(e);
        Share s = new Share(this, e, this.name + " shares", 1000);
        numberOfShares+=1000;
        numberOfTreasuryShares+=1000;
        this.shares.add(s);
        Main.shareList.add(s);
    }

    public ArrayList<Share> getShares() {
        return shares;
    }
    
    

}
