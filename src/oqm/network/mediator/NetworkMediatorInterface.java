/*
NetworkMediatorInterface -- an interface within the OQM(Open Queuing Model).
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
package oqm.network.mediator;

import javax.swing.JPanel;

/**
 * NetworkMediatorInterface is an interface that provides a coupling between
 * OQM's Output Models and Output View's.
 * It manages a Network UI View that is designed to allow the user
 * to view a simulation network rendering once the Input Model has been loaded into the simluation.
 * It provides the logic for the View's Actions and renders the Model Results with a
 * Network Rendering UI backed by the JUNG library within the View.
 * @author Kaleb
 */
public interface NetworkMediatorInterface
{

    /**
     * Animate the network.
     * @param animate boolean indicating if the network should be animated.
     */
    public void animateNetwork(boolean animate);

    /**
     * Get the network View.
     * @return the network View.
     */
    public JPanel getView();

    /**
     * Check if the network is animated.
     * @return boolean indicating if the network is animated.
     */
    public boolean isAnimateNetwork();

    /**
     * Reset the network.
     */
    public void resetNetwork();

    /**
     * Set the network.
     * @param system the System Input Model.
     */
    public void setNetwork(double[][] system);
}
