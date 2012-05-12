/*
SimControlView -- a class within the OQM (Open Queuing Model).
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
package oqm.simulation.view;

import java.awt.Frame;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

/**
 * The main View for the OQM application.
 * @author Kaleb
 */
public class SimControlView extends JFrame
{
    private ActionListener feedbackAction;
    private ActionListener networkAction;
    private ActionListener outputAction;
    private ActionListener plotterAction;
    private ActionListener propertiesAction;
    private ActionListener simulationAction;
    private ActionListener runSimulationAction;
    private ActionListener viewAction;
    private JPanel outputView;
    private JPanel inputView;
    private JPanel optimizeView;

    /** Creates new form Main */
    public SimControlView(ActionListener networkAction,
            ActionListener outputAction, ActionListener simulationAction,
            ActionListener plotterAction, ActionListener propertiesAction,
            ActionListener runSimulationAction, ActionListener viewAction,
            ActionListener feedbackAction,
            JPanel inputView, JPanel outputView, JPanel optimizeView)
    {
        initComponents();

        this.networkAction = networkAction;
        this.outputAction = outputAction;
        this.plotterAction = plotterAction;
        this.propertiesAction = propertiesAction;
        this.simulationAction = simulationAction;
        this.runSimulationAction = runSimulationAction;
        this.viewAction = viewAction;
        this.feedbackAction = feedbackAction;

        initActions();

        this.inputView = inputView;
        this.outputView = outputView;
        this.optimizeView = optimizeView;

        this.inputView.setVisible(true);
        this.outputView.setVisible(true);
        this.optimizeView.setVisible(true);

        this.setVisible(true);
        this.pack();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainScrollPane.setViewportView(this.inputView);
        mainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.validate();
        mainScrollPane.updateUI();

    }

    private void initActions()
    {
        this.importModelMenuItem.addActionListener(simulationAction);
        this.importModelMenuItem.setActionCommand("importSimulationAction");

        this.saveModelMenuItem.addActionListener(simulationAction);
        this.saveModelMenuItem.setActionCommand("exportSimulationAction");

        this.closeSimulationMenuItem.addActionListener(simulationAction);
        this.closeSimulationMenuItem.setActionCommand("clearSimulationAction");

        this.saveOutputMenuItem.addActionListener(outputAction);
        this.saveOutputMenuItem.setActionCommand("saveOutputAction");

        this.clearOutputMenuItem.addActionListener(outputAction);
        this.clearOutputMenuItem.setActionCommand("clearOutputAction");

        this.viewInputMenuItem.addActionListener(viewAction);
        this.viewInputMenuItem.setActionCommand("useInputViewAction");

        this.viewOutputMenuItem.addActionListener(viewAction);
        this.viewOutputMenuItem.setActionCommand("useOutputViewAction");

        this.viewOptimizerMenuItem.addActionListener(viewAction);
        this.viewOptimizerMenuItem.setActionCommand("useOptimzerViewAction");

        this.editPropertiesMenuItem.addActionListener(propertiesAction);
        this.editPropertiesMenuItem.setActionCommand("loadPropertiesAction");

        this.runSimulationMenuItem.addActionListener(runSimulationAction);
        this.runSimulationMenuItem.setActionCommand("runSimulationAction");

        this.resetSimulationMenuItem.addActionListener(runSimulationAction);
        this.resetSimulationMenuItem.setActionCommand("resetSimulationAction");

        this.animateNetworkMenuItem.addActionListener(networkAction);
        this.animateNetworkMenuItem.setActionCommand("animateNetworkAction");

        this.renderNetworkMenuItem.addActionListener(networkAction);
        this.renderNetworkMenuItem.setActionCommand("renderNetworkAction");

        this.clearNetworkMenuItem.addActionListener(networkAction);
        this.clearNetworkMenuItem.setActionCommand("clearNetworkAction");

        this.renderPointsMenuItem.addActionListener(plotterAction);
        this.renderPointsMenuItem.setActionCommand("useScatterPlotAction");

        this.renderTimeMenuItem.addActionListener(plotterAction);
        this.renderTimeMenuItem.setActionCommand("useLinePlotAction");

        this.clearPlotterMenuItem.addActionListener(plotterAction);
        this.clearPlotterMenuItem.setActionCommand("clearPlotAction");

        this.generateFeedbackMenuItem.addActionListener(feedbackAction);
        this.generateFeedbackMenuItem.setActionCommand("generateFeedback");
        this.optimizeFeedbackMenuItem.addActionListener(feedbackAction);
        this.optimizeFeedbackMenuItem.setActionCommand("optimumFeedback");
    }

    public JCheckBoxMenuItem getAnimateNetworkMenuItem()
    {
        return animateNetworkMenuItem;
    }

    public JMenuItem getClearNetworkMenuItem()
    {
        return clearNetworkMenuItem;
    }

    public JMenuItem getClearOutputMenuItem()
    {
        return clearOutputMenuItem;
    }

    public JMenuItem getClearPlotterMenuItem()
    {
        return clearPlotterMenuItem;
    }

    public JMenuItem getCloseSimulationMenuItem()
    {
        return closeSimulationMenuItem;
    }

    public JMenuItem getEditPropertiesMenuItem()
    {
        return editPropertiesMenuItem;
    }

    public JMenuItem getExitMenuItem()
    {
        return exitMenuItem;
    }

    public JMenuItem getImportModelMenuItem()
    {
        return importModelMenuItem;
    }

    public JCheckBoxMenuItem getMonteCarloMenuItem()
    {
        return convergenceMenuItem;
    }

    public JCheckBoxMenuItem getRenderPointsMenuItem()
    {
        return renderPointsMenuItem;
    }

    public JCheckBoxMenuItem getRenderNetworkMenuItem()
    {
        return renderNetworkMenuItem;
    }

    public JCheckBoxMenuItem getRenderTimeMenuItem()
    {
        return renderTimeMenuItem;
    }

    public JMenuItem getResetSimulationMenuItem()
    {
        return resetSimulationMenuItem;
    }

    public JMenuItem getRunSimulationMenuItem()
    {
        return runSimulationMenuItem;
    }

    public JMenuItem getSaveModelMenuItem()
    {
        return saveModelMenuItem;
    }

    public JMenuItem getSaveOutputMenuItem()
    {
        return saveOutputMenuItem;
    }

    public JCheckBoxMenuItem getViewInputMenuItem()
    {
        return viewInputMenuItem;
    }

    public JCheckBoxMenuItem getViewOptimizerMenuItem()
    {
        return viewOptimizerMenuItem;
    }

    public void setClearOutputMenuItem(JMenuItem clearOutputMenuItem)
    {
        this.clearOutputMenuItem = clearOutputMenuItem;
    }

    public void setClearPlotterMenuItem(JMenuItem clearPlotterMenuItem)
    {
        this.clearPlotterMenuItem = clearPlotterMenuItem;
    }

    public void setCloseSimulationMenuItem(JMenuItem closeSimulationMenuItem)
    {
        this.closeSimulationMenuItem = closeSimulationMenuItem;
    }

    public void setEditPropertiesMenuItem(JMenuItem editPropertiesMenuItem)
    {
        this.editPropertiesMenuItem = editPropertiesMenuItem;
    }

    public void setExitMenuItem(JMenuItem exitMenuItem)
    {
        this.exitMenuItem = exitMenuItem;
    }

    public void setImportModelMenuItem(JMenuItem importModelMenuItem)
    {
        this.importModelMenuItem = importModelMenuItem;
    }

    public void setRenderNetworkMenuItem(JCheckBoxMenuItem renderNetworkMenuItem)
    {
        this.renderNetworkMenuItem = renderNetworkMenuItem;
    }

    public void setRenderPointsMenuItem(JCheckBoxMenuItem renderPointsMenuItem)
    {
        this.renderPointsMenuItem = renderPointsMenuItem;
    }

    public void setRenderTimeMenuItem(JCheckBoxMenuItem renderTimeMenuItem)
    {
        this.renderTimeMenuItem = renderTimeMenuItem;
    }

    public void setResetSimulationMenuItem(JMenuItem resetSimulationMenuItem)
    {
        this.resetSimulationMenuItem = resetSimulationMenuItem;
    }

    public void setRunSimulationMenuItem(JMenuItem runSimulationMenuItem)
    {
        this.runSimulationMenuItem = runSimulationMenuItem;
    }

    public void setSaveModelMenuItem(JMenuItem saveModelMenuItem)
    {
        this.saveModelMenuItem = saveModelMenuItem;
    }

    public void setSaveOutputMenuItem(JMenuItem saveOutputMenuItem)
    {
        this.saveOutputMenuItem = saveOutputMenuItem;
    }

    public void setViewInputMenuItem(JCheckBoxMenuItem viewInputMenuItem)
    {
        this.viewInputMenuItem = viewInputMenuItem;
    }

    public JCheckBoxMenuItem getViewOutputMenuItem()
    {
        return viewOutputMenuItem;
    }

    public void setAnimateNetworkMenuItem(JCheckBoxMenuItem animateNetworkMenuItem)
    {
        this.animateNetworkMenuItem = animateNetworkMenuItem;
    }

    public void setClearNetworkMenuItem(JMenuItem clearNetworkMenuItem)
    {
        this.clearNetworkMenuItem = clearNetworkMenuItem;
    }

    public void setViewOutputMenuItem(JCheckBoxMenuItem viewOutputMenuItem)
    {
        this.viewOutputMenuItem = viewOutputMenuItem;
    }

    public void setInputView()
    {
        mainScrollPane.setViewportView(inputView);
    }

    public void setOutputView()
    {
        mainScrollPane.setViewportView(outputView);
    }

    public void setOptimizeView()
    {
        mainScrollPane.setViewportView(optimizeView);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainScrollPane = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        importModelMenuItem = new javax.swing.JMenuItem();
        saveModelMenuItem = new javax.swing.JMenuItem();
        closeSimulationMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        saveOutputMenuItem = new javax.swing.JMenuItem();
        clearOutputMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        viewNetworkMenu = new javax.swing.JMenu();
        viewOutputMenuItem = new javax.swing.JCheckBoxMenuItem();
        viewInputMenuItem = new javax.swing.JCheckBoxMenuItem();
        viewOptimizerMenuItem = new javax.swing.JCheckBoxMenuItem();
        propertiesMenu = new javax.swing.JMenu();
        editPropertiesMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        convergenceMenuItem = new javax.swing.JCheckBoxMenuItem();
        runMenu = new javax.swing.JMenu();
        runSimulationMenuItem = new javax.swing.JMenuItem();
        resetSimulationMenuItem = new javax.swing.JMenuItem();
        networkMenu = new javax.swing.JMenu();
        animateNetworkMenuItem = new javax.swing.JCheckBoxMenuItem();
        renderNetworkMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        clearNetworkMenuItem = new javax.swing.JMenuItem();
        plotterMenu = new javax.swing.JMenu();
        renderPointsMenuItem = new javax.swing.JCheckBoxMenuItem();
        renderTimeMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        clearPlotterMenuItem = new javax.swing.JMenuItem();
        periodicityMenu = new javax.swing.JMenu();
        generateFeedbackMenuItem = new javax.swing.JMenuItem();
        optimizeFeedbackMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });

        mainScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainScrollPane.setMaximumSize(new java.awt.Dimension(1200, 1000));
        mainScrollPane.setPreferredSize(new java.awt.Dimension(1050, 1002));

        fileMenu.setText("File");

        importModelMenuItem.setText("Open Simulation");
        fileMenu.add(importModelMenuItem);

        saveModelMenuItem.setText("Save Simulation");
        saveModelMenuItem.setEnabled(false);
        fileMenu.add(saveModelMenuItem);

        closeSimulationMenuItem.setText("Close Simulation");
        closeSimulationMenuItem.setEnabled(false);
        fileMenu.add(closeSimulationMenuItem);
        fileMenu.add(jSeparator1);

        saveOutputMenuItem.setText("Save Output");
        saveOutputMenuItem.setEnabled(false);
        fileMenu.add(saveOutputMenuItem);

        clearOutputMenuItem.setText("Clear Output");
        clearOutputMenuItem.setEnabled(false);
        fileMenu.add(clearOutputMenuItem);
        fileMenu.add(jSeparator2);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        viewNetworkMenu.setText("Views");

        viewOutputMenuItem.setSelected(true);
        viewOutputMenuItem.setText("View Simulation Output");
        viewNetworkMenu.add(viewOutputMenuItem);

        viewInputMenuItem.setText("View Simulation Input");
        viewNetworkMenu.add(viewInputMenuItem);

        viewOptimizerMenuItem.setText("View Simulation Optimization");
        viewNetworkMenu.add(viewOptimizerMenuItem);

        jMenuBar1.add(viewNetworkMenu);

        propertiesMenu.setText("Properties");

        editPropertiesMenuItem.setText("Edit Properties");
        editPropertiesMenuItem.setEnabled(false);
        propertiesMenu.add(editPropertiesMenuItem);
        propertiesMenu.add(jSeparator3);

        convergenceMenuItem.setSelected(true);
        convergenceMenuItem.setText("Convergence Simulation");
        convergenceMenuItem.setEnabled(false);
        propertiesMenu.add(convergenceMenuItem);

        jMenuBar1.add(propertiesMenu);

        runMenu.setText("Run");

        runSimulationMenuItem.setText("Run Simulation");
        runSimulationMenuItem.setEnabled(false);
        runMenu.add(runSimulationMenuItem);

        resetSimulationMenuItem.setText("Reset Simulation");
        resetSimulationMenuItem.setEnabled(false);
        runMenu.add(resetSimulationMenuItem);

        jMenuBar1.add(runMenu);

        networkMenu.setText("Network");

        animateNetworkMenuItem.setSelected(true);
        animateNetworkMenuItem.setText("Animate Network");
        animateNetworkMenuItem.setEnabled(false);
        networkMenu.add(animateNetworkMenuItem);

        renderNetworkMenuItem.setText("Render Network");
        renderNetworkMenuItem.setEnabled(false);
        networkMenu.add(renderNetworkMenuItem);
        networkMenu.add(jSeparator4);

        clearNetworkMenuItem.setText("Clear Network");
        clearNetworkMenuItem.setEnabled(false);
        networkMenu.add(clearNetworkMenuItem);

        jMenuBar1.add(networkMenu);

        plotterMenu.setText("Plotter");

        renderPointsMenuItem.setSelected(true);
        renderPointsMenuItem.setText("Render Points (Scatter Plot)");
        renderPointsMenuItem.setEnabled(false);
        plotterMenu.add(renderPointsMenuItem);

        renderTimeMenuItem.setText("Render Time (Line Plot)");
        renderTimeMenuItem.setEnabled(false);
        plotterMenu.add(renderTimeMenuItem);
        plotterMenu.add(jSeparator5);

        clearPlotterMenuItem.setText("Clear Plotter");
        clearPlotterMenuItem.setEnabled(false);
        plotterMenu.add(clearPlotterMenuItem);

        jMenuBar1.add(plotterMenu);

        periodicityMenu.setText("Periodicity");

        generateFeedbackMenuItem.setText("Generate Feedback");
        periodicityMenu.add(generateFeedbackMenuItem);

        optimizeFeedbackMenuItem.setText("Find Optimum Feedback");
        periodicityMenu.add(optimizeFeedbackMenuItem);

        jMenuBar1.add(periodicityMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_formPropertyChange
    {//GEN-HEADEREND:event_formPropertyChange
    }//GEN-LAST:event_formPropertyChange

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exitMenuItemActionPerformed
    {//GEN-HEADEREND:event_exitMenuItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem animateNetworkMenuItem;
    private javax.swing.JMenuItem clearNetworkMenuItem;
    private javax.swing.JMenuItem clearOutputMenuItem;
    private javax.swing.JMenuItem clearPlotterMenuItem;
    private javax.swing.JMenuItem closeSimulationMenuItem;
    private javax.swing.JCheckBoxMenuItem convergenceMenuItem;
    private javax.swing.JMenuItem editPropertiesMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem generateFeedbackMenuItem;
    private javax.swing.JMenuItem importModelMenuItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JMenu networkMenu;
    private javax.swing.JMenuItem optimizeFeedbackMenuItem;
    private javax.swing.JMenu periodicityMenu;
    private javax.swing.JMenu plotterMenu;
    private javax.swing.JMenu propertiesMenu;
    private javax.swing.JCheckBoxMenuItem renderNetworkMenuItem;
    private javax.swing.JCheckBoxMenuItem renderPointsMenuItem;
    private javax.swing.JCheckBoxMenuItem renderTimeMenuItem;
    private javax.swing.JMenuItem resetSimulationMenuItem;
    private javax.swing.JMenu runMenu;
    private javax.swing.JMenuItem runSimulationMenuItem;
    private javax.swing.JMenuItem saveModelMenuItem;
    private javax.swing.JMenuItem saveOutputMenuItem;
    private javax.swing.JCheckBoxMenuItem viewInputMenuItem;
    private javax.swing.JMenu viewNetworkMenu;
    private javax.swing.JCheckBoxMenuItem viewOptimizerMenuItem;
    private javax.swing.JCheckBoxMenuItem viewOutputMenuItem;
    // End of variables declaration//GEN-END:variables
}
