/*
SimulationMediator -- A class within the OQM (Open Queuing Model).
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
package oqm.simulation.mediator;

import file.open.controller.OpenFileController;
import file.open.observer.OpenFileObserver;
import file.save.controller.SaveFileControllerInterface;
import file.save.controller.SaveSpreadsheetFileController;
import oqm.network.mediator.NetworkMediator;
import oqm.optimize.controller.OptimizeController;
import oqm.plot.mediator.PlotMediator;
import oqm.simulation.properties.view.SimulationPropertiesFrame;
import oqm.simulation.view.state.SimulationViewStateInterface;
import oqm.simulation.view.state.output.SimulationViewOutputStateInterface;
import oqm.spreadsheet.mediator.SSMediator;
import simulyn.algorithm.model.AlgorithmModelInterface;
import simulyn.input.model.InputModelInterface;
import simulyn.simulation.mediator.SimulationMediatorInterface;

/**
 * SimulationMediator can be thought of as the master mediator for the simulation.
 * It coordinates the Simulations Controllers/Mediators and allows them
 * to stay fairly decoupled from each.
 * @author Kaleb
 */
public class SimulationMediator implements SimulationMediatorInterface
{

    private InputModelInterface inputModel;
    private OpenFileObserver fileObserver;
    // Model Algorithm Models are responsible for running the simulation
    // with their algorithm. They use a Command Pattern and a SwingWorker
    // so the GUI won't hang on big computations.
    private AlgorithmModelInterface convergenceSimulation;
    private SimulationPropertiesFrame propertiesView;
    private SimulationViewOutputStateInterface simulationViewOutputState;
    private SimulationViewStateInterface simulationViewState;
    private NetworkMediator networkMediator;
    private PlotMediator plotMediator;
    private SSMediator ssMediator;
    private OptimizeController optimizeController;

    /**
     * Initialize a new SimulationMediator.
     * @param inputModels the simulations Input Models.
     * @param diagnosticSimulation the desired Diagnostic Simulation.
     * @param monteCarloSimulation the desired Monte Carlo Simulation.
     * @param pixelGridSimulation the desired Pixel Grid Simulation.
     * @param networkMediator the Mediator for the Network Output.
     * @param plotMediator the Mediator for the Plot Output.
     * @param simulationPropertiesState the Simulation Properties.
     * @param propertiesView the Simulation Properties View.
     * @param ssMediator the Mediator for the Plot Output.
     */
    public SimulationMediator(
            InputModelInterface inputModel,
            AlgorithmModelInterface convergenceSimulation,
            SimulationPropertiesFrame propertiesView,
            NetworkMediator networkMediator,
            PlotMediator plotMediator,
            SSMediator ssMediator)
    {
        this.convergenceSimulation = convergenceSimulation;
        this.inputModel = inputModel;
        this.propertiesView = propertiesView;
        this.networkMediator = networkMediator;
        this.plotMediator = plotMediator;
        this.ssMediator = ssMediator;

        // Cast the InputModels into OpenFileObservers so we can use them
        // to load the InputModels from external files.
        this.fileObserver = (OpenFileObserver) this.inputModel;
    }

    /**
     * Provides the logic for when a View Action requests that a new
     * Input Model should be loaded into the simulation.
     */
    @Override
    public void onLoadSimulationInputModel()
    {
        OpenFileController importModel =
                new OpenFileController(fileObserver);
    }

    /**
     * Provides the logic for when a View Action requests that a new
     * set of Simulation Properties should be selected.
     */
    @Override
    public void onLoadSimulationProperties()
    {
        this.propertiesView.setVisible(true);
    }

    /**
     * Provides the logic for when a View Action requests that a new simulation
     * should be run using the current Input Models and the result should be
     * made available to the Output Models.
     */
    @Override
    public void onRunSimulation()
    {
        this.convergenceSimulation.execute();
        this.simulationViewOutputState.outputAvailable(true);
    }

    /**
     * Save the simulation Input Model's. 
     */
    @Override
    public void onSaveSimulationInputModel()
    {

        SaveFileControllerInterface saveDirectoryController = new SaveSpreadsheetFileController(inputModel.getModelInput());
    }

    /**
     * Clear the simulation Input Models and reset to the default state.
     */
    @Override
    public void onClearSimulation()
    {
        this.inputModel.setModelInput(new double[0][0]);
        this.simulationViewOutputState.outputAvailable(false);
        this.simulationViewState.onSimulationUnloaded();
        this.simulationViewState.onPropertiesUnloaded();
        this.networkMediator.resetNetwork();
        this.plotMediator.onClearUI();
        this.ssMediator.onClearModelResult();
        this.optimizeController.resetSystem();
    }

    public void setSimulationViewState(SimulationViewStateInterface simulationViewState)
    {
        this.simulationViewState = simulationViewState;
    }

    public void setSimulationViewOutputState(SimulationViewOutputStateInterface simulationViewState)
    {
        this.simulationViewOutputState = simulationViewState;
    }

    public void setOptimizeController(OptimizeController optimizeController)
    {
        this.optimizeController = optimizeController;
    }
}
