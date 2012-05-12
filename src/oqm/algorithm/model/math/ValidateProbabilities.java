/*
ValidateProbabilities -- a class within the Open Queueing Model (OQM).
Copyright (C) 2012, Kaleb Kircher.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package oqm.algorithm.model.math;

/**
 * An OQM Model class that validates the transition probabilities
 * of a transition probabilities matrix.
 * @author Kaleb
 */
public class ValidateProbabilities
{

    /**
     * Method checks if the transition probabilities in each row
     * sum up to 1.
     * @param matrix
     * @return
     */
    public static boolean validateProbabilities(double[][] matrix)
    {
        // Initially  true
        boolean validSum = true;

        // Sum the rows
        for (int i = 0; i < matrix.length; i++)
        {
            double sum = 0;

            for (int j = 0; j
                    < matrix[i].length; j++)
            {
                sum += matrix[i][j];
            }
            // If the sum isn't equal to 1 or just slightly less
            // In the case of 1/3, you end up with .999999999 etc...
            if (sum < .99 || sum > 1)
            {
                validSum = false;
            }
        }
        return validSum;
    }
}
