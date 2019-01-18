/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.Random;
import static stockmarket.Main.timeUnit;

/**
 *An instance of <code>RawMaterial</code> contains the parameters of a
 * raw material.
 * @author Zosia
 */
public class RawMaterial extends Asset {
    private String unit;
    private Currency currency;
    private double value;
    private double minValue;
    private double maxValue;
    private double amount;
    private double timesSold;
    private double timesBought;
    private final double firstValue;
   
    /**
     *Creates a <code>RawMaterial</code> object with the soecified name. If
     * the name is empty, it is generatedusing the <code>NameGenerator</code>.
     * @param name                  the name
     * @throws NoNamesException     when there are no names available
     * @throws DoesntExistException when there is no currency.
     */
    public RawMaterial(String name) throws NoNamesException, DoesntExistException {
        super(name);
        if("".equals(name)) this.name = Main.nameGenerator.generateName(this);
        else this.name = name;
        this.amount = new Random().nextDouble()+ new Random().nextInt(10000)+5000;
        this.value = new Random().nextInt(200) + new Random().nextDouble();
        firstValue = value;
        this.timesSold=0;
        this.timesBought=0;
        this.minValue = value;
        this.maxValue = value;
        if (Main.currencyList.isEmpty()) throw new DoesntExistException("Add a currency first.");
        this.currency = Main.currencyList.get(new Random().nextInt(Main.currencyList.size()));
        this.unit = Main.nameGenerator.generateUnit();
        
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     *Generates the value of the raw material using the ratio of the number of
     * buyers to the number of sellers.
     */
    public void generateValue() {
        double tempValue=value;
        tempValue=(timesSold+1)/(timesBought+1)*tempValue*(new Random().nextDouble()+0.5);
        if(tempValue<1) tempValue = 1;
        if (tempValue < minValue) minValue = tempValue;
        else if (tempValue > maxValue) maxValue = tempValue;
        addChartEntry(tempValue, timeUnit);
        timesBought=0;
        timesSold=0;
        value = tempValue;
    }

    public String getUnit() {
        return unit;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getFirstValue() {
        return firstValue;
    }

    public double getAmount() {
        return amount;
    }
    
    /**
     *Called when a <code>MarketInvestor</code> wants to buy it
     * @param amount                the amount
     * @throws NotEnoughException   when thhe investor wants to buy more than there is
     */
    public void sell (double amount) throws NotEnoughException {
        if (amount>this.amount) throw new NotEnoughException(this);
        this.amount-=amount;
        this.timesSold++;
    }

    /**
     *Called when a <code>MarketInvestor</code> wants to sell it
     * @param amount    the amount
     */
    public void buy (double amount) {
        this.amount+=amount;
        this.timesBought++;
    }

}
