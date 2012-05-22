/*
InputModelChangeEvent -- a class class within the OQM(Open Queuing Model).
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
package oqm.input.model.changeEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import oqm.globals.Globals;
import oqm.simulation.view.state.SimulationViewStateInterface;
import simulyn.input.model.InputModelInterface;

/**
 * Input Model Change Event keeps tracks of what Input Models have been loaded
 * and what Input Models still need to be loaded. It manages Model State
 * appropriatly. 
 * @author Kaleb
 */
public class InputModelChangeEvent implements PropertyChangeListener
{

    private ArrayList<InputModelInterface> models;
    private SimulationViewStateInterface simulationViewInputState;

    /**
     * Initialize a new InputModelChangeEvent.
     * @param models the Input Models that will be kept track of.
     * @param modelResultState the Simulation View's Input State manager.
     */
    public InputModelChangeEvent(ArrayList<InputModelInterface> models,
            SimulationViewStateInterface modelResultState)
    {
        this.models = models;
        this.simulationViewInputState = modelResultState;
    }

    /**
     * Verifies the Input Model State. Called whenever the Input Model State
     * is changed.
     * @param evt the Property Event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        // Check if all of the Input Models have been loaded.
        if (evt.getPropertyName().equals("propertiesChanged"))
        {
            boolean modelsLoaded = true;

            Iterator iterate = models.iterator();

            while (iterate.hasNext())
            {
                // If an Input Model isn't ready, indicate.
                if (!((InputModelInterface) iterate.next()).isModelInputReady())
                {
                    modelsLoaded = false;
                }
            }

            // If the Input Model is ready, update the View State. Otherwise,
            // do nothing.
            if (modelsLoaded)
            {
                if (!Globals.TEST_MODE)
                {
                    simulationViewInputState.onSimulationLoaded();
                }
            }
        }
    }
}
