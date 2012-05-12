/*
SimulationViewInputStateInterface -- An interface within the
OQM (Open Queuing Model).
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
package oqm.simulation.view.state.output;

/**
 * An interface for classes that manage simulation Output View State.
 * @author Kaleb
 */
public interface SimulationViewOutputStateInterface
{

    /**
     * Indicate that Output from the simulation is available.
     */
    public void outputAvailable(boolean available);
}
