/*
InputViewGridLayoutPanelAbstract -- an abstract class within the
OQM (Open Queuing Model).
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
package oqm.input.view.layout;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import oqm.globals.Globals;
import simulyn.ui.components.inputModel.InputViewAbstractExtraLarge;

/**
 * A special JPanel that provides the layout for the Input Model Views.
 * @author Kaleb
 */
public class InputViewGridLayoutPanel extends InputViewLayoutPanelAbstract
{

    private GridLayout layout;

    public InputViewGridLayoutPanel(ArrayList<InputViewAbstractExtraLarge> inputPanels, JPanel controlBar)
    {
        super(controlBar);
        this.inputPanels = inputPanels;
        this.layout = new GridLayout(1, 1);
        this.modelPanel.setLayout(layout);

        this.modelPanel.add(inputPanels.get(Globals.SYSTEM_INPUT_MODEL));
    }
}
