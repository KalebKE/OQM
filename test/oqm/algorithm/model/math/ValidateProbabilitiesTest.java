
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
 * JUnit test for the Validate Probabilities function.
 * @author Kaleb
 */
public class ValidateProbabilitiesTest
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
    double[][] matrix3 =
    {
        {
            0, 1.2, 0
        },
        {
            0.5, 0, 0.5
        },
        {
            0.5, 0.5, 0
        }
    };
    private double[][] matrix4 =
    {
        {
            0, 1, 0, 0
        },
        {
            0.5, 0, 0.52, 0
        },
        {
            0.25, 0.25, 0, 0.5
        },
        {
            0.75, 0, 0.25, 0
        }
    };
    private double[][] matrix5 =
    {
        {
            0, 1, 0, 0
        },
        {
            0.5, 0, 0.5, 0
        },
        {
            0.343, 0.333, 0, 0.333
        },
        {
            0.75, 0, 0.25, 0
        }
    };
    private double[][] matrix6 =
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
    private double[][] matrix7 =
    {
        {
            0, 0, 0, 1, 0
        },
        {
            0, 0, 0, 1, 0
        },
        {
            0, 0, 0, 1, 0
        },
        {
            0, 0, 0, 1, 0
        }
    };

    public ValidateProbabilitiesTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* ValidateProbabilitiesTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* ValidateProbabilitiesTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* ValidateProbabilitiesTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* ValidateProbabilitiesTest: @After method");
    }

    /**
     * Test of validateProbabilities method, of class ValidateProbabilities.
     */
    @Test
    public void testExecute()
    {
        System.out.println("* ValidateProbabilitiesTest: testValidateProbabilities()");

        assertTrue(ValidateProbabilities.execute(matrix0));
        assertTrue(ValidateProbabilities.execute(matrix1));
        assertTrue(ValidateProbabilities.execute(matrix2));

        assertFalse(ValidateProbabilities.execute(matrix3));
        assertFalse(ValidateProbabilities.execute(matrix4));
        assertFalse(ValidateProbabilities.execute(matrix5));
    }

    /**
     * Test of Negative Number Exception.
     */
    @Test(expected = NegativeNumberException.class)
    public void checkNegativeNumberException()
    {
        System.out.println("* ValidateProbabilitiesTest: checkExpectedException()");
        ValidateProbabilities.execute(matrix6);
    }

    /**
     * Test of Non Square Matrix Exception.
     */
    @Test(expected = NonSquareMatrixException.class)
    public void checkNonSquareMatrixException()
    {
        System.out.println("* ValidateProbabilitiesTest: checkNonSquareMatrixException()");
        ValidateProbabilities.execute(matrix7);
    }
}
