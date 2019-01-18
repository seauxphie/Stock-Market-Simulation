/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.Random;


/**
 *A thread where an investor updates itself and sells or buys assets.
 * @author Zosia
 */
public class InvestorThread extends Thread {

    private final Investor investor;

    /**
     *Creates a new instance of <code>InvsetorThread</code>.
     * @param investor  the investor
     */
    public InvestorThread(Investor investor) {
        this.investor=investor;
    }

    public Investor getInvestor() {
        return investor;
    }
    
    
    
    @Override
    public void run() {
        System.out.println("new InvestorThread " + investor.getFullName());
               
        
        while (!currentThread().isInterrupted()) {

            int addMoney = new Random().nextInt(10000);
            if (addMoney  == 1) {
                investor.setBudget(investor.getBudget()+new Random().nextInt(50000));
            }

            int toBuy = new Random().nextInt(1000);
            int toSell = new Random().nextInt(1000);

            //buy shares

            if (toBuy < 2) {
                int eIndex = 0;
                if (!Main.exchangeList.isEmpty()) {
                    eIndex = new Random().nextInt(Main.exchangeList.size());
                    Exchange e = Main.exchangeList.get(eIndex);
                    synchronized(e.getShares()) {
                        if (!e.getShares().isEmpty()) {
                            Share s;
                            int sIndex = new Random().nextInt(e.getShares().size());
                            s = e.getShares().get(sIndex);
                            try {
                                investor.buy(s, (toBuy+1)*50, e);
                                
                            } catch (NoMoneyException | DoesntExistException exception) {
                                String msg = investor.getFullName() + " error: " + exception.getMessage() + s.getName();
                                
                            } catch (Exception ex) {
                                String msg = investor.getFullName() + " error: " + ex.getMessage();
                                
                            }
                        }
                    }
                }
            }

            //buy currency

            else if (toBuy < 4) {
                if (!Main.currencyMarket.getCurrencies().isEmpty()) {
                    int cIndex = new Random().nextInt(Main.currencyMarket.getCurrencies().size());
                    Currency c = Main.currencyMarket.getCurrencies().get(cIndex);
                    try {
                        investor.buy(c, toBuy*new Random().nextInt(200)+100);
                        //System.out.println(investor.getFullName() + " has bought some " + c.getName());
                    }
                    catch (NoMoneyException | DoesntExistException exception) {
                        String msg = investor.getFullName() + " error: " + exception.getMessage();
                        //System.out.println(msg);
                    }
                }
                }

            //buy material

            else if (toBuy < 6) {                
                    if (!Main.materialMarket.getMaterials().isEmpty()) {
                        int materialIndex = new Random().nextInt(Main.materialMarket.getMaterials().size());
                        RawMaterial material = (RawMaterial) Main.materialMarket.getMaterials().get(materialIndex);
                        try {
                            investor.buy(material, toBuy*new Random().nextInt(10)+5);
                            //System.out.println(investor.getFullName() + " has bought some " + material.getName());
                        }
                        catch (NoMoneyException | DoesntExistException | NotEnoughException exception) {
                            String msg = investor.getFullName() + " error: " + exception.getMessage();
                            //System.out.println(msg);
                        } 

                    }
                }

            //buy a fund unit

            else if (toBuy <8) {
                if (!Main.fundList.isEmpty()) {
                    Fund fund = Main.fundList.get(new Random().nextInt(Main.fundList.size()));
                    try {
                        investor.buyFundUnit(fund);
                        //System.out.println(investor.getFullName() + " has bought a participation unit of " + fund.getFundName());
                    }
                    catch (NoMoneyException exception) {
                        String msg = investor.getFullName() + " error: " + exception.getMessage();
                            //System.out.println(msg);
                    }
                }

            }

            //selling

            if (toSell < 2) {
                if (!investor.getShares().isEmpty() && new Random().nextInt(100)==0) {
                    int index = new Random().nextInt(investor.getShares().size());
                    Share s= investor.getShares().get(index);
                    synchronized(s) {
                        investor.sell(s);
                    }
                }
            }


            else if (toSell < 4) {
                int index;
                RawMaterial m;
                if (!investor.getMaterials().isEmpty()) {
                    synchronized(investor) {
                        index = new Random().nextInt(investor.getMaterials().size());
                        m = (RawMaterial) investor.getMaterials().keySet().toArray()[index];
                    }
                    try {
                        investor.sell(m, toSell*10);
                        //System.out.println(investor.getFullName() + " has sold some " + m.getName());
                        }
                        catch (DoesntExistException exception) {
                            String msg = investor.getFullName() + " error: " + exception.getMessage();
                            //System.out.println(msg);
                    }
                }
            }

            else if (toSell < 6) {
                int index;
                Currency c;
                if (!investor.getCurrencies().isEmpty()) {

                        index = new Random().nextInt(investor.getCurrencies().size());
                        c = (Currency) investor.getCurrencies().keySet().toArray()[index];

                    try {
                        investor.sell(c, toSell*100);
                        //System.out.println(investor.getFullName() + " has sold some " + c.getName());
                        }
                        catch (DoesntExistException exception) {
                            String msg = investor.getFullName() + " error: " + exception.getMessage();
                            //System.out.println(msg);
                        }
                    }
                }

            else if (toSell < 8) {
                int index;
                Currency c;
                if (!investor.getFundUnits().isEmpty()) {
                    try {
                        investor.sellFundUnit();
                        }
                        catch (DoesntExistException exception) {
                            String msg = investor.getFullName() + " error: " + exception.getMessage();
                            //System.out.println(msg);
                        }
                    }
                }

        }
        
        if (currentThread().isInterrupted()) {
           System.out.println("InvestorThread " + investor.getFullName()+ " has stopped");
        }
        
           
    }
    
}
