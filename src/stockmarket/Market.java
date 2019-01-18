/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

import java.io.Serializable;

/**
 *The parent class of exchanges and other markets.
 * @author Zosia
 */
public class Market implements Serializable {
    protected String name;
    protected double markup;
    
    public Market() {
       
    }

    public void setName(String n) {
        this.name = n;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setMarkup (double m) {
        this.markup=m;
    }
    
    public double getMarkup() {
        return this.markup;
    }
    
}
