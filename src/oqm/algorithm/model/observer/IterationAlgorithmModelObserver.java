/*
IterationAlgorithmModelObserver -- A class within the OQM(Open Queuing Model).
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
package oqm.algorithm.model.observer;

/**
 * An Observer hook for classes that want to observe updates to the Iteration Algorithm
 * Model.
 * @author Kaleb
 */
public interface IterationAlgorithmModelObserver
{
    /**
     * Hook for classes to observe updates to the simulation results. 
     * @param iterations the number of iterations that were required to find
     * the steady state of the transition probabilities matrix. Or, if the
     * steady state was not found, the value that n was limited to.
     */
    public void updateIterationAlgorithmModelOutput(int iterations);
}
