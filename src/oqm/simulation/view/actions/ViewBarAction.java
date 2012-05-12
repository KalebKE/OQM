/*
ViewBarAction -- A class within the OQM (Open Queuing Model).
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
package oqm.simulation.view.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oqm.simulation.view.SimControlView;
import oqm.simulation.view.state.simulator.SimulationTypeViewStateInterface;

/**
 * ViewBarAction is an ActionListener implementation used
 * to manage the Actions from the Simulation Control View.
 * @author Kaleb
 */
public class ViewBarAction implements ActionListener
{

    private SimControlView view;
    private SimulationTypeViewStateInterface viewTypeState;

    /**
     * Initialize a new ViewCarAction.
     */
    public ViewBarAction()
    {
        super();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("useOutputViewAction"))
        {
            view.setOutputView();
            viewTypeState.onSimulatorOutputView();
        }
        if (e.getActionCommand().equals("useInputViewAction"))
        {
            view.setInputView();
            viewTypeState.onSimulatorInputView();
        }

        if (e.getActionCommand().equals("useOptimzerViewAction"))
        {
            view.setOptimizeView();
            viewTypeState.onSimulatorOptimizeView();
        }
    }

    /**
     * Set the Simulation Control View.
     * @param view the SimControlView. 
     */
    public void setView(SimControlView view)
    {
        this.view = view;
    }

    public void setViewTypeState(SimulationTypeViewStateInterface viewTypeState)
    {
        this.viewTypeState = viewTypeState;
    }
}
