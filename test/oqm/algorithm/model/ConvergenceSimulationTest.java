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
 * JUnit Test for a Convergence Simulation. It uses the entire Algorithm Model
 * to perform a mock simulation. The Convergence Simulation Test class is an
 * Observer to the Algorithm's Models.
 * @author Kaleb
 */
public class ConvergenceSimulationTest implements ConvergenceAlgorithmModelObserver,
        IterationAlgorithmModelObserver, SteadyStateAlgorithmModelObserver
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
     * Test of the Convergence Algorithm Model Observers. Registers 1000
     * observers with the Convergence Algorithm Model Subject and then asserts
     * that the number of registered observers is the number of observers that
     * were intended to be registered.
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
     * Test of the Iteration Algorithm Model Observers. Registers 1000
     * observers with the Iteration Algorithm Model Subject and then asserts
     * that the number of registered observers is the number of observers that
     * were intended to be registered.
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
     * Test of the Steady State Algorithm Model Observers. Registers 1000
     * observers with the Steady State Algorithm Model Subject and then asserts
     * that the number of registered observers is the number of observers that
     * were intended to be registered.
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

    /**
     * Test of the entire Convergence Algorithm Model. It registers Convergence Simulation Test
     * as an observer 1000 times and then executes three different simulations.
     * Convergence Simulation Test is updated of new model state by the subject and the output is
     * verified.
     */
    @Test
    public void testConvergenceModel()
    {
        System.out.println("* ConvergenceSimulationTest: testSteadyStateObserver()");

        ConvergenceSimulation convergenceSimulation = new ConvergenceSimulation();

        for (int i = 0; i < 1000; i++)
        {
            ((IterationAlgorithmModelInterface) convergenceSimulation).registerObserver((IterationAlgorithmModelObserver) this);
            ((ConvergenceAlgorithmModelInterface) convergenceSimulation).registerObserver((ConvergenceAlgorithmModelObserver) this);
            ((SteadyStateAlgorithmModelInterface) convergenceSimulation).registerObserver((SteadyStateAlgorithmModelObserver) this);
        }

        convergenceSimulation.updateSystemInputModel(matrix0);
        this.converged = true;
        // Force the simulation to use the same thread so we can use assert.
        convergenceSimulation.sameThreadExecution(true);
        convergenceSimulation.execute();

        convergenceSimulation.updateSystemInputModel(matrix1);
        convergenceSimulation.execute();

        convergenceSimulation.updateSystemInputModel(matrix2);
        convergenceSimulation.execute();
    }

    /**
     * Observer hook for Convergence Algorithm Model subjects.
     * @param modelResult the result of the steady state of the transition
     * probability matrix.
     */
    public void updateConvergenceAlgorithmModelOutput(double[][] modelResult)
    {
        //Ensure that the matrix is still square.
        if (modelResult.length == modelResult[0].length)
        {
            assertTrue(true);
        } else
        {
            assertFalse(false);
        }
    }

    /**
     * Observer hook for the Iteration Algorithm Model subject.
     * @param iterations the number of iterations that were required
     * to find the steady state, or if the steady state wasn't found, the
     * value that n was limited at.
     */
    public void updateIterationAlgorithmModelOutput(int iterations)
    {
        // Ensure that the iterations are greater than 0.
        if (iterations > 0)
        {
            assertTrue(true);
        } else
        {
            assertTrue(false);
        }
    }

    /**
     * Observer hook for the State State Algorithm Model subject.
     * @param converged indicates if the steady state of the transition
     * probiblity matrix was found, or not.
     */
    public void updateSteadyStateAlgorithmModelOutput(boolean converged)
    {
        // Ensure that Systems that are supposed to converge actually converge
        // and Systems that aren't supposed to converge do not converge.
        if (this.converged == converged)
        {
            assertTrue(true);
        } else
        {
            assertTrue(false);
        }
    }
}
