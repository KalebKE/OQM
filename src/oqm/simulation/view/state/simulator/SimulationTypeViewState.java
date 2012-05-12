/*
SimulationTypeViewInputState -- A class within the Machine Artificial Vision
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
package oqm.simulation.view.state.simulator;

import oqm.simulation.view.SimControlView;
import oqm.simulation.view.controlBar.ControlBar;

/**
 * SimulationTypeViewState maintains all of the View State required for selecting
 * the type of simulation that will be run.
 * It enables and disables certain UI functionality based on the
 * Simulation Properties State.
 * @author Kaleb
 */
public class SimulationTypeViewState implements SimulationTypeViewStateInterface
{

    private ControlBar outputViewBar;
    private ControlBar inputViewBar;
    private ControlBar optimizeViewBar;
    private SimControlView simulatorView;

    /**
     * Initialize a SimulationTypeViewState.
     * @param outputViewBar the Output View Control Bar.
     * @param inputViewBar the Input View Control Bar.
     * @param view the Simulation Control View.
     */
    public SimulationTypeViewState(ControlBar outputViewBar,
            ControlBar inputViewBar, ControlBar optimizeViewBar, SimControlView view)
    {
        this.outputViewBar = outputViewBar;
        this.inputViewBar = inputViewBar;
        this.optimizeViewBar = optimizeViewBar;
        this.simulatorView = view;
    }

    /**
     * Indicate that the Simulation Output View is desired.
     */
    @Override
    public void onSimulatorOutputView()
    {
        this.simulatorView.getViewOutputMenuItem().setSelected(true);
        this.simulatorView.getViewInputMenuItem().setSelected(false);
        this.simulatorView.getViewOptimizerMenuItem().setSelected(false);

        this.outputViewBar.getOutputViewButton().getModel().setPressed(true);
        this.outputViewBar.getOutputViewButton().getModel().setSelected(true);
        this.outputViewBar.getInputViewButton().getModel().setPressed(false);
        this.outputViewBar.getInputViewButton().getModel().setSelected(false);
        this.outputViewBar.getOptimizeViewButton().getModel().setPressed(false);
        this.outputViewBar.getOptimizeViewButton().getModel().setSelected(false);

        this.inputViewBar.getOutputViewButton().getModel().setPressed(true);
        this.inputViewBar.getOutputViewButton().getModel().setSelected(true);
        this.inputViewBar.getInputViewButton().getModel().setPressed(false);
        this.inputViewBar.getInputViewButton().getModel().setSelected(false);
        this.inputViewBar.getOptimizeViewButton().getModel().setPressed(false);
        this.inputViewBar.getOptimizeViewButton().getModel().setSelected(false);

        this.optimizeViewBar.getOutputViewButton().getModel().setPressed(true);
        this.optimizeViewBar.getOutputViewButton().getModel().setSelected(true);
        this.optimizeViewBar.getInputViewButton().getModel().setPressed(false);
        this.optimizeViewBar.getInputViewButton().getModel().setSelected(false);
        this.optimizeViewBar.getOptimizeViewButton().getModel().setPressed(false);
        this.optimizeViewBar.getOptimizeViewButton().getModel().setSelected(false);
    }

    /**
     * Indicate that the Simulation Input View is desired.
     */
    @Override
    public void onSimulatorInputView()
    {
        this.simulatorView.getViewOutputMenuItem().setSelected(false);
        this.simulatorView.getViewInputMenuItem().setSelected(true);
        this.simulatorView.getViewOptimizerMenuItem().setSelected(false);

        this.outputViewBar.getOutputViewButton().getModel().setPressed(false);
        this.outputViewBar.getOutputViewButton().getModel().setSelected(false);
        this.outputViewBar.getInputViewButton().getModel().setPressed(true);
        this.outputViewBar.getInputViewButton().getModel().setSelected(true);
        this.outputViewBar.getOptimizeViewButton().getModel().setPressed(false);
        this.outputViewBar.getOptimizeViewButton().getModel().setSelected(false);

        this.inputViewBar.getOutputViewButton().getModel().setPressed(false);
        this.inputViewBar.getOutputViewButton().getModel().setSelected(false);
        this.inputViewBar.getInputViewButton().getModel().setPressed(true);
        this.inputViewBar.getInputViewButton().getModel().setSelected(true);
        this.inputViewBar.getOptimizeViewButton().getModel().setPressed(false);
        this.inputViewBar.getOptimizeViewButton().getModel().setSelected(false);

        this.optimizeViewBar.getOutputViewButton().getModel().setPressed(false);
        this.optimizeViewBar.getOutputViewButton().getModel().setSelected(false);
        this.optimizeViewBar.getInputViewButton().getModel().setPressed(true);
        this.optimizeViewBar.getInputViewButton().getModel().setSelected(true);
        this.optimizeViewBar.getOptimizeViewButton().getModel().setPressed(false);
        this.optimizeViewBar.getOptimizeViewButton().getModel().setSelected(false);
    }

    public void onSimulatorOptimizeView()
    {
        this.simulatorView.getViewOutputMenuItem().setSelected(false);
        this.simulatorView.getViewInputMenuItem().setSelected(false);
        this.simulatorView.getViewOptimizerMenuItem().setSelected(true);

        this.outputViewBar.getOutputViewButton().getModel().setPressed(false);
        this.outputViewBar.getOutputViewButton().getModel().setSelected(false);
        this.outputViewBar.getInputViewButton().getModel().setPressed(false);
        this.outputViewBar.getInputViewButton().getModel().setSelected(false);
        this.outputViewBar.getOptimizeViewButton().getModel().setPressed(true);
        this.outputViewBar.getOptimizeViewButton().getModel().setSelected(true);

        this.inputViewBar.getOutputViewButton().getModel().setPressed(false);
        this.inputViewBar.getOutputViewButton().getModel().setSelected(false);
        this.inputViewBar.getInputViewButton().getModel().setPressed(false);
        this.inputViewBar.getInputViewButton().getModel().setSelected(false);
        this.inputViewBar.getOptimizeViewButton().getModel().setPressed(true);
        this.inputViewBar.getOptimizeViewButton().getModel().setSelected(true);

        this.optimizeViewBar.getOutputViewButton().getModel().setPressed(false);
        this.optimizeViewBar.getOutputViewButton().getModel().setSelected(false);
        this.optimizeViewBar.getInputViewButton().getModel().setPressed(false);
        this.optimizeViewBar.getInputViewButton().getModel().setSelected(false);
        this.optimizeViewBar.getOptimizeViewButton().getModel().setPressed(true);
        this.optimizeViewBar.getOptimizeViewButton().getModel().setSelected(true);
    }
}
