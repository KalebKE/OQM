/*
SimulationViewInputState -- A class within the OQM (Open Queuing Model).
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

import oqm.simulation.view.SimControlView;
import oqm.simulation.view.controlBar.ControlBar;

/**
 * SimulationViewInputState maintains all of the View State required for the
 * simulations Input Models. It enables and disables certain UI functionality
 * based on the Input Models State.
 * @author Kaleb
 */
public class SimulationViewState implements SimulationViewStateInterface
{

    private boolean propertiesLoaded;
    private boolean simulationLoaded;
    private ControlBar outputViewBar;
    private ControlBar inputViewBar;
    private ControlBar optimizeViewBar;
    private SimControlView view;

    /**
     * Initialize a SimulationViewInputState.
     * @param outputViewBar the Output View Control Bar.
     * @param inputViewBar the Input View Control Bar.
     */
    public SimulationViewState(ControlBar outputViewBar, ControlBar inputViewBar,
            ControlBar optimizeViewBar)
    {
        this.outputViewBar = outputViewBar;
        this.inputViewBar = inputViewBar;
        this.optimizeViewBar = optimizeViewBar;

        propertiesLoaded = false;
    }

    /**
     * Initialize the Input States.
     */
    public void init()
    {
        this.onPropertiesUnloaded();
        this.onSimulationUnloaded();
    }

    /**
     * Check to see if the simulation properties have been loaded.
     * @return boolean indicating loaded simulation properties.
     */
    @Override
    public boolean isPropertiesLoaded()
    {
        return propertiesLoaded;
    }

    /**
     * Check to see if a simulation has been loaded.
     * @return boolean indicating a simulation has been loaded.
     */
    @Override
    public boolean isSimulationLoaded()
    {
        return simulationLoaded;
    }

    /**
     * Inidcate that the simulation properties have been loaded.
     */
    @Override
    public void onPropertiesLoaded()
    {
        // Disable these buttons
        outputViewBar.getRunSimulationButton().setEnabled(true);
        inputViewBar.getRunSimulationButton().setEnabled(true);
        optimizeViewBar.getRunSimulationButton().setEnabled(true);
        view.getRunSimulationMenuItem().setEnabled(true);

        propertiesLoaded = true;
    }

    /**
     * Inidicate that the simulation properties have not been loaded.
     */
    @Override
    public void onPropertiesUnloaded()
    {
        view.getEditPropertiesMenuItem().setEnabled(true);
        view.getRunSimulationMenuItem().setEnabled(false);
        view.getSaveModelMenuItem().setEnabled(false);
        view.getClearOutputMenuItem().setEnabled(false);

        // Disable these buttons
        outputViewBar.getSimulationPropertiesButton().setEnabled(true);
        outputViewBar.getRunSimulationButton().setEnabled(false);
        outputViewBar.getSaveModelOutputButton().setEnabled(false);
        outputViewBar.getClearModelOutputButton().setEnabled(false);
        outputViewBar.getIterateBackwardButton().setEnabled(false);
        outputViewBar.getIterateForwardButton().setEnabled(false);

        // Disable these buttons
        inputViewBar.getSimulationPropertiesButton().setEnabled(true);
        inputViewBar.getRunSimulationButton().setEnabled(false);
        inputViewBar.getSaveModelOutputButton().setEnabled(false);
        inputViewBar.getClearModelOutputButton().setEnabled(false);
        inputViewBar.getIterateBackwardButton().setEnabled(false);
        inputViewBar.getIterateForwardButton().setEnabled(false);

        // Disable these buttons
        optimizeViewBar.getSimulationPropertiesButton().setEnabled(true);
        optimizeViewBar.getRunSimulationButton().setEnabled(false);
        optimizeViewBar.getSaveModelOutputButton().setEnabled(false);
        optimizeViewBar.getClearModelOutputButton().setEnabled(false);
        optimizeViewBar.getIterateBackwardButton().setEnabled(false);
        optimizeViewBar.getIterateForwardButton().setEnabled(false);

        propertiesLoaded = false;
    }

    /**
     * Indicate that a simulation has been loaded.
     */
    @Override
    public void onSimulationLoaded()
    {
        view.getEditPropertiesMenuItem().setEnabled(true);
        view.getSaveModelMenuItem().setEnabled(true);
        view.getCloseSimulationMenuItem().setEnabled(true);
        view.getMonteCarloMenuItem().setEnabled(true);
        view.getMonteCarloMenuItem().setSelected(true);
        view.getAnimateNetworkMenuItem().setEnabled(true);
        view.getAnimateNetworkMenuItem().setSelected(true);
        view.getRenderNetworkMenuItem().setEnabled(true);
        view.getClearNetworkMenuItem().setEnabled(true);
        view.getClearPlotterMenuItem().setEnabled(true);
        view.getRenderPointsMenuItem().setEnabled(true);
        view.getRenderPointsMenuItem().setEnabled(true);
        view.getRenderTimeMenuItem().setEnabled(true);
        view.getRunSimulationMenuItem().setEnabled(true);

        // Disable these buttons
        outputViewBar.getSimulationPropertiesButton().setEnabled(true);
        outputViewBar.getSaveSimulationButton().setEnabled(true);
        outputViewBar.getClearSimulationButton().setEnabled(true);
        outputViewBar.getClearNetworkButton().setEnabled(true);
        outputViewBar.getClearPlotButton().setEnabled(true);
        outputViewBar.getRunSimulationButton().setEnabled(true);
        outputViewBar.getIterateBackwardButton().setEnabled(true);
        outputViewBar.getIterateForwardButton().setEnabled(true);

        // Disable these buttons
        inputViewBar.getSimulationPropertiesButton().setEnabled(true);
        inputViewBar.getSaveSimulationButton().setEnabled(true);
        inputViewBar.getClearSimulationButton().setEnabled(true);
        inputViewBar.getConvergenceSimulationButton().setEnabled(true);
        inputViewBar.getClearNetworkButton().setEnabled(true);
        inputViewBar.getClearPlotButton().setEnabled(true);
        inputViewBar.getRunSimulationButton().setEnabled(true);
        inputViewBar.getIterateBackwardButton().setEnabled(true);
        inputViewBar.getIterateForwardButton().setEnabled(true);

        // Disable these buttons
        optimizeViewBar.getSimulationPropertiesButton().setEnabled(true);
        optimizeViewBar.getSaveSimulationButton().setEnabled(true);
        optimizeViewBar.getClearSimulationButton().setEnabled(true);
        optimizeViewBar.getConvergenceSimulationButton().setEnabled(true);
        optimizeViewBar.getClearNetworkButton().setEnabled(true);
        optimizeViewBar.getClearPlotButton().setEnabled(true);
        optimizeViewBar.getRunSimulationButton().setEnabled(true);
        optimizeViewBar.getIterateBackwardButton().setEnabled(true);
        optimizeViewBar.getIterateForwardButton().setEnabled(true);
    }

    /**
     * Indicate that a simulation has not been loaded.
     */
    @Override
    public void onSimulationUnloaded()
    {
        view.getEditPropertiesMenuItem().setEnabled(false);
        view.getSaveModelMenuItem().setEnabled(false);
        view.getCloseSimulationMenuItem().setEnabled(false);
        view.getMonteCarloMenuItem().setEnabled(false);
        view.getAnimateNetworkMenuItem().setEnabled(false);
        view.getClearNetworkMenuItem().setEnabled(false);
        view.getClearPlotterMenuItem().setEnabled(false);
        view.getRenderPointsMenuItem().setEnabled(false);
        view.getRenderPointsMenuItem().setEnabled(false);
        view.getRenderTimeMenuItem().setEnabled(false);
        view.getRunSimulationMenuItem().setEnabled(false);

        outputViewBar.getClearSimulationButton().setEnabled(false);
        outputViewBar.getSaveSimulationButton().setEnabled(false);

        outputViewBar.getClearModelOutputButton().setEnabled(false);
        outputViewBar.getConvergenceSimulationButton().setEnabled(true);

        outputViewBar.getResetSimulationButton().setEnabled(false);
        outputViewBar.getClearNetworkButton().setEnabled(false);
        outputViewBar.getClearPlotButton().setEnabled(false);

        outputViewBar.getSimulationPropertiesButton().setEnabled(false);
        outputViewBar.getRunSimulationButton().setEnabled(false);
        outputViewBar.getIterateBackwardButton().setEnabled(false);
        outputViewBar.getIterateForwardButton().setEnabled(false);
        outputViewBar.getSaveModelOutputButton().setEnabled(false);
        outputViewBar.getSaveModelOutputButton().setEnabled(false);

        inputViewBar.getClearSimulationButton().setEnabled(false);
        inputViewBar.getSaveSimulationButton().setEnabled(false);

        inputViewBar.getClearModelOutputButton().setEnabled(false);
        inputViewBar.getConvergenceSimulationButton().setEnabled(true);

        inputViewBar.getResetSimulationButton().setEnabled(false);

        inputViewBar.getClearNetworkButton().setEnabled(false);
        inputViewBar.getClearPlotButton().setEnabled(false);

        inputViewBar.getSimulationPropertiesButton().setEnabled(false);
        inputViewBar.getRunSimulationButton().setEnabled(false);
        inputViewBar.getIterateBackwardButton().setEnabled(false);
        inputViewBar.getIterateForwardButton().setEnabled(false);
        inputViewBar.getSaveModelOutputButton().setEnabled(false);
        inputViewBar.getSaveModelOutputButton().setEnabled(false);

        optimizeViewBar.getClearSimulationButton().setEnabled(false);
        optimizeViewBar.getSaveSimulationButton().setEnabled(false);

        optimizeViewBar.getClearModelOutputButton().setEnabled(false);
        optimizeViewBar.getConvergenceSimulationButton().setEnabled(true);

        optimizeViewBar.getResetSimulationButton().setEnabled(false);

        optimizeViewBar.getClearNetworkButton().setEnabled(false);
        optimizeViewBar.getClearPlotButton().setEnabled(false);

        optimizeViewBar.getSimulationPropertiesButton().setEnabled(false);
        optimizeViewBar.getRunSimulationButton().setEnabled(false);
        optimizeViewBar.getIterateBackwardButton().setEnabled(false);
        optimizeViewBar.getIterateForwardButton().setEnabled(false);
        optimizeViewBar.getSaveModelOutputButton().setEnabled(false);
        optimizeViewBar.getSaveModelOutputButton().setEnabled(false);

        simulationLoaded = false;
    }

    /**
     * Set the Simulation Control View.
     * @param view the SimControlView.
     */
    public void setView(SimControlView view)
    {
        this.view = view;
    }
}
