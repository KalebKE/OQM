/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.exceptions;

public class NonSquareMatrixException extends RuntimeException
{
    public NonSquareMatrixException()
    {
        super("Non-Squre Matrix! The number of rows must equal the number of colums." +
                "The algorithm that is being used requires that " +
                "the matrix must be sqaure!");
    }
}
