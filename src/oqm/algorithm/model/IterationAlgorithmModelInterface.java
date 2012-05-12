/*
IterationAlgorithmModelInterface -- A class within the OQM (Open Queuing Model).
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

import oqm.algorithm.model.observer.IterationAlgorithmModelObserver;

/**
 * An interface for classes that manages the Iteration State
 * for a Convergence Simulation. The Iteration Algorithm Model is concerned
 * with keeping track of the number of iterations that are required for the
 * steady state of the transition probabilities matrix to be found. If the steady
 * state is not found, it will keep track of the value that n was limited to.
 *
 * IterationAlgorithmModelInterface is intended to be a Subject of one or many Observer
 * classes that need to be notified of the simulation's State.
 *
 * @author Kaleb
 */
public interface IterationAlgorithmModelInterface
{

    /**
     * Notify Iteration Algorithm Model Observers that new state is available.
     * The Iteration Algorithm Model Observer is responsible for keeping track
     * of the number of iteration it took to find the steady state.
     */
    public void notifyIterationAlgorithmModelObservers();

    /**
     * Register a Iteration Algorithm Model Observer. An Iteration
     * Algorithm Model Observer is responsible for keeping track
     * of the number of iterations it took to find the steady state. Or, if
     * the steady state was not found, it will report the number of iterations
     * where n was limited.
     * @param o the Iteration Algorithm Model Observer that will be registered.
     */
    public void registerObserver(IterationAlgorithmModelObserver o);

    /**
     * Remove the Iteration Algorithm Model Observer. An Iteration
     * Algorithm Model Observer is responsible for keeping track
     * of the number of iterations it took to find the steady state. Or, if
     * the steady state was not found, it will report the number of iterations
     * where n was limited.
     * @param o the Iteration Algorithm Model Observer that will be removed.
     */
    public void removeObserver(IterationAlgorithmModelObserver o);
}
