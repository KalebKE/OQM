/*
SSMediator -- A class within the OQM (Open Queuing Model).
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
package oqm.spreadsheet.mediator;

import file.save.controller.SaveFileControllerInterface;
import file.save.controller.SaveSpreadsheetFileController;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import oqm.output.model.ConvergenceOutputModel;
import oqm.output.model.IterationOutputModel;
import oqm.output.model.SteadyStateOutputModel;
import oqm.output.model.observer.ConvergenceOutputModelObserver;
import oqm.output.model.observer.IterationOutputModelObserver;
import oqm.output.model.observer.SteadyStateOutputModelObserver;
import simulyn.output.model.OutputModelInterface;
import simulyn.output.view.mediator.OutputViewMediatorInterface;
import simulyn.ui.components.modelRenderer.SimulynLabeledTableRenderer;

/**
 * ModelViewMediatorInterface implementations are used to completely decouple
 * the Model from the View using a Model-View-Mediator (MVM) architecture. This
 * pattern is also known as a Model-View-Presenter using a Passive View strategy.
 *
 * Mediators differ from Controllers in how they couple the View to the Model.
 * With a Controller, the View requests Actions to be taken upon the Model via
 * the Controller and the Model will then update the View with the new State.
 * With a Mediator, the View requests Actions to be taken upon the Model via the
 * Mediator and the Model will update the Mediator with the new State. The Mediator
 * will then provide the View with the new State.
 *
 * An MVM is used to manage the Views for the Network UI, Plot UI, and Result UI.
 * This is because we want the UI's to be completely decoupled from any form
 * of a model or controller, mediator, presenter, etc... We want the UI's to be as
 * decoupled and as portable as possible so they can be reused in many ways.
 * But we don't know how the UI's might be used in the future. This is why
 * the MVM is used.
 *
 * SSMediator is a implementation that
 * provides a coupling between OQM's Output Models and Spreadsheet View.
 * It provides the logic for the View's Actions and renders the Output Models
 * with a TableModel used to back a JTable within the View.
 *
 * @author Kaleb
 */
public class SSMediator implements OutputViewMediatorInterface,
        SSMediatorInterface, ConvergenceOutputModelObserver, IterationOutputModelObserver,
        SteadyStateOutputModelObserver
{

    private double[][] steadyStateMatrix;
    // Table Model for the View's JTable that is pushed to the View
    // once the the Result Model State has been added to the tableModel.
    private DefaultTableModel tableModel;
    private IterationOutputModel iterationOutputModel;
    // The Point Output Model for non-Animated Simulations.
    private OutputModelInterface convergenceOutputModel;
    private SteadyStateOutputModel steadyStateOutputModel;
    private SaveFileControllerInterface saveFileController;
    // The View the Mediator manages
    private SimulynLabeledTableRenderer view;

    /**
     * Initialize a new ResultTableMediator.
     *@param targetModel the Input Target Model is used for Diagnostic Simulations.
     *
     * @param simulationModelResult the Simulation Result Model implementation to be
     * used by the Mediator. It Observes the SinglePointSimulations and
     * MultiplePointSimluations for new Model Results. It is also a Subject
     * Obsererved by this class for new Model Results.
     * 
     * @param shapesRatioModelResult the Shapes Ratio Result implementation to be
     * used by the Mediator. It Observes the SinglePointSimulations and
     * MultiplePointSimluations for new Model Results and then Transforms the
     * results into a ratio of point misses/hits for each shape within the image.
     * It is also a Subject Obsererved by this class for new Model Results.
     * 
     * @param imageRatioResultthe Shapes Ratio Result implementation to be
     * used by the Mediator. It Observes the SinglePointSimulations and
     * MultiplePointSimluations for new Model Results and then Transforms the
     * results into a ratio of point misses/hits from the image. It is also a
     * Subject Obsererved by this class for new Model Results.
     *
     * @param simulationPropertiesState the Simulation Properties State to
     * ensure the correct Output Models are rendered.
     */
    public SSMediator(OutputModelInterface convergenceOutputModel,
            IterationOutputModel iterationOutputModel,
            SteadyStateOutputModel steadyStateOutputModel)
    {
        // local instance of the result model
        this.convergenceOutputModel = convergenceOutputModel;
        // this class should observe changes to the result model
        ((ConvergenceOutputModel) this.convergenceOutputModel).registerObserver((ConvergenceOutputModelObserver) this);

        this.iterationOutputModel = iterationOutputModel;
        // this class should observe changes to the result model
        ((IterationOutputModel) this.iterationOutputModel).registerObserver((IterationOutputModelObserver) this);

        this.steadyStateOutputModel = steadyStateOutputModel;
        // this class should observe changes to the result model
        ((SteadyStateOutputModel) this.steadyStateOutputModel).registerObserver((SteadyStateOutputModelObserver) this);

        // Create a new View to Mediate.
        this.view = new SimulynLabeledTableRenderer();
        this.view.getTableInfoLabel().setText("No Steady State Model Avaiable");
        this.view.getModelInformationLabel().setText("No Model Information Available");
        this.tableModel = new DefaultTableModel();
        steadyStateMatrix = new double[0][0];
    }

    /**
     * Get the TableModel used to back the JTable in the View. This method
     * is usually called by the MediatorStateInterface classes are responsible
     * for managing the TableModel's State.
     * @return the DefaultTableModel used to back the JTable in the View.
     */
    public DefaultTableModel getTableModel()
    {
        return (DefaultTableModel) view.getTable().getModel();
    }

    /**
     * Get the Spreadsheet Mediator View.
     * @return the View.
     */
    @Override
    public JPanel getView()
    {
        return view;
    }

    /**
     * Provides the logic for when a View Action requests that
     * the Model Result be cleared from the View.
     */
    @Override
    public void onClearModelResult()
    {
        Object[] names =
        {
            "Simulation Results"
        };
        this.setTableModel(new DefaultTableModel(names, 20));
        this.view.getModelInformationLabel().setText("No Steady State Model Available");
        this.view.getModelInformationLabel().setForeground(Color.black);
        this.view.getTableInfoLabel().setText("No Model Information Available");
        this.view.getTableInfoLabel().setForeground(Color.black);
        this.updateUI();
    }

    /**
     * Set the TableModel that will be pushed to the JTable
     * in the View.
     * @param tableModel the TableModel containing the State
     * from the Result Models that will be pushed to the View.
     */
    public void setTableModel(DefaultTableModel tableModel)
    {
        this.tableModel = tableModel;
    }

    /**
     * Called when the Mediator has new State ready to be pushed to the View.
     */
    @Override
    public void updateUI()
    {
        // Set the rendered Table Model for the View.
        view.setModel(tableModel);
        // Prepare a new Table Model.
        this.tableModel = new DefaultTableModel();
    }

    /**
     * Provides the logic for when a View Action requests that the Result
     * Models should be persisted.
     */
    @Override
    public void onSaveSimulationOutput()
    {
        int numRow = this.getTableModel().getRowCount();
        int numCol = this.getTableModel().getColumnCount();

        double[][] output = new double[numRow][numCol];

        for (int i = 0; i < numRow; i++)
        {
            for (int j = 0; j < numCol; j++)
            {
                if (this.getTableModel().getValueAt(i, j) != null)
                {
                    output[i][j] = (Double) this.getTableModel().getValueAt(i, j);
                }
            }
        }

        saveFileController =
                new SaveSpreadsheetFileController(output);
    }

    public void updateConvergenceOutputModelOutput(double[][] modelResult)
    {
        this.steadyStateMatrix = modelResult;
        tableModel.setRowCount(this.steadyStateMatrix.length);
        tableModel.setColumnCount(this.steadyStateMatrix[0].length);
        DecimalFormat decFormat = new DecimalFormat("0.0000");

        for (int i = 0; i < this.steadyStateMatrix.length; i++)
        {
            for (int j = 0; j < this.steadyStateMatrix[i].length; j++)
            {
                tableModel.setValueAt(decFormat.format(modelResult[i][j]), i, j);
            }
        }

        this.updateUI();
    }

    public void updateIterationOutputModelOutput(int iterations)
    {
        view.getModelInformationLabel().setText("Iterations: " + iterations);
        view.getModelInformationLabel().setFont(new Font("Arial", Font.BOLD, 12));
    }

    public void updateSteadyStateOutputModelOutput(boolean converged)
    {
        view.getTableInfoLabel().setFont(new Font("Arial", Font.BOLD, 12));
        if (converged)
        {
            view.getTableInfoLabel().setText("Steady State Found");
            view.getTableInfoLabel().setForeground(Color.blue);
            view.getModelInformationLabel().setForeground(Color.blue);
        }
        if (!converged)
        {
            view.getTableInfoLabel().setText("Steady State Not Found");
            view.getTableInfoLabel().setForeground(Color.red);
            view.getModelInformationLabel().setForeground(Color.red);
        }

    }
}
