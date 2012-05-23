/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.algorithm.model.state;

import oqm.algorithm.model.ConvergenceSimulation;
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
public class ConvergenceAlgorithmModelStateTest
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

    public ConvergenceAlgorithmModelStateTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: @After method");
    }

    /**
     * Test of getConvergenceMatrix method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testGetConvergenceMatrix()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testGetConvergenceMatrix()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        instance.setConvergenceOutput(matrix0);
        double[][] result = instance.getConvergenceMatrix();

        boolean equal = true;

        for (int i = 0; i < matrix0.length; i++)
        {
            for (int j = 0; j < matrix0[i].length; j++)
            {
                if (result[i][j] != matrix0[i][j])
                {
                    equal = false;
                }
            }
        }

        assertTrue(equal);
    }

    /**
     * Test of getIterationCount method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testGetIterationCount()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testGetIterationCount()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        int expResult = 1020;
        instance.setIterationCount(expResult);
        int result = instance.getIterationCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of isConvergenceOutputReady method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testIsConvergenceOutputReady()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testIsConvergenceOutputReady()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        boolean expResult0 = false;
        instance.setConvergenceOutputReady(expResult0);
        boolean result0 = instance.isConvergenceOutputReady();
        assertEquals(expResult0, result0);

        boolean expResult1 = false;
        instance.setConvergenceOutputReady(expResult1);
        boolean result1 = instance.isConvergenceOutputReady();
        assertEquals(expResult1, result1);
    }

    /**
     * Test of isSteadyStateFound method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testIsSteadyStateFound()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testIsSteadyStateFound()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        boolean expResult = false;
        instance.setSteadyStateFound(expResult);

        boolean result = instance.isSteadyStateFound();
        assertEquals(expResult, result);

        expResult = true;
        instance.setSteadyStateFound(expResult);

        result = instance.isSteadyStateFound();
        assertEquals(expResult, result);
    }

    /**
     * Test of setConvergenceOutput method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testSetConvergenceOutput()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testIsSteadyStateFound()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        instance.setConvergenceOutput(matrix0);

        double[][] result = instance.getConvergenceMatrix();

        boolean equal = true;

        for (int i = 0; i < matrix0.length; i++)
        {
            for (int j = 0; j < matrix0[i].length; j++)
            {
                if (result[i][j] != matrix0[i][j])
                {
                    equal = false;
                }
            }
        }

        assertTrue(equal);
    }

    /**
     * Test of setConvergenceOutputReady method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testSetConvergenceOutputReady()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testSetConvergenceOutputReady()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        boolean convergenceOutputReady = false;

        instance.setConvergenceOutputReady(convergenceOutputReady);

        boolean result = instance.isConvergenceOutputReady();

        assertEquals(convergenceOutputReady, result);
    }

    /**
     * Test of setIterationCount method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testSetIterationCount()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testSetIterationCount()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        int expResult = 423420;
        instance.setIterationCount(expResult);
        int result = instance.getIterationCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIterationCountReady method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testSetIterationCountReady()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testSetIterationCountReady()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        boolean iterationCountReady = false;
        instance.setIterationCountReady(iterationCountReady);

        assertEquals(instance.isIterationCountReady(), iterationCountReady);
    }

    /**
     * Test of setSteadyStateFound method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testSetSteadyStateFound()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testSetSteadyStateFound()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);
        
        boolean steadyStateFound = false;
        instance.setSteadyStateFound(steadyStateFound);
        assertEquals(instance.isSteadyStateFound(), steadyStateFound);

        steadyStateFound = true;
        instance.setSteadyStateFound(steadyStateFound);
        assertEquals(instance.isSteadyStateFound(), steadyStateFound);
    }

    /**
     * Test of stateChanged method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testStateChanged()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testStateChanged()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);
        
        instance.stateChanged();

        assertFalse(instance.isConvergenceOutputReady());
        assertFalse(instance.isIterationCountReady());
        assertFalse(instance.isSteadyStateFound());
    }

    /**
     * Test of resetState method, of class ConvergenceAlgorithmModelState.
     */
    @Test
    public void testResetState()
    {
        System.out.println("* ConvergenceAlgorithmModelStateTest: testResetState()");

        ConvergenceSimulation model = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState instance = new ConvergenceAlgorithmModelState(model);

        instance.stateChanged();

        assertFalse(instance.isConvergenceOutputReady());
        assertFalse(instance.isIterationCountReady());
        assertFalse(instance.isSteadyStateFound());
    }
}
