/*
SteadyStateAlgorithmModelInterface -- A class within the OQM (Open Queuing Model).
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

import oqm.algorithm.model.observer.SteadyStateAlgorithmModelObserver;

/**
 * An interface for classes that manages the Steady State result for a
 * Convergence Simulation. The Steady State Algorithm Model is concerned with
 * keeping track of if the steady state of the transition probabilities matrix
 * was found, or not.
 *
 * ConvergenceSimulationInterface is intended to be a Subject of one or many Observer
 * classes that need to be notified of the simulation's State.
 *
 * @author Kaleb
 */
public interface SteadyStateAlgorithmModelInterface
{

    /**
     * Notify Iteration Algorithm Model Observers that new state is available.
     * The Iteration Algorithm Model Observer is responsible for keeping track
     * of the number of iteration it took to find the steady state.
     */
    public void notifySteadyStateAlgorithmModelObservers();

    /**
     * Register a Steady State Algorithm Model Observer. A Steady State
     * Algorithm Model Observer is responsible for keeping track of if the
     * steady state was found, or not.
     * @param o the Steady State Algorithm Model Observer that will be registered.
     */
    public void registerObserver(SteadyStateAlgorithmModelObserver o);

    /**
     * Remove the Steady State Algorithm Model Observer. A Steady State
     * Algorithm Model Observer is responsible for keeping track of if the
     * steady state was found, or not.
     * @param o the Steady State Algorithm Model Observer that will be removed
     * as an Observer.
     */
    public void removeObserver(SteadyStateAlgorithmModelObserver o);
}
