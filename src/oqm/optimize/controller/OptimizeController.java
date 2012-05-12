/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.optimize.controller;

import oqm.algorithm.model.worker.OptimizeSystemWorker;
import oqm.globals.Globals;
import oqm.optimize.view.OptimizeSystemPanel;

/**
 *
 * @author Kaleb
 */
public class OptimizeController
{
    private OptimizeSystemPanel optimizeView;

    public OptimizeController(OptimizeSystemPanel optimizeView)
    {
         this.optimizeView = optimizeView;
    }

    public OptimizeSystemPanel getOptimizeView()
    {
        return optimizeView;
    }
  
    public void optimizeSystem(double[][] steadyStateMatrix, double[] serviceTimes, double alpha)
    {
        // Get an object to perform the calculations for the System Optomizations.
        OptimizeSystemWorker sysCalc = new OptimizeSystemWorker(optimizeView);
        sysCalc.sysCalc(steadyStateMatrix, serviceTimes, alpha);
    }

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

    public void setAlpha(double alpha)
    {
        Globals.ALPHA = alpha;
    }

    public void setServiceTimes(double[] serviceTimes)
    {
        this.optimizeView.setServiceTimes(serviceTimes);
    }
}
