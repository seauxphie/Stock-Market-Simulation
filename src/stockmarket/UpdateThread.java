/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.time.LocalTime;
import static stockmarket.Main.currencyList;
import static stockmarket.Main.indexList;
import static stockmarket.Main.materialList;
import static stockmarket.Main.shareList;
import static stockmarket.Main.timeUnit;

/**
 *The thread where values are calculated, new investors and funds are created
 * and indexes are updated.
 * @author Zosia
 */
public class UpdateThread extends Thread {
    
    public UpdateThread() {
        
    }
    
    @Override
    public void run() {
        while (!currentThread().isInterrupted()) {

            timeUnit = LocalTime.now().toNanoOfDay();
            
            for (long j=0; j<1000000; j++);
            
 
            if (timeUnit%2000000000 == 0 && Main.investorList.size()>0) {
            

                for (int i=0; i<materialList.size(); i++) materialList.get(i).generateValue();
                for (int i=0; i<currencyList.size(); i++) currencyList.get(i).generateValue();
                for (int i=0; i<shareList.size(); i++) shareList.get(i).generateValue();
                
                long nOfAssets=0; 
                for (int i=0; i<materialList.size(); i++) nOfAssets+=materialList.get(i).getAmount();
                for (int i=0; i<shareList.size(); i++) nOfAssets+=shareList.get(i).getNumber();
                
                
                
                if (!Main.investorList.isEmpty() && nOfAssets> Main.investorList.size()*2000000) {
                    try {
                        Investor investor = new Investor("");
                        Main.investorList.add(investor);
                        Thread t = new InvestorThread(investor);
                        t.start();
                        Main.investorThreadList.add(t);
                    }  catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                else if (!Main.fundList.isEmpty() && nOfAssets> Main.fundList.size()*10000000) {
                    try {
                        Fund fund = new Fund("");
                        Main.fundList.add(fund);
                        Thread t = new FundThread(fund);
                        t.start();
                        Main.fundThreadList.add(t);
                    }  catch (NoNamesException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                
                if (timeUnit%4 == 0)
                    for(int i=0; i<indexList.size(); i++) {
                    Index index = indexList.get(i);
                    Exchange exchange = index.getExchange();
                    for (int j=0; j<exchange.getCompanies().size(); j++) {
                        try {
                            index.assignCompany(exchange.getCompanies().get(j));
                        }
                        catch (CopyException e) {
                            String message = e.getMessage();;
                        }
                    }
                    index.generateValue();
                }
             }
            }
        if (currentThread().isInterrupted()) {
            System.out.println("Update thread has stopped");
        }
    }
    
}
