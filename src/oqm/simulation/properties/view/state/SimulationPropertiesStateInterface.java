/*
SimulationPropertiesStateInterface -- an interface within the Machine Artificial
Vision Network(Machine Artificial Vision Network).
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
package oqm.simulation.properties.view.state;

import javax.swing.SpinnerNumberModel;

/**
 * An interface for the Simulation Properties State. Simulation Properties
 * State manages all of the State related to running a simulation. 
 * Implementations define what State should be enabled for the application under
 * specific conditions.
 * @author Kaleb
 */
public interface SimulationPropertiesStateInterface
{

    /**
     * Get the Spinner Model for the Monte Carlo Simulation.
     * @return SpinnerNumberModel representing the number of Points to be fired
     * during the simulation.
     */
    public SpinnerNumberModel getAlphaSpinnerModel();

    public SpinnerNumberModel getConvergenceErrorSpinnerModel();

    public SpinnerNumberModel getFeedbackSpinnerModel();

    /**
     * Get the Monte Carlo Simulation Seed Model.
     * @return SpinnerNumberModel representing the random number seed that should
     * be used during the simulation.
     */
    public SpinnerNumberModel getIterationSpinnerModel();

    /**
     * Check to see if the Pixel Grid Simulation is enabled.
     * @return boolean indicating if the Pixel Grid Simulation is enabled.
     */
    public boolean isConvergenceSimulation();

    /**
     * Indicate that the Diagnostic Simulation should be enabled.
     */
    public void onConvergenceSimulation();
}
