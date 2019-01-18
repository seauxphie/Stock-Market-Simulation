/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

/**
 *This exception is thrown when the app is trying to duplicate an object.
 * @author Zosia
 */
public class CopyException extends Exception {

    /**
     * Creates a new instance of <code>CopyException</code> with the default
     * message.
     */
    public CopyException() {
        super("This object is already here");
    }

    /**
     * Constructs an instance of <code>CopyException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public CopyException(String msg) {
        super(msg);
    }
    
    /**
     *Constructs an instance of <code>CopyException</code> with a detaild message
     * concerning the specifeid object.
     * 
     * @param o the object
     */
    public CopyException(Object o) {
        super(o.toString() +" is already here");
    }
}
