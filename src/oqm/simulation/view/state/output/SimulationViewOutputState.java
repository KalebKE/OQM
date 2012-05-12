/*
SimulationViewOutputState -- A class within the Machine Artificial Vision
Network(Machine Artificial Vision Network).
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

import oqm.simulation.view.SimControlView;
import oqm.simulation.view.controlBar.ControlBar;

/**
 * SimulationViewOutputState manages the Output View State for the simluation.
 * It enables and disables certain UI functionality based on the Output Model
 * State.
 * @author Kaleb
 */
public class SimulationViewOutputState implements SimulationViewOutputStateInterface
{

    private boolean outputAvailable;
    private ControlBar outputViewBar;
    private ControlBar inputViewBar;
    private ControlBar optimizeViewBar;
    private SimControlView view;

    /**
     * Initialize a SimulationViewOutputState.
     * @param outputViewBar the Output View Control Bar.
     * @param inputViewBar the Input View Control Bar.
     * @param view the Simulation Control View.
     */
    public SimulationViewOutputState(ControlBar outputViewBar,
            ControlBar inputViewBar, ControlBar optimizeViewBar, SimControlView view)
    {
        this.outputViewBar = outputViewBar;
        this.inputViewBar = inputViewBar;
        this.optimizeViewBar = optimizeViewBar;
        this.view = view;
    }

    /**
     * Check if there is Output available.
     * @return
     */
    public boolean isOutputAvailable()
    {
        return outputAvailable;
    }

    /**
     * Indicate that Output from the simulation is available.
     */
    @Override
    public void outputAvailable(boolean available)
    {
        if (available)
        {
            view.getRunSimulationMenuItem().setEnabled(true);
            view.getResetSimulationMenuItem().setEnabled(true);
            view.getSaveModelMenuItem().setEnabled(true);
            view.getClearOutputMenuItem().setEnabled(true);

            // Disable these buttons
            outputViewBar.getRunSimulationButton().setEnabled(true);
            outputViewBar.getSaveModelOutputButton().setEnabled(true);
            outputViewBar.getClearModelOutputButton().setEnabled(true);
            outputViewBar.getResetSimulationButton().setEnabled(true);

            // Disable these buttons
            inputViewBar.getRunSimulationButton().setEnabled(true);
            inputViewBar.getSaveModelOutputButton().setEnabled(true);
            inputViewBar.getClearModelOutputButton().setEnabled(true);
            inputViewBar.getResetSimulationButton().setEnabled(true);

            // Disable these buttons
            optimizeViewBar.getRunSimulationButton().setEnabled(true);
            optimizeViewBar.getSaveModelOutputButton().setEnabled(true);
            optimizeViewBar.getClearModelOutputButton().setEnabled(true);
            optimizeViewBar.getResetSimulationButton().setEnabled(true);

            outputAvailable = true;
        }
        if (!available)
        {
            view.getRunSimulationMenuItem().setEnabled(false);
            view.getResetSimulationMenuItem().setEnabled(false);
            view.getSaveModelMenuItem().setEnabled(false);
            view.getClearOutputMenuItem().setEnabled(false);

            // Disable these buttons
            outputViewBar.getRunSimulationButton().setEnabled(false);
            outputViewBar.getSaveModelOutputButton().setEnabled(false);
            outputViewBar.getClearModelOutputButton().setEnabled(false);

            // Disable these buttons
            inputViewBar.getRunSimulationButton().setEnabled(false);
            inputViewBar.getSaveModelOutputButton().setEnabled(false);
            inputViewBar.getClearModelOutputButton().setEnabled(false);

            optimizeViewBar.getRunSimulationButton().setEnabled(false);
            optimizeViewBar.getSaveModelOutputButton().setEnabled(false);
            optimizeViewBar.getClearModelOutputButton().setEnabled(false);

            outputAvailable = false;
        }
    }
}
