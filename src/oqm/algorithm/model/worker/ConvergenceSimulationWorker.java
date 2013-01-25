/*
ConvergenceSimulationWorker -- a class within the Open Queueing Model (OQM).
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
package oqm.algorithm.model.worker;

import com.google.common.util.concurrent.MoreExecutors;
import javax.swing.SwingWorker;
import oqm.algorithm.model.state.ConvergenceAlgorithmModelState;
import oqm.algorithm.model.state.ConvergenceInputModelState;
import oqm.exceptions.NegativeNumberException;
import oqm.exceptions.NonSquareMatrixException;
import oqm.globals.Globals;
import sun.awt.AppContext;

/**
 * A class that contains methods to calculate the steady state vectors from a transition matrix.
 *
 * To find a steady state distribution for a Markov System with transition
 * matrix P, we solve the system of equations given by:
 *
 * x + y + z + . . .	=	1
 * [x   y   z . . . ]P	=	[x   y   z . . .]
 *
 * where we use as many unknowns as there are states in the Markov system. 
 * A steady state probability vector is then given by:
 *
 * v = [x   y   z . . . ]
 *
 * Convergence Simulation Worker uses a iterative approach to finding the
 * steady state by raising P to the nth power. For a sufficiently large
 * n, P will converge to the steady state.
 *
 * Note that this class is a Swing Worker an each instance is intended to be
 * used only ONE time.
 */
public class ConvergenceSimulationWorker extends SwingWorker
{

    // The State of the Algorith and Input Model's.
    private ConvergenceAlgorithmModelState convergenceModelState;
    private ConvergenceInputModelState inputModelState;
    double maxError;

    /**
     * Create a new instance of ConvergenceSimulationWorker.
     * @param convergenceModelState the state of the Convergence Simulation
     * that will eventually be pushed out to the Observers.
     * @param inputModelState the state of the System Input Model which is the
     * transition probability matrix that will be used to find the steady state
     * of the System with a Convergence Simulation.
     */
    public ConvergenceSimulationWorker(ConvergenceAlgorithmModelState convergenceModelState,
            ConvergenceInputModelState inputModelState)
    {
        this.convergenceModelState = convergenceModelState;
        this.inputModelState = inputModelState;
    }

    /**
     * A Swing Worker runs the acutal simulation. This keeps the GUI
     * from hanging.
     * @return null.
     * @throws Exception
     */
    @Override
    protected Object doInBackground() throws Exception
    {
        convergenceModelState.setConvergenceOutput(this.calculateConvergence(inputModelState.getSystemInputModel(),
                Globals.EPSILON, Globals.ITERATIONS));

        return null;
    }

    /**
     * A method that calculates the steady state vectors from a transition matrix.
     * It will calculate a matrix of any size.
     *
     * @param systemInputModel The transition probability matrix.
     * @param epsilon The convergence error that must be achieved before the
     * simulation is finished.
     * @param numIterations The number of iterations that should be used before
     * the simulation stops. This is an absolute limit that takes priority
     * over epsilon. 
     * @return the steady state matrix or, if the matrix did not converge, the
     * result of the last iteration of the simulation.
     */
    public double[][] calculateConvergence(double[][] systemInputModel, double epsilon, int numIterations)
    {
        double diff = 0;
        int count, i, j, k, l;

        // Validate that the transition probability matrix is square.
        if(systemInputModel.length != systemInputModel[0].length)
        {
            throw new NonSquareMatrixException();
        }

        double[][] product = new double[systemInputModel.length][systemInputModel[0].length];
        double[][] olda = new double[systemInputModel.length][systemInputModel[0].length];

        // Transfer the transition probability matrix to a local copy.
        for (i = 0; i < systemInputModel.length; i++)
        {
            for (j = 0; j < systemInputModel.length; j++)
            {
                // Verify that all the numbers are positive.
                if (systemInputModel[i][j] < 0)
                {
                    throw new NegativeNumberException();
                } else
                {
                    olda[i][j] = systemInputModel[i][j];
                }
            }
        }

        for (count = 0; count < numIterations; count++)
        {
            for (i = 0; i < systemInputModel.length; i++)
            {
                for (j = 0; j < systemInputModel.length; j++)
                {
                    product[i][j] = 0;
                }
            }

            for (i = 0; i < systemInputModel.length; i++)
            {
                for (j = 0; j < systemInputModel.length; j++)
                {
                    for (k = 0; k < systemInputModel.length; k++)
                    {
                        product[i][j] = product[i][j] + systemInputModel[i][k] * olda[k][j];
                    }
                }
            }

            boolean runAgain = false;

            for (i = 0; i < systemInputModel.length; i++)
            {
                for (j = 0; j < systemInputModel.length; j++)
                {
                    diff = Math.abs(product[i][j] - olda[i][j]);

                    if (diff > epsilon)
                    {
                        runAgain = true;
                        for (k = 0; k < systemInputModel.length; k++)
                        {
                            for (l = 0; l < systemInputModel.length; l++)
                            {
                                olda[k][l] = product[k][l];
                            }
                        }
                        break;
                    }
                }
            }

            if (!runAgain)
            {
                convergenceModelState.setSteadyStateFound(true);
                convergenceModelState.setIterationCount(count);
                return product;
            }
        }

        convergenceModelState.setSteadyStateFound(false);
        convergenceModelState.setIterationCount(count);
        return product;
    }

    /**
     * Force the SwingWorker to use the main thread so it can be tested.
     */
    public void sameThreadExecution()
    {
        AppContext.getAppContext().put(SwingWorker.class, MoreExecutors.sameThreadExecutor());
    }
}
