/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

/**
 * An exception thrown when an object the app is trying to reach does not exist.
 */
public class DoesntExistException extends Exception {
    
    /**
     * Creates a new instance of <code>DoesntExistException</code> with the
     * default message.
     */
    public DoesntExistException() {
        super("I can't find this object.");
    }

    /**
     * Constructs an instance of <code>DoesntExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DoesntExistException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>DoesntExistException</code> with a
     * detail message abut the specified object
     *
     * @param o the object
     */
    public DoesntExistException(Object o)
    {
        super("I can't find "+ o.toString());
    }
}
