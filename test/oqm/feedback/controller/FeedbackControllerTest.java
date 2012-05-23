/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.feedback.controller;

import oqm.algorithm.model.ConvergenceSimulation;
import oqm.globals.Globals;
import oqm.input.model.SystemInputModel;
import oqm.output.model.IterationOutputModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kaleb
 */
public class FeedbackControllerTest
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

    public FeedbackControllerTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* FeedbackControllerTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* FeedbackControllerTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* FeedbackControllerTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* FeedbackControllerTest: @After method");
    }

    /**
     * Test of generateFeedback method, of class FeedbackController.
     */
    @Test
    public void testGenerateFeedback()
    {
        System.out.println("* FeedbackControllerTest: testGenerateFeedback()");

        SystemInputModel inputModel = new SystemInputModel();
        IterationOutputModel outputModel = new IterationOutputModel();
        ConvergenceSimulation convergenceSimulation = new ConvergenceSimulation();

        FeedbackController instance = new FeedbackController(inputModel, outputModel, convergenceSimulation);

        Globals.FEEDBACK = 0.1;

        inputModel.setModelInput(matrix0);

        instance.generateFeedback();

        double[][] result =
        {
            {
                0.1, 0.9, 0
            },
            {
                0.45, 0.1, 0.45
            },
            {
                0.45, 0.45, 0.1
            }
        };

        boolean equal = true;

        for (int i = 0; i < result.length; i++)
        {
            for (int j = 0; j < result[i].length; j++)
            {
                if (result[i][j] != inputModel.getModelInput()[i][j])
                {
                    equal = false;
                }
            }
        }

        assertTrue(equal);
    }

    /**
     * Test of proportionalityAndIterations method, of class FeedbackController.
     */
    @Test
    public void testProportionalityAndIterations()
    {
        System.out.println("* FeedbackControllerTest: testProportionalityAndIterations()");

        SystemInputModel inputModel = new SystemInputModel();
        IterationOutputModel outputModel = new IterationOutputModel();
        ConvergenceSimulation convergenceSimulation = new ConvergenceSimulation();
        convergenceSimulation.sameThreadExecution(true);

        inputModel.registerObserver(convergenceSimulation);
        convergenceSimulation.registerObserver(outputModel);
        
        FeedbackController instance = new FeedbackController(inputModel, outputModel, convergenceSimulation);
        
        inputModel.setModelInput(matrix0);
        
        instance.proportionalityAndIterations();

        if (instance.getIterationCount().size() == 0)
        {
            assertTrue(true);
        } else
        {
            assertTrue(false);
        }
    }
}
