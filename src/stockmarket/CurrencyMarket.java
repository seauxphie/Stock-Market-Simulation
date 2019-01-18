/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The currency market.
 */
public class CurrencyMarket extends Market {
    private ArrayList<Currency>currencies;
    private HashMap<Currency, HashMap<Currency, Double>> rates;
        
    public CurrencyMarket() {
        this.name = "Currency Market";
        this.currencies = new ArrayList<>();
        rates = new HashMap<>();
        this.markup = 0.1;
        
    }

    public void setCurrencies(ArrayList<Currency> currencies) {
        this.currencies = currencies;
    }


    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public HashMap<Currency, HashMap<Currency, Double>> getRates() {
        return rates;
    }
    
    /**
     * Adds a currency to the market
     * @param c the new currency.
     */
    public void add(Currency c) {
        this.currencies.add(c);
        HashMap<Currency, Double> cRates = new HashMap<>();
        
        for (Map.Entry<Currency, HashMap<Currency, Double>> entry : rates.entrySet()) {
            if (entry.getKey()!=c) {
                Double rate = new Random().nextDouble();
                entry.getValue().put(c, rate);
                cRates.put(entry.getKey(), 1/rate);
            }
        }
        rates.put(c, cRates);
    }
    
    
}
