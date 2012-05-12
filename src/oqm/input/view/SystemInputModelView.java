/*
SystemInputModelView -- a class within the OQM (Open Queuing Model).
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
package oqm.input.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import oqm.algorithm.model.math.ValidateProbabilities;
import oqm.algorithm.model.math.WarshallAlgorithm;
import oqm.input.model.changeEvent.InputModelChangeEvent;
import oqm.input.model.observer.SystemInputModelObserver;
import simulyn.input.controller.InputControllerInterface;
import simulyn.input.model.InputModelInterface;
import simulyn.input.view.state.InputViewStateInterface;
import simulyn.ui.components.inputModel.InputViewAbstractExtraLarge;
import simulyn.ui.components.spreadsheetTable.SimTable;

/**
 * SystemInputModelView is the View for the System Matrix Input Model within OQM.
 * Observers of the SystemInputModel Subject have state pushed to them through the
 * SystemAlgorithmModelObserver interface. When the SystemInputModel receives new State,
 * it notifies its Observers of the change. This updates the State within
 * the SystemInputModelView and its Rendering View, which is responsible for
 * displaying the State itself. 
 * InputViewAbstract implementations are intended to manage the Input Model Views
 * within the Simulyn Framework. They are the View in a Model-View-Controller
 * architecture that manages the Input Models. Within Simulyn, Input Model's
 * are the simulation's model's inputs. Some simulations require many inputs
 * for their model's, some require just one input. Each InputViewAbstract
 * implementation instance is intended to manage one simulation model input.
 * @author Kaleb
 */
public class SystemInputModelView extends InputViewAbstractExtraLarge implements SystemInputModelObserver
{

    /**
     * Initialize the SystemInputModelView.
     * @param action the Action Listener responsible for managing the Actions
     * for the InputViewAbstract implementation.
     * @param inputController the Input Controller that manages the System Input
     * Model and System Input Model View.
     * @param inputModel the Input Model Interface that will back the
     * InputViewAbstract implementation.
     * @param inputViewState the state of the Input View implementation.
     * @param inputModelChanged the Model Change Event Listener for the Input
     * Models.
     */
    public SystemInputModelView(ActionListener action, InputControllerInterface inputController,
            InputModelInterface inputModel,
            InputViewStateInterface inputViewState,
            InputModelChangeEvent inputModelChanged)
    {
        super(action, inputController, inputModel, inputViewState, inputModelChanged);
        this.inputPanelHeaderLabel.setText("System Matrix: Transitions");
        this.inputPanelDescriptionLabel.setText("Current System: Defines Transition Probabilities");
        this.getInfoLabelLeft().setText("");
        this.getInfoLabelRight().setText("");
        this.getInfoLabelLeft().setFont(new Font("Arial", Font.BOLD, 12));
        this.getInfoLabelRight().setFont(new Font("Arial", Font.BOLD, 12));
    }

    /**
     * Hook for the System Input Model Subject. Renders the transition
     * probabilities matrix in the GUI.
     * @param modelInput the new model. 
     */
    @Override
    public void updateSystemInputModel(double[][] modelInput)
    {
        double[][] localModelInput = modelInput;
        if (modelInput.length == 0)
        {
            ((SimTable) this.inputPane).clearModel();
            this.inputViewState.inputModelReady(false);
        } else
        {
            boolean closure = WarshallAlgorithm.run(localModelInput);
            boolean transferProbabilities = ValidateProbabilities.validateProbabilities(localModelInput);
            if (closure)
            {
                this.getInfoLabelLeft().setForeground(Color.BLUE);
                this.getInfoLabelLeft().setText("Valid Closure");
            }
            if (!closure)
            {
                this.getInfoLabelLeft().setForeground(Color.red);
                this.getInfoLabelLeft().setText("Invalid Closure");
            }
            if (transferProbabilities)
            {
                this.getInfoLabelRight().setForeground(Color.BLUE);
                this.getInfoLabelRight().setText("Valid Transition Probabilities");
            }
            if (!transferProbabilities)
            {
                this.getInfoLabelRight().setForeground(Color.red);
                this.getInfoLabelRight().setText("Invalid Transition Probabilities");
            }

            // Set the model for the Rendering View.
            ((SimTable) this.inputPane).setModel(modelInput);
            // Indicate that the Input View State should change to reflect
            // that an Input Model has been loaded.
            this.inputViewState.inputModelReady(true);
            // Inform the Input Model Change Event that an Input Model has been loaded.
            this.firePropertyChange("propertiesChanged", !inputModel.isModelInputReady(), inputModel.isModelInputReady());
        }
    }
}
