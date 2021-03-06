/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oqm.main;

import java.awt.Color;
import oqm.factory.OQMSimulationFactory;
import oqm.simulation.view.SimControlView;

/**
 *
 * @author Kaleb
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                          javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.put("control", new Color(85, 85, 85));
                    javax.swing.UIManager.put("nimbusBase", new Color(51, 51, 51));
                    javax.swing.UIManager.put("nimbusFocus", new Color(51, 51, 51));
                    javax.swing.UIManager.put("nimbusLightBackground", new Color(153, 153, 153));
                    javax.swing.UIManager.put("nimbusSelectionBackground", new Color(90, 130, 195));
                    javax.swing.UIManager.put("text", new Color(238, 238, 238));
                    break;

                }

            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(SimControlView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(SimControlView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(SimControlView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(SimControlView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                // Create an instance of the mavn controller
                // to run the application.
                OQMSimulationFactory mavn = new OQMSimulationFactory();
            }
        });
    }

}
