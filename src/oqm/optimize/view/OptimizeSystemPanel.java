/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptimizeView.java
 *
 * Created on Apr 26, 2012, 2:38:00 PM
 */
package oqm.optimize.view;

import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import oqm.globals.Globals;
import oqm.optimize.controller.OptimizeController;
import oqm.output.model.ConvergenceOutputModel;
import oqm.output.model.SteadyStateOutputModel;
import oqm.output.model.observer.ConvergenceOutputModelObserver;
import oqm.output.model.observer.SteadyStateOutputModelObserver;
import simulyn.output.model.OutputModelInterface;
import simulyn.ui.components.BlinkerButton;

/**
 *
 * @author Kaleb
 */
public class OptimizeSystemPanel extends JPanel implements ConvergenceOutputModelObserver,
        SteadyStateOutputModelObserver
{

    private JPanel controlBar;
    private DecimalFormat shortDecFormat;
    private DecimalFormat longDecFormat;
    private DecimalFormat epicDecFormat;
    private double[][] covergenceMatrix;
    private double[] serviceTimes;
    private String shortDecimalFormat;
    private String longDecimalFormat;
    private String epicDecimalFormat;
    private OptimizeController controller;
    private OutputModelInterface convergenceOutputModel;
    private SteadyStateOutputModel steadyStateOutputModel;

    /** Creates new form OptimizeView */
    public OptimizeSystemPanel(JPanel controlBar,
            OutputModelInterface convergenceOutputModel,
            SteadyStateOutputModel steadyStateOutputModel)
    {
        initComponents();

        this.controlBar = controlBar;
        this.convergenceOutputModel = convergenceOutputModel;
        this.steadyStateOutputModel = steadyStateOutputModel;
        this.controlPanel.setLayout(new BoxLayout(this.controlPanel, BoxLayout.LINE_AXIS));
        this.controlPanel.add(this.controlBar);

        ((ConvergenceOutputModel) this.convergenceOutputModel).registerObserver(this);
        ((SteadyStateOutputModel) this.steadyStateOutputModel).registerObserver(this);

        // Set the desired decimal format here.
        // *This can be overridden by the User Preferances panel!*
        this.shortDecimalFormat = ("0.000");

        // Set the desired decimal format here.
        // *This can be overridden by the User Preferances panel!*
        this.longDecimalFormat = ("0.0000");

        // Set the desired decimal format here.
        // *This can be overridden by the User Preferances panel!*
        this.epicDecimalFormat = ("0.0000000000");

        // Creates a new DecimalFormatter for the text fields so we can
        // can control how many decimal places get printed.
        this.shortDecFormat = new DecimalFormat(shortDecimalFormat);
        this.longDecFormat = new DecimalFormat(longDecimalFormat);
        this.epicDecFormat = new DecimalFormat(epicDecimalFormat);

        initToolTips();
    }

    private void initToolTips()
    {
        this.steadyStateVectorTextField.setToolTipText("<html>The Steady State Vector "
                + "for the System. <br> It represents the percentage of traffic "
                + "each node <br> in the System will be visited by for a very large <br> "
                + "number of visitors.</html>");

        this.visitationVectorTextField.setToolTipText("<html>The Visitation Vector is the "
                + "Steady State Vector <br>normailzed to the outsize world. To normalize "
                + "the <br>Steady State Vector, the vector should be divided by Pi-0 (the outside world). <br> "
                + "For example," + "if the Steady State Vector is [Pi-0, Pi-1, Pi-2], <br> then each Pi should be "
                + "divided by Pi-0: Visitation Vector = [Pi-0/Pi-0, Pi-1/Pi-0, Pi-2/Pi-0].<br> The Visitation "
                + "Vector represents how many visits <br> arrive at each node for every visitor to the system. The outside "
                + "world (Pi-0) is always equal to 1 in the Visitation <br> Vector because everyone enters the System"
                + "from the outside world.</html>");

        this.definedServiceTimesTextField.setToolTipText("<html>The Service Times Vector represents how long, on average,"
                + " it <br> takes each node to service each visitor. The Visitation Vector means nothing without"
                + " the <br> Service Times Vector. The Visitation Vector tells you what percentage of the traffic"
                + " is <br> going to each node. The Service Times indicate how long, on average, each visitor"
                + " is <br> spending at each node.</html>");

        this.demandTimesTextField.setToolTipText("<html>The Demand Time Vector represents how long each node "
                + "is servicing each visitor <br>relative to each new visitor entering the System. "
                + "It is a measure of time. <br>It is calculated by multiplying the Service Vector by the "
                + "Visitation Vector.<br> For instance, if a node has a visitation rate of 0.5, it is only getting a "
                + "single visitor, on<br> average, for every two visitors that enter the System. If it takes 1 second "
                + "to service each customer<br> that comes to the node, the Demand is 1 * 0.5 = 0.5. That means that "
                + "it is taking 0.5 of a <br>second, on average, for that node to service each customer that is coming "
                + "into the System. This is<br> an interesting relationship, take note that the original Service Time "
                + "for the node was 1 <br>second, but since the node isn't getting that many visitors relative to the "
                + "visitors entering the System, <br>the node can service vistors faster than the Service Time.</html>");

        this.arrivalRateTextField.setToolTipText("<html>The Arrival Rate λ, also known as Lambda, is the "
                + "number of visitors <br>that are being put through the System for every unit of time. It is both "
                + "the number of visitors <br>entering and leaving the System because of Burke's Theorum. "
                + "The homogeneous Poisson<br> process is one of the most well-known Lévy processes. "
                + "This process is characterized<br> by a rate parameter λ, also known as intensity, such that the "
                + "number of events in time <br>interval (t, t + τ] follows a Poisson distribution with associated parameter λτ. "
                + "Just as a Poisson random<br> variable is characterized by its scalar parameter λ, a homogeneous Poisson process is "
                + "characterized by its rate <br>parameter λ, which is the expected number of 'events' or 'arrivals' that occur per unit time.</html>");

        this.betaTextField.setToolTipText("<html>The Beta Vector is a percentage scaler to increase the speed of the "
                + "nodes to catch up to the speed <br>of the rest of the System. This is found by dividing "
                + "the Demand Vector by the <br>minimum Demand. In theory, the node with the lowest Demand "
                + "is the speed at which the <br>System should be running. In reality, you can't just ask "
                + "people to work faster or<br> will machines to process more data, so Beta is fairly limited.</html>");

        this.newServiceTimesTextField.setToolTipText("<html>Once the Beta Vector scaler has been determined, in order "
                + "to speed the nodes in<br> the System up to their theoretical optimum, it needs to be divided into "
                + "the Old Service Times. <br>This will determine what the New Service Times need to be. The idea is to speed "
                + "up all of the nodes in <br>the System so they can ouput visitors at an optimum rate. If the node "
                + "has an especially high <br>rate of visitors, this can mean increaseing the speed by a large factor. In reality, "
                + "you are extremely limited <br>in terms of how much you can acutally speed up the Service Times "
                + "for the nodes of the System.</html>");

        this.newDemandTimesTextField.setToolTipText("<html>The New Demand Times are the New Service Times"
                + "multiplied by the <br>Visitation Vector.  The New Demand Times will all be balanced and "
                + "be equal to the<br> minimum Old Demand Time.</html> ");

        this.newServiceRateTextField.setToolTipText("<html>The rate at which each node is serviving "
                + "visitors for every <br>visitor that enters the System. This is calculated by dividing "
                + "the New Service<br> Rate into 1.</html>");

        this.lambdaMaxTextField.setToolTipText("<html>Lambda Max is how many visitors are being "
                + "serviced for<br> each visitor that enters the System. It is calculated by dividing "
                + "the maximum <br> New Demand into 1.</html>");

        this.lambdaVectorTextField.setToolTipText("<html>Lambda Vector is how many visitors are being "
                + "serviced at each <br>node in the System (except the outside world) "
                + " for each visitor <br>that enters the System. This is calculated by "
                + "multiplying the Visitation Vector by Lamda Max.</html>");

    }

    /**
     * Get the Arriaval Rate Text Field.
     * @return JTextField for Arrival Rate.
     */
    public JTextField getArrivalRateTextField()
    {
        return arrivalRateTextField;
    }

    /**
     * Get the Beta Text Field.
     * @return JTextField for Beta value.
     */
    public JTextField getBetaTextField()
    {
        return betaTextField;
    }

    /**
     * Get Defined Service Times Text Field.
     * @return  JTextField for Defined Service Times.
     */
    public JTextField getDefinedServiceTimesTextField()
    {
        return definedServiceTimesTextField;
    }

    /**
     * Get Demand Times Text Field.
     * @return JTextField for Demand Times.
     */
    public JTextField getDemandTimesTextField()
    {
        return demandTimesTextField;
    }

    /**
     * Get Lambda Max Text Field.
     * @return JTextField for Lambda Max.
     */
    public JTextField getLambdaMaxTextField()
    {
        return lambdaMaxTextField;
    }

    /**
     * Get Lambda Vector Text Field.
     * @return JTextField for Lambda Vector.
     */
    public JTextField getLambdaVectorTextField()
    {
        return lambdaVectorTextField;
    }

    /**
     * Get Max Demand Text Field.
     * @return JTextField for Max Demand.
     */
    public JTextField getMaxDemandTextField()
    {
        return maxDemandTextField;
    }

    /**
     * Get New Demand Times Text Field.
     * @return
     */
    public JTextField getNewDemandTimesTextField()
    {
        return newDemandTimesTextField;
    }

    /**
     * Get New Max Demand Text Field.
     * @return
     */
    public JTextField getNewMaxDemandTextField()
    {
        return newMaxDemandTextField;
    }

    /**
     * Get New Service Rate Text Field.
     * @return
     */
    public JTextField getNewServiceRateTextField()
    {
        return newServiceRateTextField;
    }

    /**
     * Get New Service Times Text Field.
     * @return
     */
    public JTextField getNewServiceTimesTextField()
    {
        return newServiceTimesTextField;
    }

    public JLabel getOldLambdaVectorLabel()
    {
        return oldLambdaVectorLabel;
    }

    public JTextField getOldLambdaVectorTextField()
    {
        return oldLambdaVectorTextField;
    }

    public JLabel getOldQueueLengthLabel()
    {
        return oldQueueLengthLabel;
    }

    public JTextField getOldQueueLengthTextField()
    {
        return oldQueueLengthTextField;
    }

    public JLabel getOldQueueWaitLabel()
    {
        return oldQueueWaitLabel;
    }

    public JTextField getOldQueueWaitTextField()
    {
        return oldQueueWaitTextField;
    }

    public JLabel getOldServerLengthLabel()
    {
        return oldServerLengthLabel;
    }

    public JTextField getOldServerLengthTextField()
    {
        return oldServerLengthTextField;
    }

    public JLabel getOldServerWaitLabel()
    {
        return oldServerWaitLabel;
    }

    public JTextField getOldServerWaitTextField()
    {
        return oldServerWaitTextField;
    }

    public JLabel getOldServiceRateLabel()
    {
        return oldServiceRateLabel;
    }

    public JTextField getOldServiceRateTextField()
    {
        return oldServiceRateTextField;
    }

    /**
     * Get Server Length Text Field.
     * @return
     */
    public JTextField getServerLengthTextField()
    {
        return serverLengthTextField;
    }

    /**
     * Get Server Wait Time Text Field.
     * @return
     */
    public JTextField getServerWaitTextField()
    {
        return serverWaitTextField;
    }

    /**
     * Get Queue Length Text Field.
     * @return
     */
    public JTextField getQueueLengthTextField()
    {
        return queueLengthTextField;
    }

    /**
     * Get Queue Wait Text Field.
     * @return
     */
    public JTextField getQueueWaitTextField()
    {
        return queueWaitTextField;
    }

    /**
     * Get Visitation Vector Text Field.
     * @return
     */
    public JTextField getVisitationVectorTextField()
    {
        return visitationVectorTextField;
    }

    /**
     * Get Steady State Vector Text Field.
     * @return
     */
    public JTextField getSteadyStateVectorTextField()
    {
        return steadyStateVectorTextField;
    }

    public void resetPanel()
    {
        this.arrivalRateLabel.setEnabled(false);
        this.arrivalRateTextField.setEnabled(false);
        this.betaLabel.setEnabled(false);
        this.betaTextField.setEnabled(false);
        this.definedServiceTimesLabel.setEnabled(false);
        this.definedServiceTimesTextField.setEnabled(false);
        this.demandLabel.setEnabled(false);
        this.demandTimesTextField.setEnabled(false);
        this.lambdaMaxTextField.setEnabled(false);
        this.newLambdaMaxLabel.setEnabled(false);
        this.lambdaVectorTextField.setEnabled(false);
        this.newLambdaVectorLabel.setEnabled(false);
        this.maxDemandLabel.setEnabled(false);
        this.maxDemandTextField.setEnabled(false);
        this.newDemandTimesLabel.setEnabled(false);
        this.newDemandTimesTextField.setEnabled(false);
        this.newMaxDemandLabel.setEnabled(false);
        this.newMaxDemandTextField.setEnabled(false);
        this.newServiceRateLabel.setEnabled(false);
        this.newServiceRateTextField.setEnabled(false);
        this.newServiceTimesLabel.setEnabled(false);
        this.newServiceTimesTextField.setEnabled(false);
        this.queueLengthLabel.setEnabled(false);
        this.queueLengthTextField.setEnabled(false);
        this.queueWaitLabel.setEnabled(false);
        this.queueWaitTextField.setEnabled(false);
        this.calculateSystemButton.setEnabled(false);
        this.serverLengthTextField.setEnabled(false);
        this.newServerLengthLabel.setEnabled(false);
        this.serverWaitTextField.setEnabled(false);
        this.newServerWaitLabel.setEnabled(false);
        this.steadyStateVectorLabel.setEnabled(false);
        this.steadyStateVectorTextField.setEnabled(false);
        this.visitationVectorLabel.setEnabled(false);
        this.visitationVectorTextField.setEnabled(false);
    }

    /**
     * Set the arrivalRate Text.
     * @param arrivalRateText
     */
    public void setArrivalRateText(double arrivalRateText)
    {
        this.arrivalRateTextField.setText(this.epicDecFormat.format(arrivalRateText));
        this.arrivalRateLabel.setEnabled(true);
        this.arrivalRateTextField.setEnabled(true);
    }

    /**
     * Set the Beta Text Field text.
     * @param betaText
     */
    public void setBetaText(double[] betaText)
    {
        String beta = "";

        for (int i = 0; i
                < betaText.length; i++)
        {
            beta += shortDecFormat.format(betaText[i]) + ", ";
        }

        this.betaTextField.setText(beta);
        this.betaLabel.setEnabled(true);
        this.betaTextField.setEnabled(true);
    }

    public void setController(OptimizeController controller)
    {
        this.controller = controller;
    }

    /**
     * Set the Defined Service Times Text Box text.
     * @param definedServiceTimes
     */
    public void setDefinedServiceTimesText(double[] definedServiceTimes)
    {
        serviceTimes = definedServiceTimes;

        String serviceTimesString = "";

        for (int i = 0; i < definedServiceTimes.length; i++)
        {
            serviceTimesString += Double.toString(definedServiceTimes[i]) + ", ";
        }

        setServiceTimesText(definedServiceTimes);

        this.definedServiceTimesTextField.setText(serviceTimesString);
        this.definedServiceTimesLabel.setEnabled(true);
        this.definedServiceTimesTextField.setEnabled(true);
    }

    /**
     * Set the current decimal format.
     * @param decimalFormat
     */
    public void setDecimalFormat(String decimalFormat)
    {
        // Set an String instance variable so we can use it later.
        this.shortDecimalFormat = decimalFormat;

        // Set a DecimalFormat instance variable so we can use it later.
        shortDecFormat = new DecimalFormat(decimalFormat);
    }

    /**
     *  Set the Demand Times text.
     * @param demandTimesText
     */
    public void setDemandTimesText(double[] demandTimesText)
    {
        String demandTime = "";

        for (int i = 0; i
                < demandTimesText.length; i++)
        {
            demandTime += shortDecFormat.format(demandTimesText[i]) + ", ";
        }

        this.demandTimesTextField.setText(demandTime);
        this.demandLabel.setEnabled(true);
        this.demandTimesTextField.setEnabled(true);
    }

    /**
     * Set Lambda Max Text.
     * @param lambdaMaxText
     */
    public void setLambdaMaxText(double lambdaMaxText)
    {
        this.lambdaMaxTextField.setText(epicDecFormat.format(lambdaMaxText));
        this.lambdaMaxTextField.setEnabled(true);
        this.newLambdaMaxLabel.setEnabled(true);
    }

    /**
     * Set Lambda Vector Text.
     * @param lambdaVectorText
     */
    public void setLambdaVectorText(double[] lambdaVectorText)
    {
        String lambda = "";

        for (int i = 0; i
                < lambdaVectorText.length; i++)
        {
            lambda += shortDecFormat.format(lambdaVectorText[i]) + ", ";
        }

        this.lambdaVectorTextField.setText(lambda);
        this.lambdaVectorTextField.setEnabled(true);
        this.newLambdaVectorLabel.setEnabled(true);
    }

    /**
     * Set Max Demand Times text.
     * @param maxDemandText
     */
    public void setMaxDemandText(double maxDemandText)
    {
        this.maxDemandTextField.setText(shortDecFormat.format(maxDemandText));
        this.maxDemandLabel.setEnabled(true);
        this.maxDemandTextField.setEnabled(true);
    }

    /**
     * Set New Demand Times text.
     * @param newDemandTimesText
     */
    public void setNewDemandTimesText(double[] newDemandTimesText)
    {
        String demandTime = "";

        for (int i = 0; i
                < newDemandTimesText.length; i++)
        {
            demandTime += shortDecFormat.format(newDemandTimesText[i]) + ", ";
        }

        this.newDemandTimesTextField.setText(demandTime);
        this.newDemandTimesLabel.setEnabled(true);
        this.newDemandTimesTextField.setEnabled(true);
    }

    /**
     * Set New Max Demand Text.
     * @param newMaxDemandText
     */
    public void setNewMaxDemandText(double newMaxDemandText)
    {
        this.newMaxDemandTextField.setText(shortDecFormat.format(newMaxDemandText));
        this.newMaxDemandLabel.setEnabled(true);
        this.newMaxDemandTextField.setEnabled(true);
    }

    /**
     * Set New Service Rate text.
     * @param newServiceRateText
     */
    public void setNewServiceRateText(double[] newServiceRateText)
    {
        String mu = "";

        for (int i = 0; i
                < newServiceRateText.length; i++)
        {
            mu += shortDecFormat.format(newServiceRateText[i]) + ", ";
        }

        this.newServiceRateTextField.setText(mu);
        this.newServiceRateLabel.setEnabled(true);
        this.newServiceRateTextField.setEnabled(true);
    }

    /**
     * Set New Service Times text.
     * @param newServiceTimesText
     */
    public void setNewServiceTimesText(double[] newServiceTimesText)
    {
        String sNew = "";

        for (int i = 0; i
                < newServiceTimesText.length; i++)
        {
            sNew += shortDecFormat.format(newServiceTimesText[i]) + ", ";
        }

        this.newServiceTimesTextField.setText(sNew);
        this.newServiceTimesLabel.setEnabled(true);
        this.newServiceTimesTextField.setEnabled(true);
    }

    /**
     * Set Lambda Vector Text.
     * @param lambdaVectorText
     */
    public void setOldLambdaVectorText(double[] oldLambdaVectorText)
    {
        String lambda = "";

        for (int i = 0; i
                < oldLambdaVectorText.length; i++)
        {
            lambda += shortDecFormat.format(oldLambdaVectorText[i]) + ", ";
        }

        this.oldLambdaVectorTextField.setText(lambda);
        this.oldLambdaVectorTextField.setEnabled(true);
        this.oldLambdaVectorLabel.setEnabled(true);
    }

    /**
     * Set New Service Rate text.
     * @param newServiceRateText
     */
    public void setOldServiceRateText(double[] oldServiceRateText)
    {
        String mu = "";

        for (int i = 0; i
                < oldServiceRateText.length; i++)
        {
            mu += shortDecFormat.format(oldServiceRateText[i]) + ", ";
        }

        this.oldServiceRateTextField.setText(mu);
        this.oldServiceRateLabel.setEnabled(true);
        this.oldServiceRateTextField.setEnabled(true);
    }

    /**
     * Set the Server Length Text.
     * @param oldServerLengthText
     */
    public void setOldServerLengthText(double[] oldServerLengthText)
    {
        String serverLength = "";

        for (int i = 0; i
                < oldServerLengthText.length; i++)
        {
            serverLength += shortDecFormat.format(oldServerLengthText[i]) + ", ";
        }
        this.oldServerLengthTextField.setText(serverLength);
        this.oldServerLengthTextField.setEnabled(true);
        this.oldServerLengthLabel.setEnabled(true);
    }

    /**
     * Set the Server Wait Time text.
     * @param oldServerWaitText
     */
    public void setOldServerWaitText(double[] oldServerWaitText)
    {
        String serverWait = "";

        for (int i = 0; i
                < oldServerWaitText.length; i++)
        {
            serverWait += shortDecFormat.format(oldServerWaitText[i]) + ", ";
        }

        this.oldServerWaitTextField.setText(serverWait);
        this.oldServerWaitTextField.setEnabled(true);
        this.oldServerWaitLabel.setEnabled(true);
    }

    /**
     * Set the Queue Length text.
     * @param oldQueueLengthText
     */
    public void setOldQueueLengthText(double[] oldQueueLengthText)
    {
        String lq = "";

        for (int i = 0; i
                < oldQueueLengthText.length; i++)
        {
            lq += shortDecFormat.format(oldQueueLengthText[i]) + ", ";
        }

        this.oldQueueLengthTextField.setText(lq);
        this.oldQueueLengthLabel.setEnabled(true);
        this.oldQueueLengthTextField.setEnabled(true);
    }

    /**
     *  Set the Queue Wait Time text.
     * @param oldQueueWaitText
     */
    public void setOldQueueWaitText(double[] oldQueueWaitText)
    {
        String wq = "";

        for (int i = 0; i
                < oldQueueWaitText.length; i++)
        {
            wq += shortDecFormat.format(oldQueueWaitText[i]) + ", ";
        }

        this.oldQueueWaitTextField.setText(wq);
        this.oldQueueWaitLabel.setEnabled(true);
        this.oldQueueWaitTextField.setEnabled(true);
    }

    /**
     * Set the Queue Length text.
     * @param queueLengthText
     */
    public void setQueueLengthText(double[] queueLengthText)
    {
        String lq = "";

        for (int i = 0; i
                < queueLengthText.length; i++)
        {
            lq += shortDecFormat.format(queueLengthText[i]) + ", ";
        }

        this.queueLengthTextField.setText(lq);
        this.queueLengthLabel.setEnabled(true);
        this.queueLengthTextField.setEnabled(true);
    }

    /**
     *  Set the Queue Wait Time text.
     * @param queueWaitText
     */
    public void setQueueWaitText(double[] queueWaitText)
    {
        String wq = "";

        for (int i = 0; i
                < queueWaitText.length; i++)
        {
            wq += shortDecFormat.format(queueWaitText[i]) + ", ";
        }

        this.queueWaitTextField.setText(wq);
        this.queueWaitLabel.setEnabled(true);
        this.queueWaitTextField.setEnabled(true);
    }

    public void setServiceTimes(double[] serviceTimes)
    {
        this.serviceTimes = serviceTimes;
        this.setServiceTimesText(this.serviceTimes);
        this.calculateSystemButton.setEnabled(true);
    }

    /**
     * Set the Server Length Text.
     * @param serverLengthText
     */
    public void setServerLengthText(double[] serverLengthText)
    {
        String serverLength = "";

        for (int i = 0; i
                < serverLengthText.length; i++)
        {
            serverLength += shortDecFormat.format(serverLengthText[i]) + ", ";
        }
        this.serverLengthTextField.setText(serverLength);
        this.serverLengthTextField.setEnabled(true);
        this.newServerLengthLabel.setEnabled(true);
    }

    /**
     * Set the Server Wait Time text.
     * @param serverWaitText
     */
    public void setServerWaitText(double[] serverWaitText)
    {
        String serverWait = "";

        for (int i = 0; i
                < serverWaitText.length; i++)
        {
            serverWait += shortDecFormat.format(serverWaitText[i]) + ", ";
        }

        this.serverWaitTextField.setText(serverWait);
        this.serverWaitTextField.setEnabled(true);
        this.newServerWaitLabel.setEnabled(true);
    }

    /**
     * Set the Service Times Text.
     * @param serviceTimesText
     */
    public void setServiceTimesText(double[] serviceTimesText)
    {
        String serviceTime = "";

        for (int i = 0; i
                < serviceTimesText.length; i++)
        {
            serviceTime += shortDecFormat.format(serviceTimesText[i]) + ", ";
        }

        this.definedServiceTimesTextField.setText(serviceTime);
        this.definedServiceTimesLabel.setEnabled(true);
        this.definedServiceTimesTextField.setEnabled(true);
    }

    /**
     * Set the Steady State Vector Text.
     * @param steadyStateVectorText
     */
    public void setSteadyStateVectorText(double[] steadyStateVectorText)
    {
        String steadyState = "";

        for (int i = 0; i
                < steadyStateVectorText.length; i++)
        {
            steadyState += shortDecFormat.format(steadyStateVectorText[i]) + ", ";
        }
        this.steadyStateVectorTextField.setText(steadyState);
        this.steadyStateVectorLabel.setEnabled(true);
        this.steadyStateVectorTextField.setEnabled(true);
    }

    /**
     * Set visitationVector Text.
     *
     * @param visitationVectorText
     */
    public void setVisitationVectorText(double[] visitationVectorText)
    {
        String visitationVector = "";

        for (int i = 0; i
                < visitationVectorText.length; i++)
        {
            visitationVector += shortDecFormat.format(visitationVectorText[i]) + ", ";
        }

        this.visitationVectorTextField.setText(visitationVector);
        this.visitationVectorLabel.setEnabled(true);
        this.visitationVectorTextField.setEnabled(true);
    }

    public void updateConvergenceOutputModelOutput(double[][] modelResult)
    {
        this.covergenceMatrix = modelResult;
    }

    public void updateSteadyStateOutputModelOutput(boolean converged)
    {
        this.serviceTimeButton.setEnabled(converged);

        if (!converged)
        {
            this.calculateSystemButton.setEnabled(false);
            this.resetSystemButton.setEnabled(false);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        resultsPanel = new javax.swing.JPanel();
        calculateSystemButton = new BlinkerButton("Calculate System", "Green");
        resetSystemButton = new BlinkerButton("Reset System", "Orange");
        serviceTimeButton = new BlinkerButton("Service Times", "Green");
        jPanel1 = new javax.swing.JPanel();
        maxDemandTextField = new javax.swing.JTextField();
        betaTextField = new javax.swing.JTextField();
        arrivalRateLabel = new javax.swing.JLabel();
        arrivalRateTextField = new javax.swing.JTextField();
        demandLabel = new javax.swing.JLabel();
        maxDemandLabel = new javax.swing.JLabel();
        demandTimesTextField = new javax.swing.JTextField();
        betaLabel = new javax.swing.JLabel();
        oldServiceRateLabel = new javax.swing.JLabel();
        oldServiceRateTextField = new javax.swing.JTextField();
        oldLambdaVectorLabel = new javax.swing.JLabel();
        oldLambdaVectorTextField = new javax.swing.JTextField();
        serviceTimesPanel = new javax.swing.JPanel();
        definedServiceTimesLabel = new javax.swing.JLabel();
        definedServiceTimesTextField = new javax.swing.JTextField();
        visitationVectorTextField = new javax.swing.JTextField();
        visitationVectorLabel = new javax.swing.JLabel();
        steadyStateVectorTextField = new javax.swing.JTextField();
        steadyStateVectorLabel = new javax.swing.JLabel();
        balancedSystem = new javax.swing.JPanel();
        newDemandTimesTextField = new javax.swing.JTextField();
        newMaxDemandLabel = new javax.swing.JLabel();
        newLambdaMaxLabel = new javax.swing.JLabel();
        newServiceRateTextField = new javax.swing.JTextField();
        lambdaVectorTextField = new javax.swing.JTextField();
        lambdaMaxTextField = new javax.swing.JTextField();
        newMaxDemandTextField = new javax.swing.JTextField();
        newServiceTimesTextField = new javax.swing.JTextField();
        newDemandTimesLabel = new javax.swing.JLabel();
        newServiceRateLabel = new javax.swing.JLabel();
        newServiceTimesLabel = new javax.swing.JLabel();
        newLambdaVectorLabel = new javax.swing.JLabel();
        nodesPanel = new javax.swing.JPanel();
        serverLengthTextField = new javax.swing.JTextField();
        newServerLengthLabel = new javax.swing.JLabel();
        serverWaitTextField = new javax.swing.JTextField();
        queueLengthTextField = new javax.swing.JTextField();
        queueLengthLabel = new javax.swing.JLabel();
        newServerWaitLabel = new javax.swing.JLabel();
        queueWaitTextField = new javax.swing.JTextField();
        queueWaitLabel = new javax.swing.JLabel();
        nodesPanel1 = new javax.swing.JPanel();
        oldServerLengthTextField = new javax.swing.JTextField();
        oldServerLengthLabel = new javax.swing.JLabel();
        oldServerWaitTextField = new javax.swing.JTextField();
        oldQueueLengthTextField = new javax.swing.JTextField();
        oldQueueLengthLabel = new javax.swing.JLabel();
        oldServerWaitLabel = new javax.swing.JLabel();
        oldQueueWaitTextField = new javax.swing.JTextField();
        oldQueueWaitLabel = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();

        resultsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        calculateSystemButton.setText("Calculate System");
        calculateSystemButton.setEnabled(false);
        calculateSystemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateSystemButtonActionPerformed(evt);
            }
        });

        resetSystemButton.setText("Reset System");
        resetSystemButton.setEnabled(false);
        resetSystemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSystemButtonActionPerformed(evt);
            }
        });

        serviceTimeButton.setText("Service Times");
        serviceTimeButton.setEnabled(false);
        serviceTimeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceTimeButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Unbalanced System", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 102, 204))); // NOI18N

        maxDemandTextField.setEditable(false);
        maxDemandTextField.setText("Max Demand");
        maxDemandTextField.setEnabled(false);

        betaTextField.setEditable(false);
        betaTextField.setText("Beta");
        betaTextField.setEnabled(false);

        arrivalRateLabel.setText("Arrival Rate: Lambda Max");
        arrivalRateLabel.setEnabled(false);

        arrivalRateTextField.setEditable(false);
        arrivalRateTextField.setText("Arrival Rate");
        arrivalRateTextField.setEnabled(false);

        demandLabel.setText("Initial Demand: D");
        demandLabel.setEnabled(false);

        maxDemandLabel.setText("Max Demand: dMax");
        maxDemandLabel.setEnabled(false);

        demandTimesTextField.setEditable(false);
        demandTimesTextField.setText("Demand Times");
        demandTimesTextField.setEnabled(false);

        betaLabel.setText("Beta: B");
        betaLabel.setEnabled(false);

        oldServiceRateLabel.setText("Service Rate: Mu");
        oldServiceRateLabel.setEnabled(false);

        oldServiceRateTextField.setEditable(false);
        oldServiceRateTextField.setText("Service Rate");
        oldServiceRateTextField.setEnabled(false);

        oldLambdaVectorLabel.setText("Lambda Vector:");
        oldLambdaVectorLabel.setEnabled(false);

        oldLambdaVectorTextField.setEditable(false);
        oldLambdaVectorTextField.setText("Lambda Vector");
        oldLambdaVectorTextField.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(oldServiceRateLabel)
                            .addComponent(maxDemandLabel)
                            .addComponent(demandLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(oldServiceRateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(maxDemandTextField)
                                .addComponent(demandTimesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(oldLambdaVectorLabel)
                            .addComponent(betaLabel)
                            .addComponent(arrivalRateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(arrivalRateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(betaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(oldLambdaVectorTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))))
                .addGap(187, 187, 187))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {arrivalRateTextField, betaTextField, demandTimesTextField, maxDemandTextField, oldLambdaVectorTextField, oldServiceRateTextField});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(demandTimesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(demandLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxDemandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxDemandLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldServiceRateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oldServiceRateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arrivalRateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(arrivalRateLabel))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldLambdaVectorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oldLambdaVectorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(betaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(betaLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        serviceTimesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "System", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 102, 204))); // NOI18N

        definedServiceTimesLabel.setText("Initial Service Times:");
        definedServiceTimesLabel.setEnabled(false);

        definedServiceTimesTextField.setEditable(false);
        definedServiceTimesTextField.setText("Service Times");
        definedServiceTimesTextField.setEnabled(false);

        visitationVectorTextField.setEditable(false);
        visitationVectorTextField.setText("Visitation Vector");
        visitationVectorTextField.setEnabled(false);

        visitationVectorLabel.setText("Visitation Vector: V");
        visitationVectorLabel.setEnabled(false);

        steadyStateVectorTextField.setEditable(false);
        steadyStateVectorTextField.setText("Steady State Vector");
        steadyStateVectorTextField.setEnabled(false);

        steadyStateVectorLabel.setText("Steady State Vector: Pi");
        steadyStateVectorLabel.setEnabled(false);

        javax.swing.GroupLayout serviceTimesPanelLayout = new javax.swing.GroupLayout(serviceTimesPanel);
        serviceTimesPanel.setLayout(serviceTimesPanelLayout);
        serviceTimesPanelLayout.setHorizontalGroup(
            serviceTimesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(serviceTimesPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(serviceTimesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(definedServiceTimesTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(serviceTimesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, serviceTimesPanelLayout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(visitationVectorLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(visitationVectorTextField))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, serviceTimesPanelLayout.createSequentialGroup()
                            .addGroup(serviceTimesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(definedServiceTimesLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(steadyStateVectorLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(steadyStateVectorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        serviceTimesPanelLayout.setVerticalGroup(
            serviceTimesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(serviceTimesPanelLayout.createSequentialGroup()
                .addGroup(serviceTimesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(definedServiceTimesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(definedServiceTimesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(serviceTimesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(steadyStateVectorLabel)
                    .addComponent(steadyStateVectorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(serviceTimesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(visitationVectorLabel)
                    .addComponent(visitationVectorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        balancedSystem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Balanced System", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 102, 204))); // NOI18N

        newDemandTimesTextField.setEditable(false);
        newDemandTimesTextField.setText("New Demand Times");
        newDemandTimesTextField.setEnabled(false);

        newMaxDemandLabel.setText("New Max Demand: dNewMax");
        newMaxDemandLabel.setEnabled(false);

        newLambdaMaxLabel.setText("Arrival Rate: Lambda Max");
        newLambdaMaxLabel.setEnabled(false);

        newServiceRateTextField.setEditable(false);
        newServiceRateTextField.setText("Service Rate");
        newServiceRateTextField.setEnabled(false);

        lambdaVectorTextField.setEditable(false);
        lambdaVectorTextField.setText("Lambda Vector");
        lambdaVectorTextField.setEnabled(false);

        lambdaMaxTextField.setEditable(false);
        lambdaMaxTextField.setText("Lambda Max");
        lambdaMaxTextField.setEnabled(false);

        newMaxDemandTextField.setEditable(false);
        newMaxDemandTextField.setText("New Max Demand ");
        newMaxDemandTextField.setEnabled(false);

        newServiceTimesTextField.setEditable(false);
        newServiceTimesTextField.setText("New Service Times");
        newServiceTimesTextField.setEnabled(false);

        newDemandTimesLabel.setText("New Demand: dNew");
        newDemandTimesLabel.setEnabled(false);

        newServiceRateLabel.setText("New Service Rate: New Mu");
        newServiceRateLabel.setEnabled(false);

        newServiceTimesLabel.setText("New Service Times: sNew");
        newServiceTimesLabel.setEnabled(false);

        newLambdaVectorLabel.setText("Lambda Vector:");
        newLambdaVectorLabel.setEnabled(false);

        javax.swing.GroupLayout balancedSystemLayout = new javax.swing.GroupLayout(balancedSystem);
        balancedSystem.setLayout(balancedSystemLayout);
        balancedSystemLayout.setHorizontalGroup(
            balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(balancedSystemLayout.createSequentialGroup()
                .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(balancedSystemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(newDemandTimesLabel)
                            .addComponent(newLambdaVectorLabel)
                            .addComponent(newServiceTimesLabel)
                            .addComponent(newLambdaMaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(balancedSystemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(balancedSystemLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(newServiceRateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(newMaxDemandLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lambdaVectorTextField)
                    .addComponent(lambdaMaxTextField)
                    .addComponent(newServiceRateTextField)
                    .addComponent(newMaxDemandTextField)
                    .addComponent(newDemandTimesTextField)
                    .addComponent(newServiceTimesTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
                .addContainerGap(201, Short.MAX_VALUE))
        );
        balancedSystemLayout.setVerticalGroup(
            balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(balancedSystemLayout.createSequentialGroup()
                .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newServiceTimesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newServiceTimesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newDemandTimesLabel)
                    .addComponent(newDemandTimesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newMaxDemandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newMaxDemandLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newServiceRateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newServiceRateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newLambdaMaxLabel)
                    .addComponent(lambdaMaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(balancedSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newLambdaVectorLabel)
                    .addComponent(lambdaVectorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nodesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Balanced System Nodes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 102, 204))); // NOI18N

        serverLengthTextField.setEditable(false);
        serverLengthTextField.setText("Server Length");
        serverLengthTextField.setEnabled(false);

        newServerLengthLabel.setText("Server Length:");
        newServerLengthLabel.setEnabled(false);

        serverWaitTextField.setEditable(false);
        serverWaitTextField.setText("Server Wait");
        serverWaitTextField.setEnabled(false);

        queueLengthTextField.setEditable(false);
        queueLengthTextField.setText("Queue Length");
        queueLengthTextField.setEnabled(false);

        queueLengthLabel.setText("Queue Length:");
        queueLengthLabel.setEnabled(false);

        newServerWaitLabel.setText("Server Wait:");
        newServerWaitLabel.setEnabled(false);

        queueWaitTextField.setEditable(false);
        queueWaitTextField.setText("Queue Wait");
        queueWaitTextField.setEnabled(false);

        queueWaitLabel.setText("Queue Wait:");
        queueWaitLabel.setEnabled(false);

        javax.swing.GroupLayout nodesPanelLayout = new javax.swing.GroupLayout(nodesPanel);
        nodesPanel.setLayout(nodesPanelLayout);
        nodesPanelLayout.setHorizontalGroup(
            nodesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nodesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nodesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(newServerLengthLabel)
                    .addComponent(newServerWaitLabel)
                    .addComponent(queueWaitLabel)
                    .addComponent(queueLengthLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(nodesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(queueLengthTextField)
                    .addComponent(serverWaitTextField)
                    .addComponent(serverLengthTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addComponent(queueWaitTextField))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        nodesPanelLayout.setVerticalGroup(
            nodesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nodesPanelLayout.createSequentialGroup()
                .addGroup(nodesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newServerLengthLabel)
                    .addComponent(serverLengthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nodesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newServerWaitLabel)
                    .addComponent(serverWaitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nodesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(queueWaitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(queueWaitLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nodesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(queueLengthLabel)
                    .addComponent(queueLengthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nodesPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Unbalanced System Nodes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 102, 204))); // NOI18N

        oldServerLengthTextField.setEditable(false);
        oldServerLengthTextField.setText("Server Length");
        oldServerLengthTextField.setEnabled(false);

        oldServerLengthLabel.setText("Server Length:");
        oldServerLengthLabel.setEnabled(false);

        oldServerWaitTextField.setEditable(false);
        oldServerWaitTextField.setText("Server Wait");
        oldServerWaitTextField.setEnabled(false);

        oldQueueLengthTextField.setEditable(false);
        oldQueueLengthTextField.setText("Queue Length");
        oldQueueLengthTextField.setEnabled(false);

        oldQueueLengthLabel.setText("Queue Length:");
        oldQueueLengthLabel.setEnabled(false);

        oldServerWaitLabel.setText("Server Wait:");
        oldServerWaitLabel.setEnabled(false);

        oldQueueWaitTextField.setEditable(false);
        oldQueueWaitTextField.setText("Queue Wait");
        oldQueueWaitTextField.setEnabled(false);

        oldQueueWaitLabel.setText("Queue Wait:");
        oldQueueWaitLabel.setEnabled(false);

        javax.swing.GroupLayout nodesPanel1Layout = new javax.swing.GroupLayout(nodesPanel1);
        nodesPanel1.setLayout(nodesPanel1Layout);
        nodesPanel1Layout.setHorizontalGroup(
            nodesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nodesPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nodesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(oldServerLengthLabel)
                    .addComponent(oldServerWaitLabel)
                    .addComponent(oldQueueWaitLabel)
                    .addComponent(oldQueueLengthLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(nodesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(oldQueueLengthTextField)
                    .addComponent(oldServerWaitTextField)
                    .addComponent(oldServerLengthTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addComponent(oldQueueWaitTextField))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        nodesPanel1Layout.setVerticalGroup(
            nodesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nodesPanel1Layout.createSequentialGroup()
                .addGroup(nodesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldServerLengthLabel)
                    .addComponent(oldServerLengthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nodesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldServerWaitLabel)
                    .addComponent(oldServerWaitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nodesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldQueueWaitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oldQueueWaitLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nodesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oldQueueLengthLabel)
                    .addComponent(oldQueueLengthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout resultsPanelLayout = new javax.swing.GroupLayout(resultsPanel);
        resultsPanel.setLayout(resultsPanelLayout);
        resultsPanelLayout.setHorizontalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(resultsPanelLayout.createSequentialGroup()
                        .addComponent(nodesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(177, 177, 177))
                    .addGroup(resultsPanelLayout.createSequentialGroup()
                        .addComponent(nodesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(177, 177, 177))
                    .addGroup(resultsPanelLayout.createSequentialGroup()
                        .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(serviceTimesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, resultsPanelLayout.createSequentialGroup()
                                .addComponent(serviceTimeButton)
                                .addGap(18, 18, 18)
                                .addComponent(calculateSystemButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetSystemButton))
                            .addComponent(balancedSystem, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        resultsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {calculateSystemButton, resetSystemButton, serviceTimeButton});

        resultsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {balancedSystem, jPanel1, nodesPanel, nodesPanel1, serviceTimesPanel});

        resultsPanelLayout.setVerticalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetSystemButton)
                    .addComponent(calculateSystemButton)
                    .addComponent(serviceTimeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceTimesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(balancedSystem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nodesPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nodesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        controlPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 797, Short.MAX_VALUE)
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 73, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resultsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void calculateSystemButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_calculateSystemButtonActionPerformed
    {//GEN-HEADEREND:event_calculateSystemButtonActionPerformed

        //double[][] calcMatrix = Transpose.tranposeMatrix(this.covergenceMatrix);
        controller.optimizeSystem(this.covergenceMatrix, this.serviceTimes, Globals.ALPHA);
        this.resetSystemButton.setEnabled(true);
}//GEN-LAST:event_calculateSystemButtonActionPerformed

    private void resetSystemButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_resetSystemButtonActionPerformed
    {//GEN-HEADEREND:event_resetSystemButtonActionPerformed
        controller.resetSystem();
}//GEN-LAST:event_resetSystemButtonActionPerformed

    private void serviceTimeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_serviceTimeButtonActionPerformed
    {//GEN-HEADEREND:event_serviceTimeButtonActionPerformed
        // Get a new Service Times Wizard
        // *Call to Controller*
        ServiceTimePanel serviceTimeWizard = new ServiceTimePanel(this.covergenceMatrix.length, this.controller);
}//GEN-LAST:event_serviceTimeButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel arrivalRateLabel;
    private javax.swing.JTextField arrivalRateTextField;
    private javax.swing.JPanel balancedSystem;
    private javax.swing.JLabel betaLabel;
    private javax.swing.JTextField betaTextField;
    private javax.swing.JButton calculateSystemButton;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JLabel definedServiceTimesLabel;
    private javax.swing.JTextField definedServiceTimesTextField;
    private javax.swing.JLabel demandLabel;
    private javax.swing.JTextField demandTimesTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField lambdaMaxTextField;
    private javax.swing.JTextField lambdaVectorTextField;
    private javax.swing.JLabel maxDemandLabel;
    private javax.swing.JTextField maxDemandTextField;
    private javax.swing.JLabel newDemandTimesLabel;
    private javax.swing.JTextField newDemandTimesTextField;
    private javax.swing.JLabel newLambdaMaxLabel;
    private javax.swing.JLabel newLambdaVectorLabel;
    private javax.swing.JLabel newMaxDemandLabel;
    private javax.swing.JTextField newMaxDemandTextField;
    private javax.swing.JLabel newServerLengthLabel;
    private javax.swing.JLabel newServerWaitLabel;
    private javax.swing.JLabel newServiceRateLabel;
    private javax.swing.JTextField newServiceRateTextField;
    private javax.swing.JLabel newServiceTimesLabel;
    private javax.swing.JTextField newServiceTimesTextField;
    private javax.swing.JPanel nodesPanel;
    private javax.swing.JPanel nodesPanel1;
    private javax.swing.JLabel oldLambdaVectorLabel;
    private javax.swing.JTextField oldLambdaVectorTextField;
    private javax.swing.JLabel oldQueueLengthLabel;
    private javax.swing.JTextField oldQueueLengthTextField;
    private javax.swing.JLabel oldQueueWaitLabel;
    private javax.swing.JTextField oldQueueWaitTextField;
    private javax.swing.JLabel oldServerLengthLabel;
    private javax.swing.JTextField oldServerLengthTextField;
    private javax.swing.JLabel oldServerWaitLabel;
    private javax.swing.JTextField oldServerWaitTextField;
    private javax.swing.JLabel oldServiceRateLabel;
    private javax.swing.JTextField oldServiceRateTextField;
    private javax.swing.JLabel queueLengthLabel;
    private javax.swing.JTextField queueLengthTextField;
    private javax.swing.JLabel queueWaitLabel;
    private javax.swing.JTextField queueWaitTextField;
    private javax.swing.JButton resetSystemButton;
    private javax.swing.JPanel resultsPanel;
    private javax.swing.JTextField serverLengthTextField;
    private javax.swing.JTextField serverWaitTextField;
    private javax.swing.JButton serviceTimeButton;
    private javax.swing.JPanel serviceTimesPanel;
    private javax.swing.JLabel steadyStateVectorLabel;
    private javax.swing.JTextField steadyStateVectorTextField;
    private javax.swing.JLabel visitationVectorLabel;
    private javax.swing.JTextField visitationVectorTextField;
    // End of variables declaration//GEN-END:variables
}
