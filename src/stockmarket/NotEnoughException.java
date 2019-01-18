/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

/**
 *Thrown when the investor wants to buy a bigger amount of an asset
 * than there is on the market.
 * 
 */
public class NotEnoughException extends Exception {

    /**
     * Constructs an instance of <code>NotEnoughException</code> with the
     * default message.
     *
     */
    public NotEnoughException() {
        super("There is not enough of this asset on the market");
    }
    
     /**
     * Constructs an instance of <code>NotEnoughException</code> with the
     * default message about an asset.
     *
     * @param a the asset
     */
    public NotEnoughException (Asset a) {
        super("There is not enough "+a.getName()+" on the market");
    }
    
}
