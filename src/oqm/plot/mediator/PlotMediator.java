/*
PlotMediator -- a class within the OQM(Open Queuing Model).
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
package oqm.plot.mediator;

import javax.swing.JPanel;
import oqm.output.model.ConvergenceOutputModel;
import oqm.output.model.SteadyStateOutputModel;
import oqm.output.model.observer.ConvergenceOutputModelObserver;
import oqm.output.model.observer.SteadyStateOutputModelObserver;
import org.math.plot.Plot2DPanel;
import simulyn.output.model.OutputModelInterface;
import simulyn.output.view.PlotViewInterface;
import simulyn.output.view.mediator.OutputViewMediatorInterface;
import simulyn.ui.components.plotter.SimulynDefaultPlotView;

/**
 * OutputViewMediatorInterface implementations are used to completely decouple
 * the Model from the View using a Model-View-Mediator (MVM) architecture. This
 * pattern is also known as a Model-View-Presenter using a Passive View strategy.
 * It has a single method used to update the view with new state: updateUI().
 *
 * Mediators differ from Controllers in how they couple the View to the Model.
 *
 * With a Controller, the View requests Actions to be taken upon the Model via
 * the Controller. The Controller will call the Model and when the the Model is
 * ready it will update the View with the new State.
 * With a Mediator, the View requests Actions to be taken upon the Model via the
 * Mediator and the Model will update the Mediator (not the View) with the new
 * State. After the the Mediator appropriately renders the State, the
 * Mediator will then provide the View with the rendered State.
 *
 * An MVM is used to manage the Views for the Network UI, Plot UI, and Spreadsheet UI.
 * This is because we want the UI's to be completely decoupled from any form
 * of a model or controller, mediator, presenter, etc... We want the UI's to be as
 * decoupled and as portable as possible so they can be reused in many ways.
 *
 * PlotMediator is a implementation providing a coupling between
 * the Output Models and the Output Views. It manages a Plot UI View that is
 * designed to allow the user to view the simulations Output Models in a graphical
 * environment. It provides the logic for the View's Actions and renders the
 * Output Models with a two-dimensional Plot used to render a Scatter Plot
 * and Line Plot.
 * @author Kaleb
 */
public class PlotMediator implements OutputViewMediatorInterface,
        PlotMediatorInterface, ConvergenceOutputModelObserver,
        SteadyStateOutputModelObserver
{
    // Lists to keep track of model outputs locally so they can
    // be rendered and pushed to the Plot View.

    private double[][] steadyStateMatrix;
    // The Point Output Model for non-Animated Simulations.
    private OutputModelInterface convergenceOutputModel;
    private SteadyStateOutputModel steadyStateOutputModel;
    // The Plot2DPanel that backs the Plot View. The Plot2DPanel has its
    // State set here and is then pushed to the Plot View.
    private Plot2DPanel plot;
    // The SwingWorker is used to create the new Plot for the
    // Plot View. This SwingWorker manages results from MultiplePointSimulations.
    // The View the Mediator manages
    private SimulynDefaultPlotView view;

    public PlotMediator(OutputModelInterface convergenceOutputModel,
            SteadyStateOutputModel steadyStateOutputModel)
    {
        // local instance of the result model
        this.convergenceOutputModel = convergenceOutputModel;
        this.steadyStateOutputModel = steadyStateOutputModel;
        // this class should observe changes to the result model
        ((ConvergenceOutputModel) this.convergenceOutputModel).registerObserver((ConvergenceOutputModelObserver) this);
        ((SteadyStateOutputModel) this.steadyStateOutputModel).registerObserver(this);

        // Create a new instance of the View.
        view = new SimulynDefaultPlotView();

        // Create a new Plot that will eventually be set with
        // the simulations Output Model State and then pushed to the View.
        plot = new Plot2DPanel();

        steadyStateMatrix = new double[0][0];
    }

    /**
     * Returns an instance of the Plot that is currently being used and
     * will eventually be pushed to the View after the State is added
     * by one of the SwingWorkers.
     * @return a Plot with or without State.
     */
    @Override
    public Plot2DPanel getPlot()
    {
        return plot;
    }

    /**
     * Get the Plot Mediator View. 
     * @return the JPanel representing the Plot Mediator View.
     */
    @Override
    public JPanel getView()
    {
        return view;
    }

    /**
     * Remove all plots from the current Plot and update the View.
     */
    @Override
    public void onClearUI()
    {
        plot.removeAllPlots();
        updateUI();
    }

    /**
     * Renders a Line Plot representing the amount of time the simulation
     * took to run versus the number of inputs fed into the simulations
     * network. 
     */
    @Override
    public void onLinePlot()
    {
    }

    /**
     * Renders a Scatter Plot representing the Points that hit shapes and Points
     * that missed shapes during the simulation
     */
    @Override
    public void onScatterPlot()
    {
    }

    /**
     * Called when the Mediator has new State ready to be pushed to the View.
     */
    @Override
    public void updateUI()
    {
        ((PlotViewInterface) view).setPlot(plot);
    }

    public void updateConvergenceOutputModelOutput(double[][] modelResult)
    {
        this.steadyStateMatrix = modelResult;
    }

    public void updateSteadyStateOutputModelOutput(boolean converged)
    {
        if (converged)
        {
            double[] y = new double[steadyStateMatrix.length];
            for (int i = 0; i < steadyStateMatrix.length; i++)
            {
                y[i] = steadyStateMatrix[0][i];
            }

            plot.addBarPlot("Steady State", y);
            plot.setFixedBounds(1, 1, 1);

            updateUI();
        }
    }
}

