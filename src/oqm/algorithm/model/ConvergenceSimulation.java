/*
CovergenceSimulation -- A class within the OQM (Open Queuing Model).
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
package oqm.algorithm.model;

import java.util.ArrayList;
import javax.swing.SwingWorker;
import oqm.algorithm.model.observer.SteadyStateAlgorithmModelObserver;
import oqm.algorithm.model.observer.ConvergenceAlgorithmModelObserver;
import oqm.algorithm.model.observer.IterationAlgorithmModelObserver;
import oqm.algorithm.model.state.ConvergenceAlgorithmModelState;
import oqm.algorithm.model.state.ConvergenceInputModelState;
import oqm.algorithm.model.worker.ConvergenceSimulationWorker;
import oqm.input.model.observer.SystemInputModelObserver;
import simulyn.algorithm.model.AlgorithmModelInterface;

/**
 * A Convergence Simulation finds the steady state of a Markov chain by multiplying the
 * transition probabilities by itself iterativly until the steady state is found.
 * The steady state represents the probability that a visitor will be in a certain
 * state for a very large number of visitors.
 *
 * If P is the transition probabilities matrix, then a Convergence Simulation
 * raises P to the nth power, or P^n. The steady state is found by comparing
 * the result of P^n to a validation algorithm that ensures that P has converged
 * within a defined value known as the convergence error. The smaller the
 * convergence error, the longer it will take P to converge to the steady state.
 *
 * ConvergenceSimulationInterfaces are intended to be a Subject of one or many Observer
 * classes that need to be notified of the simulation's State.
 *
 * ConvergenceSimulation is an Observer of the SystemInputModel Subject.
 * The SystemInputModel is responsible for managing the transition probabilities
 * matrix that will be multiplied by itself to find the steady state by
 * the ConvergenceSimulation.
 *
 * ConvergenceSimulation uses a Command Pattern, with one method called execute(),
 * to run the Convergence Simulation. Once the simulation has been run,
 * the Observers are notified of the results.
 *
 * ConvergenceSimulation implements three Algorithm Models:
 * - ConvergenceAlgorithmModels are responsible for managing the state
 * of the probability transition matrix as it is iterated to find the steady state.
 * - SteadyStateAlgorithModels indicate if the steady state was found during
 * the simulation, or not.
 * - IterationAlgorithmModels are responsible for keeping track of the number
 * of iterations that were required to find the steady state.
 *
 * @author Kaleb
 */
public class ConvergenceSimulation implements AlgorithmModelInterface,
        ConvergenceAlgorithmModelInterface, SteadyStateAlgorithmModelInterface,
        IterationAlgorithmModelInterface, SystemInputModelObserver
{
    // Lists for Observers.

    private ArrayList<ConvergenceAlgorithmModelObserver> convergenceObservers;
    private ArrayList<SteadyStateAlgorithmModelObserver> steadyStateObservers;
    private ArrayList<IterationAlgorithmModelObserver> iterationObservers;
    // State Managers for Algorithm and Input Models.
    private ConvergenceAlgorithmModelState convergenceModelState;
    private ConvergenceInputModelState inputModelState;
    // Swing Worker for the simulation calculations.
    private SwingWorker simulationWorker;

    /**
     * Create a new instance of ConvergenceSimulation.
     */
    public ConvergenceSimulation()
    {
        // Lists for the Observers.
        convergenceObservers = new ArrayList<ConvergenceAlgorithmModelObserver>();
        steadyStateObservers = new ArrayList<SteadyStateAlgorithmModelObserver>();
        iterationObservers = new ArrayList<IterationAlgorithmModelObserver>();

        // The State Manager classes.
        inputModelState = new ConvergenceInputModelState();
        convergenceModelState = new ConvergenceAlgorithmModelState(this);
    }

    /**
     * Execute will run the Convergence Simulation. It spawns off a new thread
     * to perform the actual calculations using a Swing Worker.
     */
    public void execute()
    {
        // Make sure the Input Model is ready.
        if (inputModelState.isInputModelReady())
        {
            // Get a new instance of the Swing Worker.
            simulationWorker = new ConvergenceSimulationWorker(convergenceModelState, inputModelState);
            // Execute the Swing Worker.
            simulationWorker.execute();
        }
    }

    /**
     * Notify the Steady State Algorithm Model Observers that new state is available.
     * The Steady State Algorithm Model is responsible for keeping track of if
     * the steady state of was found during the simulation, or not.
     */
    public void notifySteadyStateAlgorithmModelObservers()
    {
        for (int i = 0; i < steadyStateObservers.size(); i++)
        {
            SteadyStateAlgorithmModelObserver steadyStateObserver = (SteadyStateAlgorithmModelObserver) steadyStateObservers.get(i);
            steadyStateObserver.updateSteadyStateAlgorithmModelOutput(convergenceModelState.isSteadyStateFound());
        }
    }

    /**
     * Notify Convergence Algorithm Model Observers that new state is available.
     * Convergence Algorithm Models are responsible for keeping track of the
     * transition probabilities matrix that was multiplied by itself to find
     * the steady state.
     */
    public void notifyConvergenceAlgorithmModelObservers()
    {
        for (int i = 0; i < convergenceObservers.size(); i++)
        {
            ConvergenceAlgorithmModelObserver convergenceObserver = (ConvergenceAlgorithmModelObserver) convergenceObservers.get(i);
            convergenceObserver.updateConvergenceAlgorithmModelOutput(convergenceModelState.getConvergenceMatrix());
        }
    }

    /**
     * Notify Iteration Algorithm Model Observers that new state is available.
     * The Iteration Algorithm Model Observer is responsible for keeping track
     * of the number of iteration it took to find the steady state.
     */
    public void notifyIterationAlgorithmModelObservers()
    {
        for (int i = 0; i < iterationObservers.size(); i++)
        {
            IterationAlgorithmModelObserver iterationObserver = (IterationAlgorithmModelObserver) iterationObservers.get(i);
            iterationObserver.updateIterationAlgorithmModelOutput(convergenceModelState.getIterationCount());
        }
    }

    /**
     * Register a Convergence Algorithm Model Observer. A Convergence
     * Algorithm Model Observer is responsible for managing the transition
     * probabilities matrix while it is being multiplied by itself to
     * find the steady state of the System.
     * @param o the Convergence Algorithm Model Observer that will be
     * registered.
     */
    public void registerObserver(ConvergenceAlgorithmModelObserver o)
    {
        convergenceObservers.add(o);
    }

    /**
     * Register a Iteration Algorithm Model Observer. An Iteration
     * Algorithm Model Observer is responsible for keeping track
     * of the number of iterations it took to find the steady state. Or, if
     * the steady state was not found, it will report the number of iterations
     * where n was limited.
     * @param o the Iteration Algorithm Model Observer that will be registered.
     */
    public void registerObserver(IterationAlgorithmModelObserver o)
    {
        iterationObservers.add(o);
    }

    /**
     * Register a Steady State Algorithm Model Observer. A Steady State
     * Algorithm Model Observer is responsible for keeping track of if the
     * steady state was found, or not.
     * @param o the Steady State Algorithm Model Observer that will be registered.
     */
    public void registerObserver(SteadyStateAlgorithmModelObserver o)
    {
        steadyStateObservers.add(o);
    }

    /**
     * Remove the Convergence Algorithm Model Observer. A Convergence
     * Algorithm Model Observer is responsible for managing the transition
     * probabilities matrix while it is being multiplied by itself to
     * find the steady state of the System.
     * @param o the Convergence Algorithm Model Observer that will be removed
     * as an Observer.
     */
    public void removeObserver(ConvergenceAlgorithmModelObserver o)
    {
        int i = convergenceObservers.indexOf(o);
        if (i >= 0)
        {
            convergenceObservers.remove(o);
        }
    }

    /**
     * Remove the Iteration Algorithm Model Observer. An Iteration
     * Algorithm Model Observer is responsible for keeping track
     * of the number of iterations it took to find the steady state. Or, if
     * the steady state was not found, it will report the number of iterations
     * where n was limited.
     * @param o the Iteration Algorithm Model Observer that will be removed.
     */
    public void removeObserver(IterationAlgorithmModelObserver o)
    {
        int i = iterationObservers.indexOf(o);
        if (i >= 0)
        {
            iterationObservers.remove(o);
        }
    }

    /**
     * Remove the Steady State Algorithm Model Observer. A Steady State
     * Algorithm Model Observer is responsible for keeping track of if the
     * steady state was found, or not.
     * @param o the Steady State Algorithm Model Observer that will be removed
     * as an Observer.
     */
    public void removeObserver(SteadyStateAlgorithmModelObserver o)
    {
        int i = steadyStateObservers.indexOf(o);
        if (i >= 0)
        {
            steadyStateObservers.remove(o);
        }
    }

    /**
     * The Observer hook for the System Input Model Subject. 
     * @param modelInput the new transition probabilities matrix.
     */
    public void updateSystemInputModel(double[][] modelInput)
    {
        inputModelState.setSystemInputModel(modelInput);
    }
}
