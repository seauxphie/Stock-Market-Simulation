/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.ArrayList;

/**
 *An instance of the Fund class stores the parameters of an investment fund.
 * @author Zosia
 */
public class Fund extends MarketInvestor {
    private String fundName;
    private ArrayList<Investor> members;
    private ArrayList<Integer>units;
        
    /**
     *Creates an instance of <code>Fund</code>.
     * @param name              The name of the fund; if empty, it is generated using
     *                          <code>NameGenerator</code>
     * @param fullName          The name of the manager; if empty, it is generated using
     *                          <code>NameGenerator</code>
     * @throws NoNamesException when no new names are available
     */
    public Fund(String name, String fullName) throws NoNamesException {
        super(fullName);
        if("".equals(name)) this.fundName = Main.nameGenerator.generateName(this);
        else this.fundName = name;
        this.members = new ArrayList<>();
        this.budget=0;
    }
    
    public Fund(String name) throws NoNamesException {
        super("");
        if ("".equals(name)) this.fundName = Main.nameGenerator.generateName(this);
        else this.fundName = name;
        this.members = new ArrayList<>();
    }



    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundName() {
        return fundName;
    }
    
    @Override
    public String getName() {
        return fundName;
    }
    
    public String getManager() {
        return fullName;
        
    }
    public void setManager(String f) {
        this.fullName=f;
    }

    public ArrayList<Investor> getMembers() {
        return members;
    }
    
    /**
     *Sells a unit to an <code>Investor</code>.
     * @param i the investor
     */
    public synchronized void sellUnit(Investor i) {
        if (this.members.contains(i)) {
            int index = members.indexOf(i);
            int x = this.units.get(index);
            this.units.set(index, x+1);
        }
        else this.members.add(i);
        this.units.add(1);
        this.budget+=1000.0;
        
    }
    
    /**
     *Buys a participation unit from an investor.
     * @param i                     the investor
     * @throws DoesntExistException when the investor is no a member of the fund
     */
    public synchronized void buyUnit(Investor i) throws DoesntExistException {
        if (!members.contains(i)) throw new DoesntExistException(i.getFullName() + " is not a member of "+ this.getFundName());
        int index = members.indexOf(i);
            int amount = this.units.get(index);
            if(amount==1.0) {
                this.members.remove(index);
                this.units.remove(index);
            }
            else this.units.set(index, amount-1);
            this.budget-=1000;
                
    }

}
