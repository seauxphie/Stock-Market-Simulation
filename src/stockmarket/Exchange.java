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
 *An instance of the Exchange class contains all of the parameters
 * of an exchange.
 */
public class Exchange extends Market implements Serializable {
    private String country;
    private Currency currency;
    private String city;
    private String headquarters;
    private ArrayList<Index> indexes = new ArrayList<>();;
    private ArrayList<Company> companies = new ArrayList<>();
    private ArrayList<Share>shares = new ArrayList<>();

    /**
     *Creates an instance of Exchange with the specified name. All of the other
     * field values are randomly generated.
     * @param name              the name; if empty, the name is randomly generated
     * @throws NoNamesException when no new names rae available
     */
    public Exchange (String name) throws NoNamesException {
        if ("".equals(name)) this.name = Main.nameGenerator.generateName(this);
        else this.name = name;
        this.country = Main.nameGenerator.generateCountry();
        this.city = Main.nameGenerator.generateCity();
        this.headquarters = city;
       this.markup = new Random().nextInt(4)/10;
    }
 
    
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public void setIndexes(ArrayList<Index> indexes) {
        this.indexes = indexes;
    }

    public String getCountry() {
        return country;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public ArrayList<Index> getIndexes() {
        return indexes;
    }
    
    public void addIndex (Index i) {
        this.indexes.add(i);
    }
    
    public void addCompany (Company c, Share s) {
        this.companies.add(c);
        this.shares.add(s);
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }
    
    /**
     *Adds shares when a company issues them.
     * @param c         the company
     * @param number    the number of shares
     */
    public void addShares(Company c, int number) {
        
        for (int i=0; i<shares.size(); i++) {
           if (shares.get(i).getCompany() == c) {
               shares.get(i).setNumber(shares.get(i).getNumber()+number);
               break;
           } 
        }
 
    }

    public ArrayList<Share> getShares() {
        return shares;
    }
    
    /**
     *Sells shares to a <code>MarketInvestor</code>.
     * @param s                     the shares
     * @param number                the number of shares
     * @param owner                 the investor
     * @throws DoesntExistException when the <code>Share</code> does not exist
     */
    public void sellShare (Share s, int number, MarketInvestor owner) throws DoesntExistException {
        if (s == null) throw new DoesntExistException ("Someone has already bought it");
        if (!shares.contains(s)) throw new DoesntExistException("Cant find " + s.getName() + " in here");
        synchronized(s) {
            int available = s.getNumberLeftToSell();
            if (available<number) throw new DoesntExistException("Not enough shares to sell [" + Integer.toString(available)+']');
                s.getCompany().sellShares(s, number, this);
                s.setNumberLeftToSell(available-number);
        }
        
    }


    
}
