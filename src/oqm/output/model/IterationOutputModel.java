/*
IterationOutputModel -- a class within the OQM(Open Queuing Model).
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
package oqm.output.model;

import java.util.ArrayList;
import oqm.algorithm.model.observer.IterationAlgorithmModelObserver;
import oqm.output.model.observer.IterationOutputModelObserver;

/**
 * Iteration Output Model manages the number of iterations required
 * to find the steady state from the Convergence
 * Simulation. It is both a Observer of
 * the simulation's Algorithm Model and a Subject with its own Observers,
 * typically Output Mediators who render the State and then push it to the View.
 * @author Kaleb
 */
public class IterationOutputModel implements
        IterationAlgorithmModelObserver
{

    private int iterations;
    private ArrayList<IterationOutputModelObserver> modelResultObservers;

    /**
     * Initialize the state.
     */
    public IterationOutputModel()
    {
        modelResultObservers = new ArrayList<IterationOutputModelObserver>();
    }

    /**
     * Register the Iteration Output Model Observer.
     * @param o the Iteration Output Model Observer.
     */
    public void registerObserver(IterationOutputModelObserver o)
    {
        modelResultObservers.add(o);
    }

    /**
     * Remove the Iteration Output Model Observer.
     * @param o the Iteration Output Model Observer.
     */
    public void removeObserver(IterationOutputModelObserver o)
    {
        int i = modelResultObservers.indexOf(o);
        if (i >= 0)
        {
            modelResultObservers.remove(i);
        }
    }

    /**
     * Notify all Iteration Output Model Observer.
     */
    public void notifyObservers()
    {
        for (int i = 0; i < modelResultObservers.size(); i++)
        {
            IterationOutputModelObserver matrixObserver = (IterationOutputModelObserver) modelResultObservers.get(i);
            matrixObserver.updateIterationOutputModelOutput(iterations);
        }
    }

    /**
     * The Observer hook for the Convergence Simulation. It updates the number
     * of iterations that were required to find the steady state from the
     * transition probabilities matrix.
     * @param iterations the number of iterations required to find the steady state.
     */
    public void updateIterationAlgorithmModelOutput(int iterations)
    {
        this.iterations = iterations;
        notifyObservers();
    }
}
