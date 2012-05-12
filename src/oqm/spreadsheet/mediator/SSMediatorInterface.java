/*
SSMediatorInterface -- An interface within the OQM (Open Queuing Model).
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

import javax.swing.JPanel;

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
 * SSMediatorInterface is an interface that
 * provides a coupling between OQM's Output Models and Spreadsheet View.
 * It provides the logic for the View's Actions and renders the Output Models
 * with a TableModel used to back a JTable within the View.
 *
 * @author Kaleb
 */
public interface SSMediatorInterface
{

    /**
     * Get the Spreadsheet Mediator View.
     * @return the ssMediatorView.
     */
    public JPanel getView();

    /**
     * Clear the Model Rendering.
     */
    public void onClearModelResult();

    /**
     * Implementations should initialize the classes
     * that will save the results data to an external file.
     * This can be accomplished by accessing a file controller
     * and requesting a Save File Chooser (usually a JFileChooser)
     * and passing it the results.
     * Example:
     * {
     * fileController.getSaveFileChooser(results);
     * }
     */
    public void onSaveSimulationOutput();
}
