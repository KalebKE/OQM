/*
SimulationFactoryInterface -- An interface within the OQM(Open Queuing Model).
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
package oqm.factory;

/**
 * An interface for OQMSimulationFactories. OQMSimulationFactories are responsible for
 * initializing all of objects required for the application. The OQM application
 * is based on a model-view-controller (MVC) architecture. The different modules of the
 * architecture generally communicate via their own Observers. This allows different
 * modules to be "pluged-in" to the application.
 * 
 * The MVC modules are sub-classified into Input, Algorithm and Output. Input Modules are
 * concerned with managing state that will be used as an input (like matricies) in the application.
 * Output Modules are concered with managing state that will be used as an output
 * (like the results of a simulation) in the application. It is up to the implementations
 * to decide exactly what qualifies as Input and Output.
 * An Algorithm Module sits between the Input and Output Models and is responsible
 * for actually running the Algorithm based on the Input Modules State and
 * then providing the output to the Output Modules.
 *
 * Implementing SimulationFactoryInterface allows for a custom implementation of how all
 * of the modules are "pluged-in". 
 * @author Kaleb Kircher
 */
public interface SimulationFactoryInterface
{

    /**
     * Initialize the OQM Algorithm Models. The Alogirthm Models can be thought
     * of as the individual simulations. They are responsible for running
     * the simulations and coordianting all of the supporting classes.
     */
    public void initAlgorithmModels();

    /**
     * Initialize the Simulation Control Bars. The Control Bars are part
     * of the UI. They allow the user to control all of the key functionality
     * of OQM with one-click of the mouse.
     */
    public void initControlBarViews();

    /**
     * Initialize the Input Model Controllers. The Input Model Controllers
     * use a Controller pattern to manage the Input View and Input Models in a
     * Model-View-Controller architecture.
     */
    public void initInputModelControllers();

    /**
     * Initialize the simulation Input Models. The Input Models are responsible
     * for managing all of the inputs required for the simluations algorithms
     * to run. The inputs for the algorithms can come from external files or
     * can be generated within OQM by the user. The Input Models are part of a
     * Model-View-Controller architecture and push new State to their Observers,
     * usually Input View's.
     */
    public void initInputModels();

    /**
     * Initialize the simulations Input Model Actions. Input Model Actions
     * decouple the Input Controllers from the Input Models and Input Views
     * by using an ActionListener to manage the dependencies and forward
     * the Actions to the appropriate classes.
     */
    public void initInputModelActions();

    /**
     * Initialize the Input Model Change Event. The Change Event watches the
     * Input Models and determines if they have been loaded into OQM. Once
     * all of the Input Models have been loaded, it notifies other classes
     * that new simulation state should be enabled.
     */
    public void initInputModelChangeEvent();

    /**
     * Initialize Input Model Views. Input Model Views Render the State
     * from the Input Models so the user can interact with the State in a
     * graphical environment (usually a spreadsheet).
     */
    public void initInputModelViews();

    /**
     * Initialize a new Input Model View State. Input Model View State
     * manages the Input View State for the simulation. They enable and
     * disable user functionality based on the Input Models that have
     * been loaded.
     */
    public void initInputModelViewState();

    /**
     * Initialize the Network Mediator. The Network Mediator manages the
     * Network View and Network Output Models by decoupling everything
     * with a Mediator Pattern.
     */
    public void initNetworkMediator();

    /**
     * Initialize the Network Output Models. The Network Output Models
     * manage the Network State from the Network Algorithm Models they Observe.
     */
    public void initOutputModels();

    /**
     * Initialize the Output Model View. Output Model Views generally display
     * Outputs from the simulations Input Models.
     */
    public void initOutputModelViews();

    /**
     * Initialize a Plot Mediator. The Plot Mediator manages the Plot Models
     * and Plot Views by decoupling everything with a Mediator Pattern.
     */
    public void initPlotMediator();

    /**
     * Initialize the Simulation Actions. Simulation Actions are ActionListeners
     * used to decouple Actions between classes.
     */
    public void initSimulationActions();

    /**
     * Initialize a Simulation Mediator. A Simulation Mediator mediates all
     * of the simulations Mediators/Controllers.
     */
    public void initSimulationMediator();

    /**
     * Initialize the Simulation Properties. The Simulation Properties are
     * responsible for managing the State of the Simuation. This State can be
     * the Type of simulation, or even details about specific simulation Type's
     * that the user has specified.
     */
    public void initSimulationProperties();

    /**
     * Initialize the Simulation View. The Simulation View is the main
     * View for the OQM Simulation.
     */
    public void initSimulationView();

    /**
     * Initialize the Spreadsheet Mediator. The Spreadsheet Mediator manages
     * the Spreadsheet Output Models and Spreadsheet Output Views by using a
     * Mediator Pattern to decouple everything.
     */
    public void initSSMediator();

    /**
     * Initialize the View State for the OQM Simulation.
     */
    public void initViewState();
}
