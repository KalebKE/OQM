
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kaleb
 */
public class BarPlotTest
{
    public static void main(String[] args)
    {
       Plot2DPanel plot = new Plot2DPanel();
       double[] y = {1,2,3,4};
       plot.addBarPlot("test", y);
       JFrame frame = new JFrame();
       frame.add(plot);
       frame.pack();
       frame.setVisible(true);
    }
}
