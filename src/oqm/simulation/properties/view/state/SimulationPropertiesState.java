/*
SimulationPropertiesState -- A class within the OQM (Open Queuing Model).
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
package oqm.simulation.properties.view.state;

import javax.swing.SpinnerNumberModel;
import oqm.globals.Globals;
import oqm.simulation.properties.view.SimulationPropertiesFrame;

/**
 * A implentation of the Simulations State Pattern. This class is responsible
 * for managing the State of the Simulation's Properties. Essentially, it helps
 * make the GUI more intuitive for the user and helps other classes in the
 * application decide what to do.
 * @author Kaleb
 */
public class SimulationPropertiesState implements SimulationPropertiesStateInterface
{

    private boolean convergenceSimulation;
    private SimulationPropertiesFrame propertiesFrameView;
    private SpinnerNumberModel iterationSpinnerModel;
    private SpinnerNumberModel alphaSpinnerModel;
    private SpinnerNumberModel convergenceErrorSpinnerModel;
    private SpinnerNumberModel feedbackSpinnerModel;

    /**
     * Initialize the SimulationPropertiesState.
     */
    public SimulationPropertiesState()
    {
        super();
    }

    /**
     * Get the Alpha Spinner Model for the Covergence Simulation.
     * @return SpinnerNumberModel representing value of Alpha.
     */
    @Override
    public SpinnerNumberModel getAlphaSpinnerModel()
    {
        return alphaSpinnerModel;
    }

    public SpinnerNumberModel getConvergenceErrorSpinnerModel()
    {
        return convergenceErrorSpinnerModel;
    }

    public SpinnerNumberModel getFeedbackSpinnerModel()
    {
        return feedbackSpinnerModel;
    }

    /**
     * Get the Iteration Spinner Model.
     * @return SpinnerNumberModel representing the number of iterations
     * that the simulation should attempt before the steady state has been found.
     */
    @Override
    public SpinnerNumberModel getIterationSpinnerModel()
    {
        return iterationSpinnerModel;
    }

    /**
     * Check to see if the Convergence Simulation is enabled.
     * @return boolean indicating if the Convergence Simulation is enabled.
     */
    @Override
    public boolean isConvergenceSimulation()
    {
        return convergenceSimulation;
    }

    /**
     * Set the Simulation Properties Frame.
     * @param propertiesView
     */
    public void setView(SimulationPropertiesFrame propertiesView)
    {
        this.propertiesFrameView = propertiesView;

        iterationSpinnerModel = new SpinnerNumberModel(1000.0, 0.0, 100000.0, 1);
        alphaSpinnerModel = new SpinnerNumberModel(0.9, 0.0, 1, 0.01);
        convergenceErrorSpinnerModel = new SpinnerNumberModel(Globals.EPSILON, 0.000, 1.000, 0.0001);
        feedbackSpinnerModel = new SpinnerNumberModel(0.01, 0.0, 1, 0.001);

        propertiesFrameView.getAlphaSpinner().setModel(alphaSpinnerModel);
        propertiesFrameView.getIterationSpinner().setModel(iterationSpinnerModel);
        propertiesFrameView.getConvergenceErrorSpinner().setModel(convergenceErrorSpinnerModel);
        propertiesFrameView.getFeedbackSpinner().setModel(feedbackSpinnerModel);

        this.onConvergenceSimulation();
    }

    /**
     * Indicate that the Convergence Simulation should be enabled.
     */
    @Override
    public void onConvergenceSimulation()
    {
        propertiesFrameView.getAlphaLabel().setEnabled(true);
        propertiesFrameView.getAlphaSpinner().setEnabled(true);

        propertiesFrameView.getIterationSpinner().setEnabled(true);
        propertiesFrameView.getIterationLabel().setEnabled(true);

        propertiesFrameView.getConvergenceErrorSpinner().setEnabled(true);
        propertiesFrameView.getConvergenceErrorLabel().setEnabled(true);

        convergenceSimulation = true;
    }
}
