/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Kaleb
 */
public class WarshallAlgorithmTest
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
            1, 0, 0
        },
        {
            0, 1, 0
        },
        {
            0, 1, 0
        }
    };
    private double[][] matrix4 =
    {
        {
            1, 0, 0, 0
        },
        {
            0, 1, 0, 0
        },
        {
            0, 0, 1, 0
        },
        {
            0, 0, 0, 1
        }
    };
    private double[][] matrix5 =
    {
        {
            1, 0, 0, 0
        },
        {
            1, 0, 0, 0
        },
        {
            1, 0, 0, 0
        },
        {
            1, 0, 0, 0
        }
    };
    private double[][] matrix6 =
    {
        {
            0, 1, 0, 0
        },
        {
            0, 1, 0, 0
        },
        {
            0, 1, 0, 0
        },
        {
            0, 1, 0, 0
        }
    };
    private double[][] matrix7 =
    {
        {
            0, 0, 1, 0
        },
        {
            0, 0, 1, 0
        },
        {
            0, 0, 1, 0
        },
        {
            0, 0, 1, 0
        }
    };
    
    private double[][] matrix8 =
    {
        {
            0, 0, 0, 1
        },
        {
            0, 0, 0, 1
        },
        {
            0, 0, 0, 1
        },
        {
            0, 0, 0, 1
        }
    };
    private double[][] matrix9 =
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

    private double[][] matrixA =
    {
        {
            0, 0, 0, -1
        },
        {
            0, 0, 0, 1
        },
        {
            0, 0, 0, 1
        },
        {
            0, 0, 0, 1
        }
    };

    public WarshallAlgorithmTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* WarshallAlgorithmTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* WarshallAlgorithmTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* WarshallAlgorithmTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* WarshallAlgorithmTest: @After method");
    }

    /**
     * Test of run method, of class WarshallAlgorithm.
     */
    @Test
    public void testExecute()
    {
        System.out.println("* WarshallAlgorithmTest: testExecute()");

        assertTrue(WarshallAlgorithm.execute(matrix0));
        assertTrue(WarshallAlgorithm.execute(matrix1));
        assertTrue(WarshallAlgorithm.execute(matrix2));

        assertFalse(WarshallAlgorithm.execute(matrix3));
        assertFalse(WarshallAlgorithm.execute(matrix4));
        assertFalse(WarshallAlgorithm.execute(matrix5));
        assertFalse(WarshallAlgorithm.execute(matrix6));
        assertFalse(WarshallAlgorithm.execute(matrix7));
        assertFalse(WarshallAlgorithm.execute(matrix8));
    }

    /**
     * Test of Negative Number Exception.
     */
    @Test(expected = NonSquareMatrixException.class)
    public void checkNonSqaureMatrixException()
    {
        System.out.println("* WarshallAlgorithmTest: checkNonSqaureMatrixException()");
        WarshallAlgorithm.execute(matrix9);
    }

    /**
     * Test of Negative Number Exception.
     */
    @Test(expected = NegativeNumberException.class)
    public void checkNegativeNumberException()
    {
        System.out.println("* WarshallAlgorithmTest: checkNegativeNumberException()");
        WarshallAlgorithm.execute(matrixA);
    }
}
