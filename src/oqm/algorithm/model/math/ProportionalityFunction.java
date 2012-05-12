/*
ProportionalityFunction -- a class within the Open Queueing Model (OQM).
Copyright (C) 2012, Kaleb Kircher, Dennis Steele.

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
 * A model class that proportionatly distributes a feedback loop across
 * a Transtion Mattrix. In theory, this will make a periodic matrix aperiodic.
 * @author Kaleb
 */
public class ProportionalityFunction
{

    /**
     * Method will proportionatly add a feedback error (alpha) to a transition matrix.
     * @param matrix
     * @param alpha
     * @return
     */
    public static double[][] proportionalizeMatrix(double[][] matrix, double alpha)
    {
        double[][] proportionalMatrix = new double[matrix.length][matrix.length];

        // Get a local copy
        for (int i = 0; i < matrix.length; i++)
        {
            // Iterate through second matrix
            for (int j = 0; j < matrix[i].length; j++)
            {
                proportionalMatrix[i][j] = matrix[i][j];
            }
        }

        // Iterate through first array
        for (int i = 0; i < proportionalMatrix.length; i++)
        {
            // Iterate through second matrix
            for (int j = 0; j < proportionalMatrix[i].length; j++)
            {
                if (proportionalMatrix[i][j] != 0)
                {
                    // Dr. Steele's Function to normalize each row for an
                    // equal distribution of aplha accross active nodes
                    proportionalMatrix[i][j] = alpha * proportionalMatrix[i][i] + ((1 - alpha) * proportionalMatrix[i][j]);
                }
            }
            // Dr. Steele's Function to set the Identity Matrix to alpha
            proportionalMatrix[i][i] = alpha;
        }

        return proportionalMatrix;
    }
}


