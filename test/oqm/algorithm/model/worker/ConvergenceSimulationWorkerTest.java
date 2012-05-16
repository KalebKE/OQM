
package oqm.algorithm.model.worker;

import oqm.algorithm.model.ConvergenceSimulation;
import oqm.algorithm.model.state.ConvergenceAlgorithmModelState;
import oqm.algorithm.model.state.ConvergenceInputModelState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit Test for Convergence Simulation Worker.
 * @author Kaleb
 */
public class ConvergenceSimulationWorkerTest
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

    public ConvergenceSimulationWorkerTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* ConvergenceSimulationWorkerTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* ConvergenceSimulationWorkerTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* ConvergenceSimulationWorkerTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* ConvergenceSimulationWorkerTest: @After method");
    }

    /**
     * Test of calculateConvergence method, of class ConvergenceSimulationWorker.
     */
    @Test
    public void testCalculateConvergence()
    {
        System.out.println("* ConvergenceSimulationWorkerTest: testCalculateConvergence()");

        ConvergenceSimulation convergenceSimulation = new ConvergenceSimulation();
        ConvergenceAlgorithmModelState convergenceModelState = new ConvergenceAlgorithmModelState(convergenceSimulation);
        ConvergenceInputModelState inputModelState = new ConvergenceInputModelState();
        ConvergenceSimulationWorker simulationWorker = new ConvergenceSimulationWorker(convergenceModelState, inputModelState);

        inputModelState.setSystemInputModel(matrix0);
        // Force same thread execution so we can use assert.
        simulationWorker.sameThreadExecution();
        simulationWorker.execute();

        if (convergenceModelState.getIterationCount() > 0)
        {
            assertTrue(true);
        } else
        {
            assertTrue(false);
        }

        if (convergenceModelState.getConvergenceMatrix().length == convergenceModelState.getConvergenceMatrix()[0].length)
        {
            assertTrue(true);
        } else
        {
            assertTrue(false);
        }
    }
}
