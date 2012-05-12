/*
OptimizeController -- a class within the OQM(Open Queuing Model).
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
package oqm.optimize.controller;

import oqm.algorithm.model.worker.OptimizeSystemWorker;
import oqm.globals.Globals;
import oqm.optimize.view.OptimizeSystemPanel;

/**
 * The Optimize Controller is responsible for managing the interactions
 * between the Optimize View GUI and the Models that back it. The Optimize
 * Controller is part of an MVC architecture within the OQM that is responsible
 * for performing optimization alogirthms on the steady state from the transition
 * probabilities matrix.
 * @author Kaleb
 */
public class OptimizeController
{

    private OptimizeSystemPanel optimizeView;

    /**
     * Initialize a new Optimize Controller.
     * @param optimizeView the View that the optimization algorithm results
     * will be rendered to.
     */
    public OptimizeController(OptimizeSystemPanel optimizeView)
    {
        this.optimizeView = optimizeView;
    }

    /**
     * Get the Optimize View.
     * @return
     */
    public OptimizeSystemPanel getOptimizeView()
    {
        return optimizeView;
    }

    /**
     * Optimize the steady state system produced from the transition probabilities
     * matrix.
     * @param steadyStateMatrix the steady state system produced from the transition
     * probabilities matrix.
     * @param serviceTimes the service times required for each node to service a visitor
     * @param alpha this can be thought of as temperature, or a measure of entropy,
     * in thermodynamics. It is essentially T in S = Q/T where S is the measure
     * of entropy in the System and Q is the heat energy (1/dMax). T is defined
     * by the user. In general, a value of 0.7 for S is ideal.
     */
    public void optimizeSystem(double[][] steadyStateMatrix, double[] serviceTimes, double alpha)
    {
        // Get an object to perform the calculations for the System Optomizations.
        OptimizeSystemWorker sysCalc = new OptimizeSystemWorker(optimizeView);
        sysCalc.sysCalc(steadyStateMatrix, serviceTimes, alpha);
    }

    /**
     * Reset the system results.
     */
    public void resetSystem()
    {
        optimizeView.resetPanel();
        optimizeView.getSteadyStateVectorTextField().setText("");
        optimizeView.getVisitationVectorTextField().setText("");
        optimizeView.getDemandTimesTextField().setText("");
        optimizeView.getMaxDemandTextField().setText("");
        optimizeView.getArrivalRateTextField().setText("");
        optimizeView.getBetaTextField().setText("");
        optimizeView.getNewServiceTimesTextField().setText("");
        optimizeView.getNewDemandTimesTextField().setText("");
        optimizeView.getNewMaxDemandTextField().setText("");
        optimizeView.getNewServiceRateTextField().setText("");
        optimizeView.getLambdaMaxTextField().setText("");
        optimizeView.getLambdaVectorTextField().setText("");
        optimizeView.getServerLengthTextField().setText("");
        optimizeView.getServerWaitTextField().setText("");
        optimizeView.getQueueWaitTextField().setText("");
        optimizeView.getQueueLengthTextField().setText("");
    }

    /**
     * Set the value for alpha.
     * @param alpha this can be thought of as temperature, or a measure of entropy,
     * in thermodynamics. It is essentially T in S = Q/T where S is the measure
     * of entropy in the System and Q is the heat energy (1/dMax). T is defined
     * by the user. In general, a value of 0.7 for S is ideal.
     */
    public void setAlpha(double alpha)
    {
        Globals.ALPHA = alpha;
    }

    /**
     * Set the Service Times. The Service Time is the time required for each node
     * to service a vistor in the system.
     * @param serviceTimes the Service times for the System.
     */
    public void setServiceTimes(double[] serviceTimes)
    {
        this.optimizeView.setServiceTimes(serviceTimes);
    }
}
