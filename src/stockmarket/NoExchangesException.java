/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

/**
 *Thrown when there are no <code>Exchange</code> objects.
 * 
 */
public class NoExchangesException extends Exception {

    /**
     * Creates a new instance of <code>NoExchangesException</code> with the default
     * message.
     */
    public NoExchangesException() {
        super("Create an exchange before adding a company or index");
    }
    
    
    
}
