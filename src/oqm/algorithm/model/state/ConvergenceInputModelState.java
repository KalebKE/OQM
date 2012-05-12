/*
ConvergenceInputModelState -- A class within the OQM(Open Queuing Model).
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

import simulyn.algorithm.model.state.AlgorithmModelStateInterface;

/**
 * AlrogithmModelStateInterfaces are State Managers that use a State Pattern
 * to help Algorithm Model's manage their State and push that State to the
 * Algorithm Model's Subject Obeservers.
 * Some State Managers keep track of when to push State on their own and some
 * need to be notified by their Model. In the latter case, the State Manager
 * still does validation to ensure the Model State has been set.
 *
 * ConvergenceInputModelState manages the State of the Input Model
 * Subjects. It keeps track of what Input Models required for the
 * Convergence Simulation have been set and which ones have not. In the
 * case of Convergence Simulation, only one Input Model is required. When
 * all of the required State has been set, the method isInputModelReady()
 * will return true;
 * @author Kaleb
 */
public class ConvergenceInputModelState implements AlgorithmModelStateInterface
{
    private boolean systemUpdated;

    private boolean inputModelReady;

    // Algorithm Input State
    private double[][] systemInputModel;

    /**
     * Initialize a Convergence Input Model State.
     */
    public ConvergenceInputModelState()
    {
        this.inputModelReady = false;
        this.systemUpdated = false;
    }

    /**
     * Get the System Input Model. The System Input Model is
     * the transition probability matrix that will be used to find the
     * steady state with the Convergence Simulation.
     * @return the System Input Model.
     */
    public double[][] getSystemInputModel()
    {
        return systemInputModel;
    }

    /**
     * Check if the System Input Model is ready. The System Input Model is
     * the transition probability matrix that will be used to find the
     * steady state with the Convergence Simulation.
     * @return boolean indicating if the Simulation Input Model is ready.
     */
    public boolean isInputModelReady()
    {
        return inputModelReady;
    }

    /**
     * Set the System Input Model. The System Input Model is
     * the transition probability matrix that will be used to find the
     * steady state with the Convergence Simulation.
     * @param inputModel the System Input Model.
     */
    public void setSystemInputModel(double[][] inputModel)
    {
        this.systemInputModel = inputModel;
        this.systemUpdated = true;
        stateChanged();
    }


    /**
     * Indicate the that State has changed.
     */
    @Override
    public void stateChanged()
    {
        if (this.systemUpdated)
        {
            inputModelReady = true;
        }
    }

    /**
     * Reset the State.
     */
    @Override
    public void resetState()
    {
        this.inputModelReady = false;
        this.systemUpdated = false;
    }
}
