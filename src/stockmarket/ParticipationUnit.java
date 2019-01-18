/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

/**
 *An instance of <code>ParticipationUnit</code> is created when an investor
 * becomes a member of a fund.
 */
public class ParticipationUnit extends Asset {
    private Fund fund;
    private MarketInvestor owner;
    private double currentValue;
    private double minValue;
    private double maxValue;

    /**
     *Cretes an instance of <code>ParticipationUnit</code>.
     * @param fund  the fund
     * @param owner the new owner
     * @param name  the name
     */
    public ParticipationUnit(Fund fund, MarketInvestor owner, String name) {
        super(name);
        this.fund = fund;
        this.owner = owner;
    }
    

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public MarketInvestor getOwner() {
        return owner;
    }

    public void setOwner(MarketInvestor owner) {
        this.owner = owner;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double value) {
        this.currentValue = value;
        if (value>this.maxValue) this.maxValue = value;
        if (value<this.minValue) this.minValue = value;
    }
    
    public void generateValue() {
        
    }
    
    
    
    
    
    
}
