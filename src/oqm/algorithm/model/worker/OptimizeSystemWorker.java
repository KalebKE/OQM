/*
OptimizeSystemWorker -- a class within the Open Queueing Model (OQM).
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
package oqm.algorithm.model.worker;

import oqm.optimize.view.OptimizeSystemPanel;

/**
 * Contains the algorithms to calculate the Demand Times based on
 * Service Times and a the steady state normalized to the outside world, known
 * as the Visitation Rate. From this, we can attempt to optimize the Demand
 * Times to maximize throughput in the System. We can verify the optimizations
 * with the Server Length (Ls), Queue Length (Lq), Server Wait Time(Ws)
 * and Queue Wait Time (Wq).
 */
public class OptimizeSystemWorker
{
    // The View
    private OptimizeSystemPanel optimizeView;

    /**
     * Initialize a new Optimize System Worker.
     * @param optimizeView the View that will display the results of
     * the calculations.
     */
    public OptimizeSystemWorker(OptimizeSystemPanel optimizeView)
    {
        this.optimizeView = optimizeView;
    }

    /**
     * Performs calculations to optomize a System from its steady state.
     * @param steadyState the steady state of the transition probabilities matrix.
     * @param serviceTimes the required service times for each node in the System
     * @param alpha this can be thought of as temperature, or a measure of entropy,
     * in thermodynamics. It is essentially T in S = Q/T where S is the measure
     * of entropy in the System and Q is the heat energy (1/dMax). T is defined
     * by the user. In general, a value of 0.7 for S is ideal.
     */
    public void sysCalc(double[][] steadyState, double[] serviceTimes, double alpha)
    {
        double[] beta = new double[steadyState.length];
        double[] demand = new double[steadyState.length];
        double[] piVector = new double[steadyState.length];
        double[] visitation = new double[steadyState.length];

        double[] dNew = new double[steadyState.length];
        double[] mu = new double[steadyState.length];
        double[] oldMu = new double[steadyState.length];
        double[] sNew = new double[steadyState.length];
        double[] nodeLambda = new double[steadyState.length];
        double[] oldNodeLambda = new double[steadyState.length];

        double piMin;
        double dMin;
        double dMax;
        double lambdaMax;
        double lambda;

        // parse to pi vector
        for (int i = 0; i < steadyState.length; i++)
        {
            piVector[i] = steadyState[0][i];
        }

        optimizeView.setSteadyStateVectorText(piVector);

        // used to find the piMin
        // piMin is the outside wold
        piMin = piVector[0];

        for (int i = 0; i < piVector.length; i++)
        {
            visitation[i] = piVector[i] / piMin;
        }

        optimizeView.setVisitationVectorText(visitation);

        for (int i = 0; i < visitation.length; i++)
        {
            demand[i] = visitation[i] * serviceTimes[i];

        }

        optimizeView.setDemandTimesText(demand);

        // used to find the dMin
        if (demand[0] != 0)
        {
            dMin = demand[0];
        } else
        {
            dMin = demand[1];
        }

        // find dMin
        for (int i = 0; i < demand.length; i++)
        {
            if (demand[i] < dMin && demand[i] != 0)
            {
                dMin = demand[i];
            }
        }

        double dMaxOld;

        // used to find the dMax
        if (demand[0] != 0)
        {
            dMaxOld = demand[0];
        } else
        {
            dMaxOld = demand[1];
        }

        // find dMax
        for (int i = 0; i < demand.length; i++)
        {
            if (demand[i] > dMaxOld && demand[i] != 0)
            {
                dMaxOld = demand[i];
            }
        }

        for (int i = 0; i < oldMu.length; i++)
        {
            oldMu[i] = 1 / serviceTimes[i];
        }

        optimizeView.setOldServiceRateText(oldMu);

        optimizeView.setMaxDemandText(dMaxOld);

        double lambdaMaxOld = 1 / dMaxOld;
        double lambdaOld = lambdaMaxOld * alpha;

        optimizeView.setArrivalRateText(lambdaMaxOld);

        for (int i = 0; i < oldNodeLambda.length; i++)
        {
            oldNodeLambda[i] = lambdaOld * visitation[i];
        }

        optimizeView.setOldLambdaVectorText(oldNodeLambda);

        for (int i = 0; i < demand.length; i++)
        {
            beta[i] = demand[i] / dMin;
        }

        optimizeView.setBetaText(beta);

        for (int i = 0; i < sNew.length; i++)
        {
            sNew[i] = serviceTimes[i] / beta[i];
        }

        double[] oldLs = new double[oldNodeLambda.length];
        double[] oldWs = new double[oldNodeLambda.length];
        double[] oldWq = new double[oldNodeLambda.length];
        double[] oldLq = new double[oldNodeLambda.length];

        for (int i = 0; i < oldNodeLambda.length; i++)
        {
            oldLs[i] = oldNodeLambda[i] / (oldMu[i] - oldNodeLambda[i]);
        }

        optimizeView.setOldServerLengthText(oldLs);

        for (int i = 0; i < oldNodeLambda.length; i++)
        {
            oldWs[i] = oldLs[i] / oldNodeLambda[i];
        }

        optimizeView.setOldServerWaitText(oldWs);

        for (int i = 0; i < oldNodeLambda.length; i++)
        {
            oldWq[i] = oldWs[i] - (1 / oldMu[i]);
        }
        optimizeView.setOldQueueWaitText(oldWq);

        for (int i = 0; i < oldNodeLambda.length; i++)
        {
            oldLq[i] = oldNodeLambda[i] * oldWq[i];
        }

        optimizeView.setOldQueueLengthText(oldLq);

        optimizeView.setNewServiceTimesText(sNew);

        for (int i = 0; i < dNew.length; i++)
        {
            dNew[i] = sNew[i] * visitation[i];

        }

        optimizeView.setNewDemandTimesText(dNew);

        for (int i = 0; i < mu.length; i++)
        {
            mu[i] = 1 / sNew[i];
        }

        optimizeView.setNewServiceRateText(mu);

        // used to find the lambda
        dMax = dNew[1];

        // find dMax
        for (int i = 0; i < dNew.length; i++)
        {
            if (dNew[i] > dMax)
            {
                dMax = dNew[i];
            }
        }

        optimizeView.setNewMaxDemandText(dMax);

        lambdaMax = 1 / dMax;
        lambda = lambdaMax * alpha;

        for (int i = 0; i < nodeLambda.length; i++)
        {
            nodeLambda[i] = lambda * visitation[i];
        }

        optimizeView.setLambdaVectorText(nodeLambda);
        optimizeView.setLambdaMaxText(lambdaMax);

        double[] ls = new double[nodeLambda.length];
        double[] ws = new double[nodeLambda.length];
        double[] wq = new double[nodeLambda.length];
        double[] lq = new double[nodeLambda.length];

        for (int i = 0; i < nodeLambda.length; i++)
        {
            ls[i] = nodeLambda[i] / (mu[i] - nodeLambda[i]);
        }

        optimizeView.setServerLengthText(ls);

        for (int i = 0; i < nodeLambda.length; i++)
        {
            ws[i] = ls[i] / nodeLambda[i];
        }

        optimizeView.setServerWaitText(ws);

        for (int i = 0; i < nodeLambda.length; i++)
        {
            wq[i] = ws[i] - (1 / mu[i]);
        }
        optimizeView.setQueueWaitText(wq);

        for (int i = 0; i < nodeLambda.length; i++)
        {
            lq[i] = nodeLambda[i] * wq[i];
        }

        optimizeView.setQueueLengthText(lq);

        int count = 1;
        for (int i = 0; i < nodeLambda.length; i++)
        {
            count++;
        }
    }
}
