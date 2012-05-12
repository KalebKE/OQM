/*
SimulationViewInputStateInterface -- An interface within the OQM
(Open Queuing Model).
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
package oqm.simulation.view.state;

/**
 * Defines an output framework to manage the applications state using a State Pattern.
 * Implementations are concerned with maintaining the output states of the
 * simluation. Implementations make the application more intuitive to work with.
 * @author Kaleb
 */
public interface SimulationViewStateInterface
{
    /**
     * Indicate if the simulation properties have been loaded.
     * @return boolean indicating if simulation properties are loaded.
     */
    public boolean isPropertiesLoaded();

    /**
     * Indicate if the simulation has been loaded.
     * @return boolean indicating if the simulation has been loaded.
     */
    public boolean isSimulationLoaded();

    /**
     * What state should be enabled when a simulation is loaded.
     */
    public void onSimulationLoaded();

    /**
     * What state should be enabled when a simulation is unloaded.
     */
    public void onSimulationUnloaded();

    /**
     * What state should be enabled when the simulation properties are loaded.
     */
    public void onPropertiesLoaded();

    /**
     * What state should be enabled when the simulation properties are unloaded.
     */
    public void onPropertiesUnloaded();
}
