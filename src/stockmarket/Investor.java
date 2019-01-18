/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.ArrayList;
import java.util.Random;

/**
 * An instance of this class stores the parameters of an investor.
 * @author Zosia
 */
public class Investor extends MarketInvestor {

    private String PESEL;
    private ArrayList<ParticipationUnit> fundUnits;

    /**
     *Creates an instance of <code>Investor</code> with the specified name.
     * The PESEL is a String of 11 randomly generated digits.
     * @param fullName  the name of the investor
     */
    public Investor(String fullName) {
        super(fullName);
        this.PESEL = generatePESEL();
        this.fundUnits = new ArrayList<>();
        this.budget = new Random().nextDouble()+ new Random().nextInt(500000);
    }
    
    @Override
    public String getName() {
        return fullName;
    }
    
    private String generatePESEL() {
        String p= "";
        for (int i=0; i<11; i++) {
            p=p+Integer.toString(new Random().nextInt(10));
        }
        return p;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }


    public String getPESEL() {
        return PESEL;
    }

    /**
     *Buys a participation unit of a fund.
     * @param f                 the fund
     * @throws NoMoneyException if the investor does not have enough money
     */
    public void buyFundUnit (Fund f) throws NoMoneyException {
        if (budget<1000.0) throw new NoMoneyException(f);
        ParticipationUnit u = new ParticipationUnit(f, this, f.getFundName()+ " - " + this.getFullName());
        capital.put(u, 1.0);
        fundUnits.add(u);
        synchronized(this) {
            budget-=1000.0;
            f.sellUnit(this);
                     
        }
    }
    
    /**
     *Sells a participation unit.
     * @throws DoesntExistException When the investor owns no participation units.
     */
    public void sellFundUnit () throws DoesntExistException {
        if (fundUnits.isEmpty()) throw new DoesntExistException(this.getFullName() + " is not a member of any investment fund");
        int index = new Random().nextInt(fundUnits.size());
        ParticipationUnit u = fundUnits.get(index);
        try {
        u.getFund().buyUnit(this);
        fundUnits.remove(index);
        }
        catch(DoesntExistException e) {
            throw new DoesntExistException(this.getFullName() + " is not a member of "+ u.getFund().getFundName());
        }
        
    }

    public ArrayList<ParticipationUnit> getFundUnits() {
        return fundUnits;
    }
    


}
