
package oqm.algorithm.model.math;

import oqm.exceptions.NegativeNumberException;
import oqm.exceptions.NonSquareMatrixException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test for the Proportionality Function.
 * @author Kaleb
 */
public class ProportionalityFunctionTest
{

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
    private double[][] matrix3 =
    {
        {
            0, -1, 0, 0
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

    private double[][] matrix4 =
    {
        {
            0, 1, 0, 0, 0
        },
        {
            0.5, 0, 0.45, 0, 0.05
        },
        {
            0.333, 0.333, 0, 0.333, 0
        },
        {
            0.75, 0, 0.25, 0, 0
        }
    };

    public ProportionalityFunctionTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* ProportionalityFunctionTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* ProportionalityFunctionTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* ProportionalityFunctionTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* ProportionalityFunctionTest: @After method");
    }

    /**
     * Test of proportionalizeMatrix method, of class ProportionalityFunction.
     */
    @Test
    public void testProportionalizeMatrix()
    {
        System.out.println("* ProportionalityFunctionTest: testProportionalizeMatrix()");
        for (int i = 0; i < 1000; i++)
        {
            assertTrue(ValidateProbabilities.execute(ProportionalityFunction.proportionalizeMatrix(matrix0, i / 1000)));
            assertTrue(ValidateProbabilities.execute(ProportionalityFunction.proportionalizeMatrix(matrix1, i / 1000)));
            assertTrue(ValidateProbabilities.execute(ProportionalityFunction.proportionalizeMatrix(matrix2, i / 1000)));

            assertTrue(WarshallAlgorithm.execute(ProportionalityFunction.proportionalizeMatrix(matrix0, i / 1000)));
            assertTrue(WarshallAlgorithm.execute(ProportionalityFunction.proportionalizeMatrix(matrix1, i / 1000)));
            assertTrue(WarshallAlgorithm.execute(ProportionalityFunction.proportionalizeMatrix(matrix2, i / 1000)));
        }
    }

    /**
     * Test of Negative Number Exception.
     */
    @Test(expected = NegativeNumberException.class)
    public void checkNegativeNumberException()
    {
        System.out.println("* ProportionalityFunctionTest: checkExpectedException()");
        ValidateProbabilities.execute(matrix3);
    }

    /**
     * Test of Non Square Matrix Exception.
     */
    @Test(expected = NonSquareMatrixException.class)
    public void checkNonSquareMatrixException()
    {
        System.out.println("* ProportionalityFunctionTest: checkNonSquareMatrixException()");
        ValidateProbabilities.execute(matrix4);
    }
}
