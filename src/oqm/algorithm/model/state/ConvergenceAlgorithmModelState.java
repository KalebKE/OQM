/*
ConvergenceAlgorithmModelState -- A class within the OQM(Open Queuing Model).
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
package oqm.algorithm.model.state;

import oqm.algorithm.model.ConvergenceSimulation;
import simulyn.algorithm.model.state.AlgorithmModelStateInterface;

/**
 * AlgorithmModelStateInterface are State Managers that use a State Pattern
 * to help Algorithm Model's manage their State and push that State to the
 * Algorithm Model's Subject Obeservers.
 *
 * Some State Managers keep track of when to push State on their own and some
 * need to be notified by their Model. In the latter case, the State Manager
 * still does validation to ensure the Model State has been set.
 * 
 * ConvergenceAlgorithmModelState is the State Manager for the Convergence
 * Simulation. When all of the required State has been set it notifies the
 * simulation to update the Convergence Simulation Observers.
 * @author Kaleb
 */
public class ConvergenceAlgorithmModelState implements AlgorithmModelStateInterface
{
    // Booleans used to keep track of what State has been set.
    private boolean convergenceOutputReady;
    private boolean iterationCountReady;
    private boolean steadyStateFound;
    // Algorithm Result Output State
    private double[][] convergenceMatrix;
    private int iterationCount;
    // The Algorithm Model this class manages State for.
    private ConvergenceSimulation model;

    /**
     * Initialize a new State Manager for to manage the Ouput Model State for a
     * Convergence Simulation.
     * @param model the Convergence Simulation that the class will manage state for.
     */
    public ConvergenceAlgorithmModelState(ConvergenceSimulation model)
    {
        this.model = model;
        this.convergenceOutputReady = false;
        this.steadyStateFound = false;
    }

    /**
     * Return the Convergence Matrix from the simulation.
     * @return the Convergence Matrix from the simulation.
     */
    public double[][] getConvergenceMatrix()
    {
        return convergenceMatrix;
    }

    /**
     * The number of iterations used to find the steady state of the
     * transition probabilities matrix.
     * @return the number of iterations used in the simulation.
     */
    public int getIterationCount()
    {
        return iterationCount;
    }

    /**
     * Check to see if the convergence matrix output is ready.
     * @return a boolean indicating if the convergence matrix is ready.
     */
    public boolean isConvergenceOutputReady()
    {
        return convergenceOutputReady;
    }

    public boolean isIterationCountReady()
    {
        return iterationCountReady;
    }
    
    /**
     * Check to see if the steady state of the transition probabilities matrix
     * was found.
     * @return a boolean indicating if the steady state was found (true), or
     * not found (false).
     */
    public boolean isSteadyStateFound()
    {
        return steadyStateFound;
    }

    /**
     * Set the ratio of points that hit each individual shape within the image
     * versus the total number of points fired at the image.
     * @param convergenceOutput ratio of points that hit each individual shape within the image
     * versus the total number of points fired at the image.
     */
    public void setConvergenceOutput(double[][] convergenceOutput)
    {
        this.convergenceMatrix = convergenceOutput;
        this.setConvergenceOutputReady(true);
        stateChanged();
    }

    /**
     * Indicate that the Convergence Simulation has run successfully and
     * the output is now ready to be pushed out to Observers.
     * @param convergenceOutputReady
     */
    public void setConvergenceOutputReady(boolean convergenceOutputReady)
    {
        this.convergenceOutputReady = convergenceOutputReady;
    }

    /**
     * Set the number of iterations required to find the steady state of the
     * transition probabilities matrix. Or, if the steady state was not found, the
     * value that n was limited to.
     * @param iterationCount the number of iterations used in the simulation.
     */
    public void setIterationCount(int iterationCount)
    {
        this.iterationCount = iterationCount;
        this.setIterationCountReady(true);
        stateChanged();
    }

    /**
     * Indicate that the iteration count is ready.
     * @param iterationCountReady boolean indicating if the iteration count
     * is ready.
     */
    public void setIterationCountReady(boolean iterationCountReady)
    {
        this.iterationCountReady = iterationCountReady;
    }

    /**
     * Indicate that the steady state for the transition probabilities
     * matrix has been found.
     * @param steadyStateFound
     */
    public void setSteadyStateFound(boolean steadyStateFound)
    {
        this.steadyStateFound = steadyStateFound;
    }

    /**
     * Indicate that the State has changed and check if the State should
     * be pushed to the Observers.
     */
    @Override
    public void stateChanged()
    {
        if (this.convergenceOutputReady && this.iterationCountReady)
        {
            try
            {
                model.notifyConvergenceAlgorithmModelObservers();
                model.notifyIterationAlgorithmModelObservers();
                model.notifySteadyStateAlgorithmModelObservers();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            resetState();
        }
    }

    /**
     * Indicate that the State has been pushed to the Observers and
     * new State needs to be set.
     */
    @Override
    public void resetState()
    {
        this.setConvergenceOutputReady(false);
        this.setIterationCountReady(false);
        this.setSteadyStateFound(false);
    }
}
