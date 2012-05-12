/*
AbstractSimulationFactory-- A abstract class within OQM(Open Queuing Model).
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

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import oqm.feedback.controller.FeedbackController;
import oqm.input.model.changeEvent.InputModelChangeEvent;
import oqm.input.view.layout.InputViewGridLayoutPanel;
import oqm.network.mediator.NetworkMediatorInterface;
import oqm.network.mediator.state.NetworkMediatorViewStateInterface;
import oqm.optimize.controller.OptimizeController;
import oqm.optimize.view.OptimizeSystemPanel;
import oqm.output.model.IterationOutputModel;
import oqm.output.model.SteadyStateOutputModel;
import oqm.output.view.layout.ModelOutputDefaultLayoutView;
import oqm.simulation.properties.view.SimulationPropertiesFrame;
import oqm.simulation.properties.view.state.SimulationPropertiesStateInterface;
import oqm.simulation.view.SimControlView;
import oqm.simulation.view.state.SimulationViewStateInterface;
import oqm.simulation.view.state.output.SimulationViewOutputStateInterface;
import oqm.simulation.view.state.simulator.SimulationTypeViewStateInterface;
import simulyn.algorithm.model.AlgorithmModelInterface;
import simulyn.input.controller.InputControllerInterface;
import simulyn.input.model.InputModelInterface;
import simulyn.input.view.state.InputViewStateInterface;
import simulyn.output.model.OutputModelInterface;
import simulyn.output.view.mediator.OutputViewMediatorInterface;
import simulyn.simulation.mediator.SimulationMediatorInterface;
import simulyn.ui.components.inputModel.InputViewAbstractExtraLarge;

/**
 * AbstractSimulationFactory is an implementation of the
 * SimulationFactoryInterface.
 * The abstraction is designed to allow the OQM application to flexibly manage
 * the Input, Algorithm and Output Modules within the MVC/MVA architecture. These
 * Modules are usually concerned with controlling other MVC/MVA's that support
 * OQM, managing input and output, managing state and managing the applications
 * algorithms. 
 * @author Kaleb
 */
public abstract class AbstractSimulationFactory implements SimulationFactoryInterface
{

    // Model Input Actions
    protected ActionListener systemInputModelPanelAction;
    // Control Bar Actions
    protected ActionListener feedbackAction;
    protected ActionListener simulationBarAction;
    protected ActionListener propertiesBarAction;
    protected ActionListener modelOuputBarAction;
    protected ActionListener viewBarAction;
    protected ActionListener newtorkViewBarAction;
    protected ActionListener plotViewBarAction;
    protected ActionListener runSimulationAction;
    protected ActionListener outputControllViewAction;
    protected ArrayList<InputModelInterface> inputModels;
    protected ArrayList<InputViewAbstractExtraLarge> inputViews;
    protected AlgorithmModelInterface convergenceSimulation;

    protected FeedbackController feedbackController;
    protected ItemListener outputMediatorEvent;
    // Model Input Controllers
    protected InputControllerInterface systemInputModelController;
    // Model Input Models
    protected InputModelInterface systemInputModel;
    // Model Input Change Event
    protected InputModelChangeEvent modelChanged;
    // Model Input Views
    protected InputViewGridLayoutPanel inputView;
    protected InputViewAbstractExtraLarge systemInputModelPanel;
    // Model Input States
    protected InputViewStateInterface systemInputModelState;
    protected JPanel outputControlBar;
    protected JPanel inputControlBar;
    protected JPanel optimizeControlBar;
    protected ModelOutputDefaultLayoutView outputView;
    protected NetworkMediatorInterface networkRendererMediator;
    protected NetworkMediatorViewStateInterface networkViewState;
    protected SimulationMediatorInterface simulationMediator;
    protected OptimizeController optimizeController;
    protected OptimizeSystemPanel optimizeView;
    // Model Result Models
    protected OutputModelInterface convergenceOutputModel;
    protected OutputViewMediatorInterface ssMediator;
    protected OutputViewMediatorInterface networkMediator;
    protected OutputViewMediatorInterface plotMediator;
    // Model Views
    protected SimControlView view;
    protected SimulationPropertiesStateInterface simulationPropertiesState;
    protected SimulationPropertiesFrame simulationPropertiesFrame;
    protected SimulationViewOutputStateInterface simulationViewState;
    // Model Result State
    protected SimulationViewStateInterface simulationInputState;
    protected SimulationTypeViewStateInterface simulationTypeViewState;
    protected IterationOutputModel iterationOutputModel;
    protected SteadyStateOutputModel steadyStateOutputModel;
}
