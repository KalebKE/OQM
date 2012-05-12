/*
IterationOutputModelObserver -- A class within the OQM(Open Queuing Model).
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
package oqm.output.model.observer;

/**
 * A hook for classes that want to observe updates to the Convergence Simulation.
 * This observer keeps track of the number of iterations required to find the
 * steady state matrix from the transition probabilities matrix.
 * @author Kaleb
 */
public interface IterationOutputModelObserver
{

    /**
     * A hook for classes that want to observe updates to the Convergence Simulation.
     * This observer keeps track of the number of iterations required to find the
     * steady state matrix from the transition probabilities matrix.
     * @param iterations the number of iterations required to find the steady state.
     */
    public void updateIterationOutputModelOutput(int iterations);
}
