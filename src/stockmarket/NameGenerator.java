/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *This class generates names.
 * @author Zosia
 */
public class NameGenerator {
    private final ArrayList<String>firstNames = new ArrayList<>();
    private final ArrayList<String>familyNames = new ArrayList<>();
    private final ArrayList<String>companyNames = new ArrayList<>();
    private final ArrayList<String>materialNames = new ArrayList<>();
    private final ArrayList<String>currencyNames = new ArrayList<>();
    private final ArrayList<String>indexNames = new ArrayList<>();
    private final ArrayList<String>exchangeNames = new ArrayList<>();
    private final ArrayList<String>fundNames = new ArrayList<>();
    private final ArrayList<String>countryNames = new ArrayList<>();
    private final ArrayList<String>cityNames = new ArrayList<>();
    private final ArrayList<String>unitNames = new ArrayList<>();
    
    
    public NameGenerator() {
        this.firstNames.addAll(Arrays.asList(
                "John", "Dean", "Samuel", "Mark", "Jane", "Emma", "Peter", "Anthony", "Christopher", "Christine",
                "Stephen", "Jordan", "Chandler", "Monica", "Rachel", "Phoebe", "Joseph", "Ross", "Bilbo", "Frodo",
                "Samwise", "Newt", "Natasha", "Lorelai", "Peter", "Aragorn", "Logan", "Erik", "Charles", "Raven" ));
        this.familyNames.addAll(Arrays.asList(
                "Smith", "Winchester", "Holmes", "Watson", "Potter", "Xavier", "Belfort", "Bing", "Buffay", "Green",
                "Geller", "Tribbiani", "Baggins", "Gamgee", "Scamander", "Riddle", "Stark", "Rogers", "Romanoff", "Strange"));
        this.companyNames.addAll(Arrays.asList(
                "Facebook", "Twitter", "Instagram", "Google", "Nestle", "IKEA", "Stark Industries", "Wayne Enterprises", "Pied Piper", "Hooli", 
                "Stratton Oakmont, Inc.", "Oscorp", "ORLEN", "Statoil"));
        this.materialNames.addAll(Arrays.asList(
                "Gold", "Silver", "Coal", "Copper", "Oil", "Platinum", "Aluminum", "Lead", "Coffee", "Vibranium"));
        this.currencyNames.addAll(Arrays.asList(
                "EUR", "PLN", "USD", "GBP", "CHF", "AUD", "JPY", "CNH", "NOK", "BTC"));
        this.indexNames.addAll(Arrays.asList(
                "WIG20", "WIG40", "CAC40", "IGBM", "AEX", "DAX", "WIG", "NASDAQ", "FTSE100", "ABC",
                "XYZ", "IPC", "ATX", "IBEX35", "OSE All Share", "PX", "STH", "PO", "NZX", "TA100"));
        this.exchangeNames.addAll(Arrays.asList(
                "Euronext", "New York Stock Exchange", "JEG", "LSE", "TMX Group", "SIX", "Oslo Bors", "Borsa", "Moscow Exchange", "Deutsche Borse"));
        this.fundNames.addAll(Arrays.asList(
            "Investment Fund 0", "Investment Fund 1", "Investment Fund 2", "Investment Fund 3", "Investment Fund 4", "Investment Fund 5", "Investment Fund 6", "Investment Fund 7", "Investment Fund 8", "Investment Fund 9",
        "Investment Fund A", "Investment Fund B", "Investment Fund C", "Investment Fund D", "Investment Fund E", "Investment Fund F", "Investment Fund G", "Investment Fund H", "Investment Fund I", "Investment Fund J"));
       this.countryNames.addAll(Arrays.asList(
               "USA", "United Kingdom", "Poland", "Russia", "Japan", "Germany", "Norway", "China", "France", "Canada"));
        this.cityNames.addAll(Arrays.asList(
                "New York", "London", "Warsaw", "Moscow", "Tokio", "Berlin", "Oslo", "Beijing", "Paris", "Ottawa"));
        
        this.unitNames.addAll(Arrays.asList(
                "t", "oz", "bbl", "lb",  "kg", "gal"
        ));
    }
    
    /**
     *Generates a new name for a company. There are 15 names available.
     * @param c                 the company
     * @return                  the name
     * @throws NoNamesException when there are no more names.
     */
    public String generateName(Company c) throws NoNamesException {
        if (companyNames.isEmpty()) throw new NoNamesException("No more names available");
        int i = new Random().nextInt(companyNames.size());
        System.out.println(i);
        String name = companyNames.get(i);
        companyNames.remove(i);
        return name;
    }
    
    /**
     *Generates a new name for a currency. There are 10 names available.
     * @param c                 the currency
     * @return                  the name
     * @throws NoNamesException when there are no more names.
     */
    public String generateName(Currency c) throws NoNamesException {
        if (currencyNames.isEmpty()) throw new NoNamesException("No more names available");
        int i = new Random().nextInt(currencyNames.size());
        String name = currencyNames.get(i);
        currencyNames.remove(i);
        return name;
    }
        
    /**
     *Generates a new name for an exchange. There are 10 names available.
     * @param e                 the exchange
     * @return                  the name
     * @throws NoNamesException when there are no more names.
     */
    public String generateName(Exchange e) throws NoNamesException {
        if (exchangeNames.isEmpty()) throw new NoNamesException("No more names available");
        int i = new Random().nextInt(exchangeNames.size());
        String name = exchangeNames.get(i);
        exchangeNames.remove(i);
        return name;
    }

    /**
     *Generates a new name for an invesment fund. There are 20 names available.
     * @param f                 the fund
     * @return                  the name
     * @throws NoNamesException when there are no more names.
     */
    public String generateName(Fund f) throws NoNamesException {
        if (fundNames.isEmpty()) throw new NoNamesException("No more names available");
        int i = new Random().nextInt(fundNames.size());
        String name = fundNames.get(i);
        fundNames.remove(i);
        return name;
    }
    
    /**
     *Generates a new name for a raw material. There are 10 names available.
     * @param m                             the raw material
     * @return                              the name
     * @throws NoNamesException             when there are no more names
     */
    public String generateName(RawMaterial m) throws NoNamesException {
        if (materialNames.isEmpty()) throw new NoNamesException("No more names available");
        int i = new Random().nextInt(materialNames.size());
        String name = materialNames.get(i);
        materialNames.remove(i);
        return name;
        
    }
    
    /**
     *Generates a new name for an index. There are 20 names available.
     * @param index                         the index
     * @return                              the name
     * @throws NoNamesException             when there are no more names
     */
    public String generateName(Index index) throws NoNamesException {
        if (indexNames.isEmpty()) throw new NoNamesException("No more names available");
        int i = new Random().nextInt(indexNames.size());
        String name = indexNames.get(i);
        indexNames.remove(i);
        return name;
    }
    
    /**
     *Generates a new name for a person - a combination of a first and last name.
     * There are 20 last names and 30 first names available. They can be repeated.
     * @return        the name
     * 
     */
    public String generateInvestorName() {
        int i = new Random().nextInt(firstNames.size());
        String name = firstNames.get(i);
        i = new Random().nextInt(familyNames.size());
        name = name + ' '+ familyNames.get(i);
        return name;
    }
    
    /**
     *Generates a country name
     * @return  the name
     */
    public String generateCountry() {
        int i = new Random().nextInt(countryNames.size());
        String name = countryNames.get(i);
        return name;
    }
    
    /**
     *Generates a city name
     * @return the name
     */
    public String generateCity() {
        int i = new Random().nextInt(cityNames.size());
        String name = cityNames.get(i);
        return name;
    }
    
    /**
     *Generates a SI unit name.
     * @return the name
     */
    public String generateUnit() {
        int i = new Random().nextInt(unitNames.size());
        String name = unitNames.get(i);
        return name;
    }
    
    
    
    
    
    
    
    
}
