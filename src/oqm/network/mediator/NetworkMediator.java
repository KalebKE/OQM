/*
NetworkMediator -- a class within the OQM(Open Queuing Model).
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
import oqm.globals.Globals;
import oqm.input.model.SystemInputModel;
import oqm.input.model.observer.SystemInputModelObserver;
import oqm.network.mediator.state.NetworkMediatorModelState;
import oqm.network.mediator.state.NetworkMediatorViewStateInterface;
import oqm.network.view.OQMNetworkRenderer;
import oqm.output.model.ConvergenceOutputModel;
import oqm.output.model.SteadyStateOutputModel;
import oqm.output.model.observer.ConvergenceOutputModelObserver;
import oqm.output.model.observer.SteadyStateOutputModelObserver;
import simulyn.input.model.InputModelInterface;
import simulyn.output.mediators.state.MediatorStateInterface;
import simulyn.output.model.OutputModelInterface;
import simulyn.output.view.mediator.OutputViewMediatorInterface;

/**
 * ModelViewMediatorInterface implementations are used to completely decouple
 * the Model from the View using a Model-View-Mediator (MVM) architecture. This
 * pattern is also known as a Model-View-Presenter using a Passive View strategy.
 * It uses a single method to push new State to the View, updateUI(). 
 *
 * Mediators differ from Controllers in how they couple the View to the Model.
 * With a Controller, the View requests Actions to be taken upon the Model via
 * the Controller and the Model will then update the View with the new State.
 * With a Mediator, the View requests Actions to be taken upon the Model via the
 * Mediator and the Model will update the Mediator with the new State. The Mediator
 * will then provide the View with the new State.
 *
 * An MVM is used to manage the Views for the Network UI, Plot UI, and Result UI.
 * This is because we want the UI's to be completely decoupled from any form
 * of a model or controller, mediator, presenter, etc... We want the UI's to be as
 * decoupled and as portable as possible so they can be reused in many ways.
 * But we don't know how the UI's might be used in the future. This is why
 * the MVM is used.
 *
 * NetworkMediator is a implementation that provides a coupling between
 * OQM's Output Models and Output View's.
 * It manages a Network UI View that is designed to allow the user
 * to view a simulation network rendering once the Input Model has been loaded into the simluation.
 * It provides the logic for the View's Actions and renders the Model Results with a
 * Network Rendering UI backed by the JUNG library within the View.
 * @author Kaleb
 */
public class NetworkMediator implements
        NetworkMediatorInterface, OutputViewMediatorInterface,
        SystemInputModelObserver, ConvergenceOutputModelObserver,
        SteadyStateOutputModelObserver
{

    private double[][] convergenceMatrix;
    private OQMNetworkRenderer networkView;
    private MediatorStateInterface networkModelState;
    private NetworkMediatorViewStateInterface networkViewState;
    // Model Result Models
    private InputModelInterface systemInputModel;
    private OutputModelInterface covergenceOutputModel;
    private SteadyStateOutputModel steadyStateOutputModel;

    /**
     * Initialize the Network Mediator.
     * @param andLayerOutputModel
     * @param orLayerOutputModel
     * @param outputLayerModelResult
     */
    public NetworkMediator(InputModelInterface systemInputModel,
            OutputModelInterface covergenceOutputModel, SteadyStateOutputModel steadyStateOutputModel)
    {
        // local instance of the result model
        this.systemInputModel = systemInputModel;
        this.covergenceOutputModel = covergenceOutputModel;
        this.steadyStateOutputModel = steadyStateOutputModel;
        // this class should observe changes to the result model
        ((SystemInputModel) this.systemInputModel).registerObserver(this);
        ((ConvergenceOutputModel) this.covergenceOutputModel).registerObserver(this);
        ((SteadyStateOutputModel) this.steadyStateOutputModel).registerObserver(this);

        networkView = new OQMNetworkRenderer();
        networkModelState = new NetworkMediatorModelState(this);
    }

    /**
     * Animate the network.
     * @param animate boolean indicating if the network should be animated.
     */
    @Override
    public void animateNetwork(boolean animate)
    {
        ((NetworkMediatorModelState) networkModelState).setAnimated(animate);
        networkViewState.setAnimated(animate);
    }

    /**
     * Get the network's Model State.
     * @return the State of the network's Model.
     */
    public NetworkMediatorModelState getNetworkModelState()
    {
        return (NetworkMediatorModelState) networkModelState;
    }

    /**
     * Get the network View.
     * @return the network View.
     */
    @Override
    public JPanel getView()
    {
        return networkView;
    }

    public boolean isAnimateNetwork()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Reset the network.
     */
    @Override
    public void resetNetwork()
    {
        updateUI();
        Globals.ITERATE = 1;
    }

    /**
     * Set the network.
     * @param system the System Input Model.
     */
    @Override
    public void setNetwork(double[][] systemInputModel)
    {
        // Save the result of the AND Layer Node's State to a local State Pattern
        // that will decide when all of the State that the View requires has been
        // initialized and set. At that point, the State Pattern will call
        // updateUI() and push the entire Network's State to the View. The
        // State Pattern will then reset for the next Network State.
        ((NetworkMediatorModelState) networkModelState).setSystemInputModel(systemInputModel);
    }

    /**
     * Set the network's View State.
     * @param networkViewState the State of the network View.
     */
    public void setNetworkViewState(NetworkMediatorViewStateInterface networkViewState)
    {
        this.networkViewState = networkViewState;
    }

    /**
     * Called when the Mediator has new View State ready to be pushed to the View.
     */
    @Override
    public void updateUI()
    {
        networkView.setNetwork(((NetworkMediatorModelState) networkModelState).getSystemInputModelMatrix());
    }

    public void updateSystemInputModel(double[][] modelInput)
    {
        this.setNetwork(modelInput);
    }

    public void updateConvergenceOutputModelOutput(double[][] modelResult)
    {
        this.convergenceMatrix = modelResult;
    }

    public void updateSteadyStateOutputModelOutput(boolean converged)
    {
        if (converged)
        {
            double[] nodes = new double[this.convergenceMatrix.length];

            for (int i = 0; i < nodes.length; i++)
            {
                nodes[i] = this.convergenceMatrix[0][i];
            }

            this.networkView.fireNodes(nodes);
        }
    }
}
