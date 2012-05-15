/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.algorithm.model;

import oqm.algorithm.model.observer.ConvergenceAlgorithmModelObserver;
import oqm.algorithm.model.observer.IterationAlgorithmModelObserver;
import oqm.algorithm.model.observer.SteadyStateAlgorithmModelObserver;
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
public class ConvergenceSimulationTest
{

    boolean converged = false;
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

    public ConvergenceSimulationTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* ConvergenceSimulationTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* ConvergenceSimulationTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* ConvergenceSimulationTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* ConvergenceSimulationTest: @After method");
    }

    /**
     * Test of execute method, of class ConvergenceSimulation.
     */
    @Test
    public void testConvergenceObserver()
    {
        System.out.println("* ConvergenceSimulationTest: testConvergenceObserver()");

        ConvergenceSimulation convergenceSimulation = new ConvergenceSimulation();

        for (int i = 0; i < 1000; i++)
        {
            ((ConvergenceAlgorithmModelInterface) convergenceSimulation).registerObserver((ConvergenceAlgorithmModelObserver) this);
        }

        assertEquals(convergenceSimulation.getNumCovergenceObservers(), 1000);
    }

    /**
     * Test of execute method, of class ConvergenceSimulation.
     */
    @Test
    public void testIterationObserver()
    {
        System.out.println("* ConvergenceSimulationTest: testIterationObserver()");

        ConvergenceSimulation convergenceSimulation = new ConvergenceSimulation();

        for (int i = 0; i < 1000; i++)
        {
            ((IterationAlgorithmModelInterface) convergenceSimulation).registerObserver((IterationAlgorithmModelObserver) this);
        }

        assertEquals(convergenceSimulation.getNumIterationObservers(), 1000);
    }

    /**
     * Test of execute method, of class ConvergenceSimulation.
     */
    @Test
    public void testSteadyStateObserver()
    {
        System.out.println("* ConvergenceSimulationTest: testSteadyStateObserver()");

        ConvergenceSimulation convergenceSimulation = new ConvergenceSimulation();

        for (int i = 0; i < 1000; i++)
        {
            ((SteadyStateAlgorithmModelInterface) convergenceSimulation).registerObserver((SteadyStateAlgorithmModelObserver) this);
        }

        assertEquals(convergenceSimulation.getNumSteadyStateObservers(), 1000);
    } 
}
