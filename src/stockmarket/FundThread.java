/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.Random;
import static stockmarket.Main.timeUnit;

/**
 *The thread where a fund buys and sells assets.
 * @author Zosia
 */
public class FundThread extends Thread {
     private final Fund fund;

    public FundThread(Fund fund) {
        this.fund = fund;
              
    }

    public Fund getFund() {
        return fund;
    }
    
    

    @Override
    public void run() {
        System.out.println("New FundThread " + fund.getFundName());
         
        while (!currentThread().isInterrupted()) {

            if (timeUnit%5==0) {            
                int toBuy = new Random().nextInt(1000);
                int toSell = new Random().nextInt(1000);

                if (toBuy < 2) {
                    int eIndex;
                    if (!Main.exchangeList.isEmpty()) {
                        eIndex = new Random().nextInt(Main.exchangeList.size());
                        Exchange e = Main.exchangeList.get(eIndex);
                        if (!e.getShares().isEmpty()) {
                            int sIndex = new Random().nextInt(e.getShares().size());
                            Share s = e.getShares().get(sIndex);
                            try {
                                fund.buy(s, (toBuy+1)*50, e);
                                //System.out.println(fund.getFundName() + " has bought some shares of " + c.getName());

                            }
                            catch (NoMoneyException | DoesntExistException exception) {
                                String msg = fund.getFundName()+" error: " + exception.getMessage();
                                //System.out.println(msg);
                            } catch (Exception ex) {
                                String msg = fund.getFundName()+" error: " + ex.getMessage();
                            }
                        }
                    }
                }
                else if (toBuy < 4) {
                    if (!Main.currencyMarket.getCurrencies().isEmpty()) {
                        int cIndex = new Random().nextInt(Main.currencyMarket.getCurrencies().size());
                        Currency c = Main.currencyMarket.getCurrencies().get(cIndex);
                        try {
                            fund.buy(c, toBuy*100);
                            //System.out.println(fund.getFundName() + " has bought some " + c.getName());
                        }
                        catch (NoMoneyException | DoesntExistException exception) {
                            String msg = fund.getFundName()+" error: " + exception.getMessage();
                                //System.out.println(msg);
                        }
                    }
                    }

                else if (toBuy < 6) {                
                        if (!Main.materialMarket.getMaterials().isEmpty()) {
                            int materialIndex = new Random().nextInt(Main.materialMarket.getMaterials().size());
                            RawMaterial material = (RawMaterial) Main.materialMarket.getMaterials().get(materialIndex);
                            try {
                                fund.buy(material, toBuy*100);
                            } catch (NoMoneyException | DoesntExistException | NotEnoughException exception) {
                                String msg = fund.getFundName()+" error: " + exception.getMessage();
                            }
                        }
                    }                        
                //selling

                if (toSell < 2) {
                    if (!fund.getShares().isEmpty()) {
                        int index = new Random().nextInt(fund.getShares().size());
                        Share s = fund.getShares().get(index);
                        fund.sell(s);
                    }
                }

                else if (toSell < 4) {
                    int index;
                    RawMaterial m;
                    if (!fund.getMaterials().isEmpty()) {

                        index = new Random().nextInt(fund.getMaterials().size());
                        m = (RawMaterial) fund.getMaterials().keySet().toArray()[index];

                        try {
                            fund.sell(m, toSell*1000);
                        } catch (DoesntExistException exception) {
                                String msg = fund.getFundName()+" error: " + exception.getMessage();
                            }
                        }
                    }

                else if (toSell < 6) {
                    int index;
                    Currency c;
                    if (!fund.getCurrencies().isEmpty()) {
                            index = new Random().nextInt(fund.getCurrencies().size());
                            c = (Currency) fund.getCurrencies().keySet().toArray()[index];
                        try {
                            fund.sell(c, toSell*1000);
                        } catch (DoesntExistException exception) {
                            String msg = fund.getFundName()+" error: " + exception.getMessage();
                                
                        }
                    }
                }
            }
      
        }
        
        if (currentThread().isInterrupted()) {
            System.out.println("FundThread " + fund.getFundName()+ " has stopped");
        }
    }
}

