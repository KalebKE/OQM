/*
CovergenceAlgorithmModelInterface -- An interface within the OQM (Open Queuing Model).
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

import oqm.algorithm.model.observer.ConvergenceAlgorithmModelObserver;

/**
 * An interface for classes that manages a Convergence Simulation. A Convergence
 * Simulation finds the steady state of a Markov chain by multiplying the
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
 * ConvergenceSimulationInterface is intended to be a Subject of one or many Observer
 * classes that need to be notified of the simulation's State.
 *
 * @author Kaleb
 */
public interface ConvergenceAlgorithmModelInterface
{

    /**
     * Notify Convergence Algorithm Model Observers that new state is available.
     * Convergence Algorithm Models are responsible for keeping track of the
     * transition probabilities matrix that was multiplied by itself to find
     * the steady state.
     */
    public void notifyConvergenceAlgorithmModelObservers();

    /**
     * Register a Convergence Algorithm Model Observer. A Convergence
     * Algorithm Model Observer is responsible for managing the transition
     * probabilities matrix while it is being multiplied by itself to
     * find the steady state of the System.
     * @param o the Convergence Algorithm Model Observer that will be
     * registered.
     */
    public void registerObserver(ConvergenceAlgorithmModelObserver o);

    /**
     * Remove the Convergence Algorithm Model Observer. A Convergence
     * Algorithm Model Observer is responsible for managing the transition
     * probabilities matrix while it is being multiplied by itself to
     * find the steady state of the System.
     * @param o the Convergence Algorithm Model Observer that will be removed
     * as an Observer.
     */
    public void removeObserver(ConvergenceAlgorithmModelObserver o);
}
