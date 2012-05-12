/*
SystemInputModel -- A class within the OQM (Open Queuing Model).
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
package oqm.input.model;

import java.util.ArrayList;
import oqm.input.model.observer.SystemInputModelObserver;
import simulyn.input.model.InputModelInterface;

/**
 * System Input Model keeps track of the current System Input for the simulation.
 * All updates to the System Input Model should be made through SystemInputModel using
 * setMatrix(double[][] matrix). This will notify all Observers of the change
 * and provide them with the new model state.
 * @author Kaleb
 */
public class SystemInputModel extends InputModelInterface
{

    /**
     * Initialize the System Input Model.
     */
    public SystemInputModel()
    {
        modelInputObservers = new ArrayList<SystemInputModelObserver>();
    }

    /**
     * Register a System Input Model Observer. System Input Model
     * Observers want to be notified when a new transition probabilities matrix
     * is ready to be used for a simulation.
     * @param o the SystemInputModelObserver.
     */
    public void registerObserver(SystemInputModelObserver o)
    {
        modelInputObservers.add(o);
    }

    /**
     * Remove a System Input Model Observer. System Input Model
     * Observers want to be notified when a new transition probabilities matrix
     * is ready to be used for a simulation.
     * @param o the SystemInputModelObserver.
     */
    public void removeObserver(SystemInputModelObserver o)
    {
        int i = modelInputObservers.indexOf(o);
        if (i >= 0)
        {
            modelInputObservers.remove(i);
        }
    }

    /**
     * Notify all System Model Observers that a new transition probabilities
     * matrix is ready to be used for a simulation.
     */
    @Override
    public void notifyObservers()
    {
        for (int i = 0; i < modelInputObservers.size(); i++)
        {
            SystemInputModelObserver matrixObserver = (SystemInputModelObserver) modelInputObservers.get(i);
            matrixObserver.updateSystemInputModel(matrix);
        }
    }
}
