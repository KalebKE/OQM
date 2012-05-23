/*
FeedbackController -- a class within the QOM(Open Queuing Model).
Copyright (C) 2012, Kaleb Kircher

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
package oqm.feedback.controller;

import java.util.ArrayList;
import javax.swing.JFrame;
import oqm.algorithm.model.math.ProportionalityFunction;
import oqm.globals.Globals;
import oqm.output.model.IterationOutputModel;
import oqm.output.model.observer.IterationOutputModelObserver;
import org.math.plot.Plot2DPanel;
import simulyn.algorithm.model.AlgorithmModelInterface;
import simulyn.input.model.InputModelInterface;

/**
 * Feedback Controller is responsible for adding feedback to the System. Feedback
 * can be thought of as a node going to itself a small percentage of the time.
 * This involves manipulating the identity matrix and then subtracting the
 * feedback proportionatly from the rest of the nodes in the row.
 * Feedback Controller can also brute force a range of values for the feedback
 * to determine optimal feed back configurations for individual Systems.
 * @author Kaleb
 */
public class FeedbackController implements IterationOutputModelObserver
{
    private AlgorithmModelInterface convergenceSimulation;
    private ArrayList iterationCount;
    private InputModelInterface inputModel;
    private IterationOutputModel outputModel;

    /**
     * Initialize a new Feedback Controller.
     * @param inputModel The System Input Model that manages the transition
     * probability matrix that will have its identity matrix changed and proprotionalized
     * to add feedback to the System.
     * @param outputModel The Iteration Output Model is used for the brute force
     * algorithm that determines the optimal feedback configuration. It knows
     * how many iterations where required to find the steady state of the
     * System.
     * @param convergenceSimulation The Convergence Simulation that is used
     * to run each simulation for the brute force algorithm use to determine
     * the optimal feedback configuration.
     */
    public FeedbackController(InputModelInterface inputModel, IterationOutputModel outputModel,
            AlgorithmModelInterface convergenceSimulation)
    {
        this.inputModel = inputModel;
        this.outputModel = outputModel;
        this.convergenceSimulation = convergenceSimulation;

        this.outputModel.registerObserver(this);

        iterationCount = new ArrayList();
    }

    public ArrayList getIterationCount()
    {
        return iterationCount;
    }

    /**
     * Add feedback to the identity matrix and proportionalize the System.
     */
    public void generateFeedback()
    {
        inputModel.setModelInput(ProportionalityFunction.proportionalizeMatrix(inputModel.getModelInput(), Globals.FEEDBACK));
    }

    /**
     * Proportionalizes a feedback alpha through the transition matrix. Will make
     * a periodic matrix a-periodic. Then it runs a series of calculations while
     * incrementing the value for alpha and comparing it to the number of iterations
     * it took for the transition matrix to converge to the steady state matrix.
     * Finally, it plots these values.
     */
    public void proportionalityAndIterations()
    {
        // Save a local copy of the current Transition Matrix
        double[][] matrix = this.inputModel.getModelInput();

        // Create an array of alphas that is 1/definedAlpha long
        double[] alphas = new double[(int) (1 / Globals.FEEDBACK)];

        for (int i = 1; i < alphas.length - 1; i++)
        {
            // Increment the value of alpha by alpha.
            // If defined Alpha = 0.01, then increment by 0.01
            alphas[i] = alphas[i - 1] + (Globals.FEEDBACK);
        }
        // Set the last index to the second to last index since the last index = 0
        alphas[alphas.length - 1] = alphas[alphas.length - 2];

        for (int i = 0; i < alphas.length; i++)
        {
            // Proportionalize the periodic transition matrix so it is now aperiodic
            inputModel.setModelInput(ProportionalityFunction.proportionalizeMatrix(matrix, alphas[i]));
            this.convergenceSimulation.execute();
        }

        double[] iterations = new double[alphas.length];
        
        for(int i =1; i < alphas.length; i++)
        {
            int iX = (Integer) this.iterationCount.get(i-1);
            iterations[i] = (double)iX;
        }

        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel plot = new Plot2DPanel();

        // define the legend position
        plot.addLegend("SOUTH");

        plot.setAxisLabel(0, "Feedback Alpha");
        plot.setAxisLabel(1, "Iterations required for Convergence");

        // add a line plot to the PlotPanel
        plot.addLinePlot("my plot", alphas, iterations);

        // put the PlotPanel in a JFrame like a JPanel
        JFrame frame = new JFrame("Alpha versus Iterations");
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);

        iterationCount = new ArrayList();
    }

    /**
     * The Observer hook for the Iteration Output Model. It knows how
     * many iterations were required for the steady state to be found.
     * @param iterations the number of iterations required to find the steady state.
     */
    public void updateIterationOutputModelOutput(int iterations)
    {
        this.iterationCount.add(iterations);
    }
}
