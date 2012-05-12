/*
OQMSimulationFactory -- a class within the QOM(Open Queuing Model).
Copyright (C) 2012, Kaleb Kircher

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
package oqm.factory;

import java.util.ArrayList;
import oqm.algorithm.model.ConvergenceAlgorithmModelInterface;
import oqm.algorithm.model.ConvergenceSimulation;
import oqm.algorithm.model.IterationAlgorithmModelInterface;
import oqm.algorithm.model.SteadyStateAlgorithmModelInterface;
import oqm.algorithm.model.observer.ConvergenceAlgorithmModelObserver;
import oqm.algorithm.model.observer.IterationAlgorithmModelObserver;
import oqm.algorithm.model.observer.SteadyStateAlgorithmModelObserver;
import oqm.feedback.controller.FeedbackController;
import oqm.globals.Globals;
import oqm.input.model.SystemInputModel;
import oqm.input.model.changeEvent.InputModelChangeEvent;
import oqm.input.model.observer.SystemInputModelObserver;
import oqm.input.view.SystemInputModelView;
import oqm.input.view.action.InputModelViewAction;
import oqm.input.view.layout.InputViewGridLayoutPanel;
import oqm.input.view.state.InputViewState;
import oqm.network.mediator.NetworkMediator;
import oqm.network.mediator.NetworkMediatorInterface;
import oqm.network.mediator.state.NetworkMediatorViewState;
import oqm.optimize.controller.OptimizeController;
import oqm.optimize.view.OptimizeSystemPanel;
import oqm.output.model.ConvergenceOutputModel;
import oqm.output.model.IterationOutputModel;
import oqm.output.model.SteadyStateOutputModel;
import oqm.output.view.layout.ModelOutputDefaultLayoutView;
import oqm.plot.mediator.PlotMediator;
import oqm.plot.mediator.PlotMediatorInterface;
import oqm.simulation.mediator.SimulationMediator;
import oqm.simulation.properties.view.SimulationPropertiesFrame;
import oqm.simulation.properties.view.state.SimulationPropertiesState;
import oqm.simulation.view.SimControlView;
import oqm.simulation.view.actions.FeedbackAction;
import oqm.simulation.view.actions.NetworkBarAction;
import oqm.simulation.view.actions.OutputBarAction;
import oqm.simulation.view.actions.PlotBarAction;
import oqm.simulation.view.actions.PropertiesBarAction;
import oqm.simulation.view.actions.RunSimulationBarAction;
import oqm.simulation.view.actions.SimulationBarAction;
import oqm.simulation.view.actions.ViewBarAction;
import oqm.simulation.view.controlBar.ControlBar;
import oqm.simulation.view.state.SimulationViewState;
import oqm.simulation.view.state.output.SimulationViewOutputState;
import oqm.simulation.view.state.simulator.SimulationTypeViewState;
import oqm.spreadsheet.mediator.SSMediator;
import simulyn.input.controller.InputController;
import simulyn.input.model.InputModelInterface;
import simulyn.ui.components.inputModel.InputViewAbstractExtraLarge;

/**
 * A Factory class for the OQM application.
 * @author Kaleb Kircher
 */
public class OQMSimulationFactory extends AbstractSimulationFactory
{

    /**
     * Initialize OQM Simulation Factory. It will
     * prepare the state for the application. 
     */
    public OQMSimulationFactory()
    {
        inputModels = new ArrayList<InputModelInterface>();
        inputViews = new ArrayList<InputViewAbstractExtraLarge>();
        initInputModels();
        initAlgorithmModels();
        initOutputModels();
        initInputModelControllers();
        initSimulationProperties();
        initSSMediator();
        initNetworkMediator();
        initPlotMediator();
        initSimulationMediator();
        initSimulationActions();
        initControlBarViews();
        initOutputModelViews();
        initInputModelChangeEvent();
        initInputModelViewState();
        initInputModelActions();
        initInputModelViews();
        initOptimizeController();
        initSimulationView();
        initViewState();
        view.setOutputView();
        // Display the view.
        this.view.setVisible(true);
    }

    /**
     * Initialize the OQM Algorithm Models. The Alogirthm Models can be thought
     * of as the individual simulations. They are responsible for running
     * the simulations and coordianting all of the supporting classes.
     */
    @Override
    public void initAlgorithmModels()
    {
        // There are currently 3 different types of Simulations
        convergenceSimulation = new ConvergenceSimulation();

        // Register the Diagnostic Simulation with the Input Models.
        ((SystemInputModel) systemInputModel).registerObserver((SystemInputModelObserver) convergenceSimulation);
    }

    /**
     * Initialize the Simulation Control Bars. The Control Bars are part
     * of the UI. They allow the user to control all of the key functionality
     * of OQM with one-click of the mouse.
     */
    @Override
    public void initControlBarViews()
    {
        // Create a new Simulation Control Bar for the Output View.
        outputControlBar = new ControlBar(simulationBarAction,
                propertiesBarAction, modelOuputBarAction, viewBarAction,
                newtorkViewBarAction, plotViewBarAction, runSimulationAction);

        // Create a new Simulation Control Bar for the Input View.
        inputControlBar = new ControlBar(simulationBarAction, propertiesBarAction,
                modelOuputBarAction, viewBarAction, newtorkViewBarAction,
                plotViewBarAction, runSimulationAction);

        // Create a new Simulation Control Bar for the Input View.
        optimizeControlBar = new ControlBar(simulationBarAction, propertiesBarAction,
                modelOuputBarAction, viewBarAction, newtorkViewBarAction,
                plotViewBarAction, runSimulationAction);

        // Add the Control Bars to the Simulation Input State Manager.
        simulationInputState = new SimulationViewState((ControlBar) outputControlBar, (ControlBar) inputControlBar,
                (ControlBar) optimizeControlBar);
        // Give the Simulation Propreties State an instance of the Simulation Input State.
        ((SimulationPropertiesFrame) simulationPropertiesFrame).setSimulationViewState(simulationInputState);
    }

    /**
     * Initialize the simulation Input Models. The Input Models are responsible
     * for managing all of the inputs required for the simluations algorithms
     * to run. The inputs for the algorithms can come from external files or
     * can be generated within OQM by the user. The Input Models are part of a
     * Model-View-Controller architecture and push new State to their Observers,
     * usually Input View's.
     */
    @Override
    public void initInputModels()
    {
        // Initialize the Input Models
        // Target Model is only used for the Diagnostic Simulation
        systemInputModel = new SystemInputModel();

        // Add the input models to the collection
        // Use globals to associate the object with the correct index
        // Since the number of objects is small, a hashMap would be
        // overkill.
        inputModels.add(Globals.SYSTEM_INPUT_MODEL, systemInputModel);
    }

    /**
     * Initialize the simulations Input Model Actions. Input Model Actions
     * decouple the Input Controllers from the Input Models and Input Views
     * by using an ActionListener to manage the dependencies and forward
     * the Actions to the appropriate classes.
     */
    @Override
    public void initInputModelActions()
    {
        systemInputModelPanelAction = new InputModelViewAction(systemInputModelController, systemInputModel);
    }

    /**
     * Initialize the Input Model Change Event. The Change Event watches the
     * Input Models and determines if they have been loaded into OQM. Once
     * all of the Input Models have been loaded, it notifies other classes
     * that new simulation state should be enabled.
     */
    @Override
    public void initInputModelChangeEvent()
    {
        modelChanged = new InputModelChangeEvent(inputModels, simulationInputState);
    }

    /**
     * Initialize the Input Model Controllers. The Input Model Controllers
     * use a Controller pattern to manage the Input View and Input Models in a
     * Model-View-Controller architecture.
     */
    @Override
    public void initInputModelControllers()
    {
        // Initialize the input controllers.
        systemInputModelController = new InputController(systemInputModel);
    }

    /**
     * Initialize Input Model Views. Input Model Views Render the State
     * from the Input Models so the user can interact with the State in a
     * graphical environment (usually a spreadsheet).
     */
    @Override
    public void initInputModelViews()
    {
        systemInputModelPanel = new SystemInputModelView(
                systemInputModelPanelAction, systemInputModelController,
                systemInputModel, systemInputModelState, modelChanged);
        ((InputModelViewAction) systemInputModelPanelAction).setView(systemInputModelPanel);

        ((InputViewState) systemInputModelState).setView(systemInputModelPanel);

        inputViews.add(Globals.SYSTEM_INPUT_MODEL, systemInputModelPanel);

        // register with the input model observers with the view
        ((SystemInputModel) systemInputModel).registerObserver((SystemInputModelView) systemInputModelPanel);

        inputView = new InputViewGridLayoutPanel(inputViews, inputControlBar);
    }

    /**
     * Initialize a new Input Model View State. Input Model View State
     * manages the Input View State for the simulation. They enable and
     * disable user functionality based on the Input Models that have
     * been loaded.
     */
    @Override
    public void initInputModelViewState()
    {
        systemInputModelState = new InputViewState();
    }

    /**
     * Initialize the Network Mediator. The Network Mediator manages the
     * Network View and Network Output Models by decoupling everything
     * with a Mediator Pattern.
     */
    @Override
    public void initNetworkMediator()
    {
        networkMediator = new NetworkMediator(this.systemInputModel, this.convergenceOutputModel, this.steadyStateOutputModel);
    }

    /**
     * Initialize the Network Output Models. The Network Output Models
     * manage the Network State from the Network Algorithm Models they Observe.
     */
    @Override
    public void initOutputModels()
    {
        super.convergenceOutputModel = new ConvergenceOutputModel();
        super.iterationOutputModel = new IterationOutputModel();
        super.steadyStateOutputModel = new SteadyStateOutputModel();
        ((ConvergenceAlgorithmModelInterface) convergenceSimulation).registerObserver((ConvergenceAlgorithmModelObserver) convergenceOutputModel);
        ((IterationAlgorithmModelInterface) convergenceSimulation).registerObserver((IterationAlgorithmModelObserver) iterationOutputModel);
        ((SteadyStateAlgorithmModelInterface) convergenceSimulation).registerObserver((SteadyStateAlgorithmModelObserver) steadyStateOutputModel);
    }

    /**
     * Initialize the Optimize Controller. It is responsible for
     * managing the interaction between the Optimize Output View and the
     * Optimize Algorithm Model.
     */
    public void initOptimizeController()
    {
        this.optimizeView = new OptimizeSystemPanel(this.optimizeControlBar, this.convergenceOutputModel, this.steadyStateOutputModel);
        this.optimizeController = new OptimizeController(optimizeView);
        this.optimizeView.setController(optimizeController);
        ((SimulationMediator)this.simulationMediator).setOptimizeController(optimizeController);
    }

    /**
     * Initialize the Output Model View. Output Model Views generally display
     * Outputs from the simulations Input Models.
     */
    @Override
    public void initOutputModelViews()
    {
        outputView = new ModelOutputDefaultLayoutView(outputControlBar,
                ((SSMediator) ssMediator).getView(), ((PlotMediatorInterface) plotMediator).getView(),
                ((NetworkMediatorInterface) networkMediator).getView());
    }

    /**
     * Initialize a Plot Mediator. The Plot Mediator manages the Plot Models
     * and Plot Views by decoupling everything with a Mediator Pattern.
     */
    @Override
    public void initPlotMediator()
    {
        plotMediator = new PlotMediator(convergenceOutputModel, this.steadyStateOutputModel);
    }

    /**
     * Initialize the Simulation Properties. The Simulation Properties are
     * responsible for managing the State of the Simuation. This State can be
     * the Type of simulation, or even details about specific simulation Type's
     * that the user has specified.
     */
    @Override
    public void initSimulationProperties()
    {
        simulationPropertiesState = new SimulationPropertiesState();
        simulationPropertiesFrame = new SimulationPropertiesFrame(simulationPropertiesState);
        
        ((SimulationPropertiesState) simulationPropertiesState).setView(simulationPropertiesFrame);
    }

    /**
     * Initialize a Simulation Mediator. A Simulation Mediator mediates all
     * of the simulations Mediators/Controllers.
     */
    @Override
    public void initSimulationMediator()
    {
        simulationMediator = new SimulationMediator(systemInputModel, convergenceSimulation,
                simulationPropertiesFrame, (NetworkMediator) this.networkMediator,
                (PlotMediator) this.plotMediator,
                (SSMediator) this.ssMediator);
    }

    /**
     * Initialize the Simulation Actions. Simulation Actions are ActionListeners
     * used to decouple Actions between classes.
     */
    @Override
    public void initSimulationActions()
    {
        this.feedbackController = new FeedbackController(this.systemInputModel,this.iterationOutputModel,this.convergenceSimulation);

        this.feedbackAction = new FeedbackAction(this.feedbackController);

        simulationBarAction = new SimulationBarAction(simulationMediator);

        propertiesBarAction = new PropertiesBarAction(simulationMediator,
                simulationPropertiesState);

        modelOuputBarAction = new OutputBarAction((SSMediator) ssMediator);
        newtorkViewBarAction = new NetworkBarAction((NetworkMediator) networkMediator);
        viewBarAction = new ViewBarAction();
        plotViewBarAction = new PlotBarAction((PlotMediator) plotMediator);
        runSimulationAction = new RunSimulationBarAction(
                (NetworkMediator) networkMediator, (PlotMediator) plotMediator, simulationMediator,
                (SSMediator) ssMediator);
    }

    /**
     * Initialize the Simulation View. The Simulation View is the main
     * View for the OQM Simulation.
     */
    @Override
    public void initSimulationView()
    {
        view = new SimControlView(newtorkViewBarAction, modelOuputBarAction,
                simulationBarAction, plotViewBarAction, propertiesBarAction,
                runSimulationAction, viewBarAction, feedbackAction, inputView,
                outputView, optimizeView);
        ((ViewBarAction) viewBarAction).setView(view);
        ((SimulationViewState) simulationInputState).setView(view);
        ((SimulationViewState) simulationInputState).init();
    }

    /**
     * Initialize the Spreadsheet Mediator. The Spreadsheet Mediator manages
     * the Spreadsheet Output Models and Spreadsheet Output Views by using a
     * Mediator Pattern to decouple everything.
     */
    @Override
    public void initSSMediator()
    {
        ssMediator = new SSMediator(this.convergenceOutputModel, this.iterationOutputModel, this.steadyStateOutputModel);
    }

    /**
     * Initialize the View State for the OQM Simulation.
     */
    @Override
    public void initViewState()
    {
        simulationTypeViewState = new SimulationTypeViewState((ControlBar) inputControlBar, (ControlBar) outputControlBar, (ControlBar) optimizeControlBar, view);
        simulationViewState = new SimulationViewOutputState((ControlBar) inputControlBar, (ControlBar) outputControlBar, (ControlBar) optimizeControlBar, view);
        networkViewState = new NetworkMediatorViewState((ControlBar) inputControlBar, (ControlBar) outputControlBar, view);
        ((NetworkMediator) networkMediator).setNetworkViewState(networkViewState);
        ((NetworkMediator) networkMediator).animateNetwork(true);
        ((ViewBarAction) viewBarAction).setViewTypeState(simulationTypeViewState);
        ((SimulationMediator) this.simulationMediator).setSimulationViewOutputState(simulationViewState);
        ((SimulationMediator) this.simulationMediator).setSimulationViewState(simulationInputState);
    }
}
