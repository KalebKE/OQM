/*
InputModelViewAction -- a class class within the OQM(Open Queuing Model).
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
package oqm.input.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import simulyn.input.controller.InputControllerInterface;
import simulyn.input.model.InputModelInterface;
import simulyn.ui.components.inputModel.InputViewAbstractExtraLarge;
import simulyn.ui.components.spreadsheetTable.SimTable;


/**
 * An Action Listener for the Input Model Views. Action Listeners are
 * used to decouple the View's from the Controller's. This has a bunch of
 * advantages and makes it easy to change the behavior of View's without having
 * to actually change any code within the View.
 * @author Kaleb
 */
public class InputModelViewAction implements ActionListener
{

    private InputControllerInterface inputController;
    private InputModelInterface inputModel;
    private InputViewAbstractExtraLarge view;

    /**
     * Initialize a new InputModelViewAction. Input Model View Action
     * is responsible for managing the Actions requested from the Input
     * Model View GUI. This includes opening, saving, and setting the Input Model
     * so it can be used by the rest of the application.
     * @param inputController the Input Model Controller
     * @param inputModel the Input Model
     */
    public InputModelViewAction(InputControllerInterface inputController,
            InputModelInterface inputModel)
    {
        this.inputController = inputController;
        this.inputModel = inputModel;
    }

    /**
     * The Input Model Action Event's.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("importModelAction"))
        {
            inputController.onImportInputModel();
        }
        if (actionCommand.equals("saveModelAction"))
        {
            inputController.onExportInputModel();
        }
        if (actionCommand.equals("setModelAction"))
        {
            inputModel.setModelInput(((SimTable) view.getInputPane()).getModel());
        }
    }

    /**
     * Set the View.
     * @param view the Input Model View.
     */
    public void setView(InputViewAbstractExtraLarge view)
    {
        this.view = view;
    }    
}
