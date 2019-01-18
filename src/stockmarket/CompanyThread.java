/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.util.Random;
import static stockmarket.Main.timeUnit;

/**
 *The thread where the company updates itself and issues shares.
 */
public class CompanyThread extends Thread {
    
    private final Company company;

    public CompanyThread(Company c) {
        this.company = c;
    }

    public Company getCompany() {
        return company;
    }
    
    @Override
    public void run() {
        System.out.println("New CompanyThread " + company.getName());
          
        while(!currentThread().isInterrupted()) {
            
            if (!Main.investorList.isEmpty()) {

            int issueShares = new Random().nextInt(100000);
            if (issueShares<company.getExchanges().size()) {
                Exchange exchange = company.getExchanges().get(issueShares);
                try {
                    company.issueShares(exchange);
                }
                catch (DoesntExistException e) {
                }

            }

            if (timeUnit%20000==0) {
                company.generateProfit();
                company.generateRevenue();            
                company.generatePrice();
            }
            
            Exchange exchange =  Main.exchangeList.get(new Random().nextInt(Main.exchangeList.size()));
            try {
                company.joinExchange(exchange);
            } catch (CopyException ex) {
                String msg = ex.getMessage();
            }
            }
                        
        }
        
        if(currentThread().isInterrupted()) {
            System.out.println("CompanyThread " + company.getName() + " has stopped");
                   
        }
    }

}
