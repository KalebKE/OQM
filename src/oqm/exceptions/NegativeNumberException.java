/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.exceptions;

public class NegativeNumberException extends RuntimeException
{
    public NegativeNumberException()
    {
        super("The matrix had a negative number! A transfer probability cannot" +
                "be negative. The input must be a positive number.");
    }
}
