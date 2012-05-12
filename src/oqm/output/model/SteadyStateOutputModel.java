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
import oqm.algorithm.model.observer.SteadyStateAlgorithmModelObserver;
import oqm.output.model.observer.SteadyStateOutputModelObserver;

/**
 * Convergence Output Model stores the Output Model State from the Convergence
 * Matrix returned by the simulation. It is both a Observer of
 * the simulation's Algorithm Model and a Subject with its own Observers,
 * typically Output Mediators who render the State and then push it to the View.
 * @author Kaleb
 */
public class SteadyStateOutputModel implements
        SteadyStateAlgorithmModelObserver
{

    private boolean steadyState;
    private ArrayList<SteadyStateOutputModelObserver> modelResultObservers;

    /**
     * Initialize the state.
     */
    public SteadyStateOutputModel()
    {
        modelResultObservers = new ArrayList<SteadyStateOutputModelObserver>();
    }

    /**
     * Register the Convergence Output Model Observer.
     * @param o the Convergence Output Model Observer.
     */
    public void registerObserver(SteadyStateOutputModelObserver o)
    {
        modelResultObservers.add(o);
    }

    /**
     * Remove the Convergence Output Model Observer.
     * @param o the Convergence Output Model Observer.
     */
    public void removeObserver(SteadyStateOutputModelObserver o)
    {
        int i = modelResultObservers.indexOf(o);
        if (i >= 0)
        {
            modelResultObservers.remove(i);
        }
    }

    /**
     * Notify all Convergence Output Model Observer.
     */
    public void notifyObservers()
    {
        for (int i = 0; i < modelResultObservers.size(); i++)
        {
            SteadyStateOutputModelObserver matrixObserver = (SteadyStateOutputModelObserver) modelResultObservers.get(i);
            matrixObserver.updateSteadyStateOutputModelOutput(steadyState);
        }
    }

    public void updateSteadyStateAlgorithmModelOutput(boolean steadyState)
    {
        this.steadyState = steadyState;
        notifyObservers();
    }
}
