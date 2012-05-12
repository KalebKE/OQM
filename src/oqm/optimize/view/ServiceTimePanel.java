/*
ServiceTimeTemplatePanel -- a class within the Open Queueing Model (OQM).
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
package oqm.optimize.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import oqm.optimize.controller.OptimizeController;

/**
 * A special JFrame that produces a temlate for defining Service Times
 * for the OQM application.
 * @author Kaleb
 */
public class ServiceTimePanel extends JFrame implements ActionListener
{

    private JPanel main = new JPanel();
    private JSpinner[] spinner;
    private SpinnerNumberModel[] model;
    private OptimizeController controller;

    /**
     * Initialize instance.
     * @param m the number of columns
     * @param controller the controller
     */
    public ServiceTimePanel(int m, OptimizeController controller)
    {
        setContentPane(main);

        this.controller = controller;

        // Dynamically size the width
        this.setSize(m * 130, 300);

        spinner = new JSpinner[m];
        model = new SpinnerNumberModel[m];

        // init spinners
        for (int i = 0; i < m; i++)
        {
            model[i] = new SpinnerNumberModel(0.0, 0.0, 100000.0, 0.5);
            spinner[i] = new JSpinner(model[i]);
            ((JSpinner.DefaultEditor) spinner[i].getEditor()).getTextField().setColumns(3);
        }

        // init grid layout for matrix spinners
        main.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.CENTER;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = .25;

        main.add(new HeaderPanel("Service Times"), c);

        c.gridx = 0;
        c.gridy = 1;
        c.weighty = .65;

        main.add(initPanel(), c);

        c.gridy = 2;
        c.weighty = .10;

        JButton confirm = new JButton("Confirm!");
        confirm.addActionListener(this);
        main.add(confirm, c);

        setVisible(true);
    }

    public JPanel initPanel()
    {
        JPanel template = new JPanel(new GridBagLayout());

        template.setSize(spinner.length * 20, spinner.length * 20);

        // layered pane
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1 / spinner.length + 1; // request any extra vertical space
        c.weighty = 1 / spinner.length + 1; // request any extra vertical space

        c.gridx = 0;
        template.add(new JLabel("Service Times:"));

        for (int i = 0; i < spinner.length; i++)
        {
            c.gridx = i + 1;
            c.ipadx = 2;
            c.ipady = 2;
            template.add(spinner[i], c);
        }
        return template;
    }

    public double[] getMatrix()
    {
        double[] matrix = new double[spinner.length];

        for (int i = 0; i < matrix.length; i++)
        {
            matrix[i] = model[i].getNumber().doubleValue();
        }

        return matrix;
    }

    public void actionPerformed(ActionEvent e)
    {
        this.dispose();
        controller.setServiceTimes(getMatrix());
    }
}
