/*
PlotMediatorInterface -- An Interface within the OQM (Open Queuing Model).
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
package oqm.plot.mediator;

import javax.swing.JPanel;
import org.math.plot.Plot2DPanel;

/**
 * Plot Mediator Interface provides the interface for Plot Mediators.
 * It contains the methods to set the desired Plot type, clear the Plot,
 * and provide the Plot View to other classes.
 * @author Kaleb
 */
public interface PlotMediatorInterface
{

    /**
     * Get the Plot Mediator View.
     * @return the JPanel representing the Plot Mediator View.
     */
    public JPanel getView();

    /**
     * Returns an instance of the Plot that is currently being used and
     * will eventually be pushed to the View after the State is added
     * by one of the SwingWorkers.
     * @return a Plot with or without State.
     */
    public Plot2DPanel getPlot();

    /**
     * Remove all plots from the current Plot and update the View.
     */
    public void onClearUI();

    /**
     * Renders a Scatter Plot representing the Points that hit shapes and Points
     * that missed shapes during the simulation
     */
    public void onScatterPlot();

    /**
     * Renders a Line Plot representing the amount of time the simulation
     * took to run versus the number of inputs fed into the simulations
     * network.
     */
    public void onLinePlot();
}
