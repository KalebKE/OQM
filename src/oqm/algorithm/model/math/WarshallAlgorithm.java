/*
 WarshallAlgorithm -- a class within the Open Queueing Model (OQM).
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
 * A class containing the methods needed to perform a boolean transitive closure
 * on a square n*n matrix. The matrix consists of a two dimensional integer
 * array where each index represents a node. A value of 1 indicates the node is
 * connected to another node. A value of 0 indicates the node is not connected
 * to another node.
 * 
 */
public class WarshallAlgorithm
{
    // The size of the node array.
    // The array *must* be sqaure.
    static int size;
    // Sum of the node array before traversal.
    static int oldSum;
    // Sum of the node array after traversal.
    static int newSum;
    // 2 Dimensional array for nodes.
    static int[][] nodes;
    // The number of traversals.
    static int count;
    // Boolean to run algorithm.
    static boolean run;

    /**
     * Method to run the Floyd-Warshall algorithm.
     *
     * @param nodes
     *            Two dimensional array containing the nodes
     * @return boolean indicating if the system is closed (true), or not(false).
     */
    public static boolean run(double[][] matrix)
    {
        // Set the size of the array if the array is square.
        if (matrix.length == matrix.length)
        {
            size = matrix.length;
        }
        // Fail.
        else
        {
            System.out.println("The matrix is not sqaure!");
        }

        nodes = new int[size][size];

        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
            {
                if (matrix[i][j] > 0)
                {
                    nodes[i][j] = 1;
                }
                else
                {
                    nodes[i][j] = 0;
                }
            }
        }

        count++;

        // Find the sum of the nodes before the traversal.
        oldSum = sum(nodes);

        count++;

        // Initially, run the algorithm.
        run = true;

        count++;

        // Count starts at 0.
        count = 0;

        while (run)
        {
            count++;

            // Set run to false.
            run = false;

            count++;

            // Row Traversal
            for (int i = 0; i < size; i++)
            {
                count++;

                // Column Traversal
                for (int j = 0; j < size; j++)
                {
                    count++;

                    // if the node is connected
                    if (WarshallAlgorithm.nodes[i][j] == 1)
                    {
                        count++;


                        // Rewrite the row with a new
                        // column traversal.
                        for (int k = 0; k < size; k++)
                        {

                            // This moves all of row X's connections into row
                            // Y's only iff X is connected to Y.
                            // The row with a connection inclusive OR'd with the
                            // row it is connected to.
                            WarshallAlgorithm.nodes[i][k] = WarshallAlgorithm.nodes[i][k]
                                    | WarshallAlgorithm.nodes[j][k];

                            count++;
                        }
                    }
                    else
                    {
                        count++;
                    }
                }
            }

            // Check to see if anything was changed.
            newSum = sum(nodes);

            // If nothing was changed, or if the system is closed, do nothing.
            if (oldSum == newSum || newSum == Math.pow(size, 2))
            {
                count++;
            }
            // If something changes, or if the system is not closed, traverse
            // again.
            else
            {
                // The newSum becomes the oldSum.
                oldSum = newSum;
                // Traverse again.
                run = true;

                count++;
            }
        }
        // Check to see if the system is closed.
        if (sum(nodes) == Math.pow(nodes.length, 2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Method will sum the values in two dimensional integer array.
     *
     * @param intArray
     *            Two dimensional array containing integers.
     * @return int representing the sum of the array.
     */
    public static int sum(int[][] intArray)
    {
        int sum = 0;

        // Row Traversal
        for (int i = 0; i < intArray.length; i++)
        {
            // Column Traversal
            for (int j = 0; j < intArray.length; j++)
            {
                // Sum the values.
                sum += intArray[i][j];
            }
        }

        return sum;
    }

    /**
     * Getter for the number of traversals.
     *
     * @return
     */
    public static int getCount()
    {
        return count;
    }
}
