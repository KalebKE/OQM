/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.input.view;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import oqm.globals.Globals;
import oqm.input.model.SystemInputModel;
import oqm.input.model.changeEvent.InputModelChangeEvent;
import oqm.input.view.action.InputModelViewAction;
import oqm.input.view.state.InputViewState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulyn.input.controller.InputController;
import simulyn.input.model.InputModelInterface;
import static org.junit.Assert.*;

/**
 *
 * @author Kaleb
 */
public class SystemInputModelViewTest
{

    // Valid
    private double[][] matrix0 =
    {
        {
            0, 1, 0
        },
        {
            0.5, 0, 0.5
        },
        {
            0.5, 0.5, 0
        }
    };
    // Valid
    private double[][] matrix1 =
    {
        {
            0, 1, 0, 0
        },
        {
            0.5, 0, 0.5, 0
        },
        {
            0.25, 0.25, 0, 0.5
        },
        {
            0.75, 0, 0.25, 0
        }
    };
    // Valid
    private double[][] matrix2 =
    {
        {
            0, 1, 0, 0
        },
        {
            0.5, 0, 0.5, 0
        },
        {
            0.333, 0.333, 0, 0.333
        },
        {
            0.75, 0, 0.25, 0
        }
    };

    public SystemInputModelViewTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* SystemInputModelViewTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* SystemInputModelViewTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* SystemInputModelViewTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* SystemInputModelViewTest: @After method");
    }

    /**
     * Test of updateSystemInputModel method, of class SystemInputModelView.
     */
    @Test
    public void testUpdateSystemInputModel()
    {
        System.out.println("* SystemInputModelViewTest: testRegisterObserver()");

        // Tell the application to turn off certain functionality so we
        // can test blocks of code independently. 
        Globals.TEST_MODE = true;

        SystemInputModel systemInputModel = new SystemInputModel();

        ArrayList<InputModelInterface> inputModels = new ArrayList<InputModelInterface>();
        inputModels.add(Globals.SYSTEM_INPUT_MODEL, systemInputModel);

        // Initialize the input controllers.
        InputController systemInputModelController = new InputController(systemInputModel);

        InputModelViewAction systemInputModelPanelAction = new InputModelViewAction(systemInputModelController, systemInputModel);

        InputViewState systemInputModelState = new InputViewState();

        InputModelChangeEvent modelChanged = new InputModelChangeEvent(inputModels, null);

        SystemInputModelView instance = new SystemInputModelView(
                systemInputModelPanelAction, systemInputModelController,
                systemInputModel, systemInputModelState, modelChanged);

        // register with the input model observers with the view
        ((SystemInputModel) systemInputModel).registerObserver((SystemInputModelView) instance);

        ((InputModelViewAction) systemInputModelPanelAction).setView(instance);
        ((InputViewState) systemInputModelState).setView(instance);

        JFrame frame = new JFrame();
        frame.add(instance);
        frame.pack();
        frame.setVisible(true);

        systemInputModel.setModelInput(matrix0);

        boolean valid = true;

        // Make sure the System we sent to the input model
        // is the same as the System that the input model
        // pushes to observers.
        for (int i = 0; i < matrix0.length; i++)
        {
            for (int j = 0; j < matrix0[i].length; j++)
            {
                if (instance.getModelInput()[i][j] != matrix0[i][j])
                {
                    valid = false;
                }
            }
        }

        assertTrue(valid);

        systemInputModel.setModelInput(matrix1);

        boolean valid1 = true;

        // Make sure the System we sent to the input model
        // is the same as the System that the input model
        // pushes to observers.
        for (int i = 0; i < matrix1.length; i++)
        {
            for (int j = 0; j < matrix1[i].length; j++)
            {
                if (instance.getModelInput()[i][j] != matrix1[i][j])
                {
                    valid1 = false;
                }
            }
        }

        assertTrue(valid1);

        systemInputModel.setModelInput(matrix2);

        boolean valid2 = true;

        // Make sure the System we sent to the input model
        // is the same as the System that the input model
        // pushes to observers.
        for (int i = 0; i < matrix2.length; i++)
        {
            for (int j = 0; j < matrix2[i].length; j++)
            {
                if (instance.getModelInput()[i][j] != matrix2[i][j])
                {
                    valid2 = false;
                }
            }
        }

        assertTrue(valid2);
    }
}
