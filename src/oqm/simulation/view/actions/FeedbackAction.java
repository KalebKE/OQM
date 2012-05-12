/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.simulation.view.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oqm.feedback.controller.FeedbackController;

/**
 *
 * @author Kaleb
 */
public class FeedbackAction implements ActionListener
{
    private FeedbackController controller;

    public FeedbackAction(FeedbackController controller)
    {
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("generateFeedback"))
        {
            controller.generateFeedback();
        }
        if (e.getActionCommand().equals("optimumFeedback"))
        {
            controller.proportionalityAndIterations();
        }
    }
}
