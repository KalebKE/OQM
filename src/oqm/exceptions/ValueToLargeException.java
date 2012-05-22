/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.exceptions;

public class ValueToLargeException extends RuntimeException
{
    public ValueToLargeException()
    {
        super("The value is to large! The input must be a positive number that is" +
                "less than or equal to 1.");
    }
}
