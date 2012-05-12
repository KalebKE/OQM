/*
SystemInputModelObserver -- A class within the OQM(Open Queuing Model).
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
package oqm.input.model.observer;

/**
 * SystemInputModelObserver provides a common interface for all classes that need to
 * be notified of updates to the simulations System Input Model. The System
 * Input Model is responisble for managing the transition probabilities matrix
 * that will be used for the simulations.
 * @author Kaleb
 */
public interface SystemInputModelObserver
{
    /**
     * The hook for classes that need to observe updates to the
     * simulations System Input Model.
     * @param modelInput the transition probabilities matrix for the
     * System Input Model.
     */
    public void updateSystemInputModel(double[][] modelInput);
}
