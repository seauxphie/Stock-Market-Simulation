/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

/**
 * Thrown when a <code>MarketInvestor</code> does not have enough money
 * to buy an asset.
 * @author Zosia
 */
public class NoMoneyException extends Exception {

    /**
     * Creates a new instance of <code>NoMoneyException</code> without detail
     * message.
     */
    public NoMoneyException() {
        super("Not enough money.");
    }

    /**
     * Constructs an instance of <code>NoMoneyException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoMoneyException(String msg) {
        super(msg);
    }
    
    public NoMoneyException(Object o) {
        super("Not enough money to buy "+o.toString());
    }
}
