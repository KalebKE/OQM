/*
NetworkMediatorViewStateInterface -- A class within the OQM(Open Queuing Model).
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

/**
 * NetworkMediatorViewStateInterface manages all of the View State required to manage the
 * graphical representation of the simulations network.
 * @author Kaleb
 */
public interface NetworkMediatorViewStateInterface
{
    /**
     * Check if the network should be animated.
     * @return boolean indicating if the network should be animated.
     */
    public boolean isAnimated();

    /**
     * Logic to manage View State when the network is animated.
     * @param animate boolean indicating if the network should be animated.
     */
    public void setAnimated(boolean animate);

}
