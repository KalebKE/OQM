/*
NetworkMediatorModelState -- A class within the OQM(Open Queuing Model).
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
package oqm.network.mediator.state;

import simulyn.output.mediators.state.MediatorStateInterface;
import simulyn.output.view.mediator.OutputViewMediatorInterface;

/**
 * NetworkMediatorModelState manages all of the Model State required to render the
 * graphical representation of the simulations network. 
 * @author Kaleb
 */
public class NetworkMediatorModelState implements MediatorStateInterface
{
    private boolean animated;
    private boolean systemInputModelUpdated;
    private double[][] systemInputModelMatrix;
    private OutputViewMediatorInterface mediator;

    /**
     * Initialize a new instance of NetworkMediatorModelState.
     * @param mediator the Network Mediator that Network State will be managed
     * for. 
     */
    public NetworkMediatorModelState(OutputViewMediatorInterface mediator)
    {
        this.mediator = mediator;
        systemInputModelUpdated = false;
        systemInputModelMatrix = new double[0][0];
    }

    /**
     * Get the double[][] representing the W0 Input Model.
     * @return the W0 Input Model.
     */
    public double[][] getSystemInputModelMatrix()
    {
        return systemInputModelMatrix;
    }

    /**
     * Check if the graphical network rendering should be animated while
     * the simulation runs.
     * @return boolean indicating if the network should be animated.
     */
    public boolean isAnimated()
    {
        return animated;
    }

    /**
     * Indicate if the graphical network representation should be animated
     * during the simulation.
     * @param animated boolean indicating if the network should be animated.
     */
    public void setAnimated(boolean animated)
    {
        this.animated = animated;
    }

    /**
     * Set the System Input Model.
     * @param systemInputModel the System Input Model.
     */
    public void setSystemInputModel(double[][] systemInputModel)
    {
        this.systemInputModelMatrix = systemInputModel;
        systemInputModelUpdated = true;
        stateChanged();
    }


    /**
     * Indicate that the State has been changed and should be verified before
     * being pushed to the Network View via the Network Mediator.
     */
    @Override
    public void stateChanged()
    {
        if (this.systemInputModelUpdated)
        {
            mediator.updateUI();
            resetState();
        }
    }

    /**
     * Reset the State.
     */
    @Override
    public void resetState()
    {
        this.systemInputModelUpdated = false;
    }
}
