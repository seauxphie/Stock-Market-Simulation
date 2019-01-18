/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockmarket;

/**
 *Thrown when the NameGenerator has run out of names.
 * @author Zosia
 */
class NoNamesException extends Exception {
    
    /**
     * Constructs an instance of <code>NoNamesException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoNamesException(String msg) {
        super(msg);
    }

}
