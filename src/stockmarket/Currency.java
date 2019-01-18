
package stockmarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/**
 *An instance of this class contains the necessary parameters of a currency.
 */
public class Currency extends Asset {
    private ArrayList<String> countries;
    private HashMap<Currency, Double> rates;
    private ArrayList<Currency> otherCurrencies = new ArrayList<>();
    private ArrayList<Double> otherRates= new ArrayList<>();
    private Double amount;
    private Double basicRate;
    private Double firstRate;
    private int timesSold = 0;
    
    /**
     *Creates a new instance of the Currency class.
     * 
     * @param name              the name of the currency; if the string is empty
     *                          generates a new name using the <code>NameGenerator</code>
     * @throws NoNamesException when all of the names have been used; the user
     *                          must provide a name.
     */
    public Currency(String name) throws NoNamesException {
        super(name);
        if ("".equals(name)) this.name = Main.nameGenerator.generateName(this);
        else this.name = name;
        
        //generate list of countries
        this.countries = new ArrayList<>();
        String countriesAll[] = Locale.getISOCountries();
        int howMany = new Random().nextInt(10);
        for (int i=0; i<howMany; i++) {
            int x;
            do {
                x = new Random().nextInt(200);
            }
            while(countries.contains(countriesAll[x]));
            countries.add(countriesAll[x]);            
        }
        //generate list of rates
        this.rates = new HashMap<>();
        this.basicRate = new Random().nextDouble()+(new Random().nextInt(100)+5);
        this.amount = new Random().nextDouble()*10000;
        this.firstRate = basicRate;
        for (int i=0; i<Main.currencyMarket.getCurrencies().size(); i++) {
            double rate = new Random().nextDouble()*(new Random().nextInt(4)+1);
            Currency other = Main.currencyMarket.getCurrencies().get(i);
            this.rates.put(other, rate);
            this.otherCurrencies.add(other);
            this.otherRates.add(rate);
            try {
                other.addRate(this);
            }
            catch(CopyException e) {}
  
        }

    }
    
    /**
     *Gets the rate of the basic currency at the time of creating the object.
     * @return  the rate
     */
    public Double getFirstRate() {
        return firstRate;
    }
  
    
    /**
     *Adds the rate of a new currency. Called when a new currency is added.
     * @param c                 the new currency
     * @throws CopyException    when the rate has already been added
     */
    public void addRate(Currency c) throws CopyException    {
        if (this.rates.containsKey(c)) throw new CopyException();
        double rate = new Random().nextDouble()+new Random().nextInt(10)+1;
        this.rates.put(c, rate);
        this.otherCurrencies.add(c);
        this.otherRates.add(rate);
    }

    public Double getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(Double basicRate) {
        this.basicRate = basicRate;
    }
    
    /**
     *Calculates the price of a unit of this currency in the "basic currency" 
     */
    public void generateValue() {
        double tempRate = basicRate;
        tempRate = tempRate*(timesSold+1)*(new Random().nextDouble()+0.5);
        if(tempRate<0.01) tempRate = 1.0;
        addChartEntry(tempRate, Main.timeUnit);
        basicRate = tempRate;
       
    }

    public double exchange(Currency c, double amount) {
        double rate = this.rates.get(c);
        return amount/rate;             

    }
 

    public void sell(double amount) throws NotEnoughException {
        if (amount>this.amount) throw new NotEnoughException(this);
        this.amount-=amount;

    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public HashMap<Currency, Double> getRates() {
        return rates;
    }

    /**
     *Used to restore the rates HashMap after reading the saved file.
     */
    public void setRates() {
        
        for (int i=0; i<Main.currencyList.size()-1; i++) {
            rates.put(otherCurrencies.get(i), otherRates.get(i));
        }
    }
    
    

    
}
