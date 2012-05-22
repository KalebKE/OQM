package oqm.algorithm.model.math;

import oqm.exceptions.NegativeNumberException;
import oqm.exceptions.NonSquareMatrixException;
import oqm.exceptions.ValueToLargeException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test for the Proportionality Function. The Proportionality Function
 * addes a feedback loop to the identity matrix of the System. The feedback is then
 * proportionatly subtracted from the other transfer probabilities so they are
 * still valid. System closure is also maintained. This unit test verifes
 * that the Proportionality Function adds the feedback loop to the indentity matrix,
 * proportionalizes the transfer probabilities, and retains closure.
 * @author Kaleb
 */
public class ProportionalityFunctionTest
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
    // Invalid Negative Number
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
    // Invalid Matrix Size
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
    // Invalid Probability Range
    private double[][] matrix5 =
    {
        {
            0, 2, 0, 0
        },
        {
            0.5, 0, 0.45, 0.05
        },
        {
            0.333, 0.333, 0.333, 0
        },
        {
            0.75, 0, 0.25, 0
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
     * Test of testTransferProbabilities method, of class ProportionalityFunction.
     * Validates the Transition Probabilities sum to 1 after the Proportionality
     * Function has added feedback to the System.
     */
    @Test
    public void testTransferProbabilities()
    {
        System.out.println("* ProportionalityFunctionTest: testTransferProbabilities()");

        // A System that is modified by the Proprotionality function should
        // have Valid Transition Probabilities.
        for (int i = 0; i < 1000; i++)
        {
            // Test Valid Systems for Valid Transition Probabilities.
            assertTrue(ValidateProbabilities.execute(ProportionalityFunction.proportionalizeMatrix(matrix0, ((double) i) / 1000)));
            assertTrue(ValidateProbabilities.execute(ProportionalityFunction.proportionalizeMatrix(matrix1, ((double) i) / 1000)));
            assertTrue(ValidateProbabilities.execute(ProportionalityFunction.proportionalizeMatrix(matrix2, ((double) i) / 1000)));
        }
    }

    /**
     * Test of testTransferProbabilities method, of class ProportionalityFunction.
     * Validates that the System still has closure after the Proportionality
     * Function has added feedback to the System.
     */
    @Test
    public void testClosure()
    {
        System.out.println("* ProportionalityFunctionTest: testTransferProbabilities()");

        // A System that is modified by the Proprotionality function should
        // have Warshall's Algorithm (Closure)
        for (int i = 0; i < 1000; i++)
        {
            // Test Valid Systems for Valid Closure.
            assertTrue(WarshallAlgorithm.execute(ProportionalityFunction.proportionalizeMatrix(matrix0, ((double) i) / 1000)));
            assertTrue(WarshallAlgorithm.execute(ProportionalityFunction.proportionalizeMatrix(matrix1, ((double) i) / 1000)));
            assertTrue(WarshallAlgorithm.execute(ProportionalityFunction.proportionalizeMatrix(matrix2, ((double) i) / 1000)));
        }
    }

    /**
     * Test of testProportionalizeMatrix0Boundaries method, of class ProportionalityFunction.
     * Validates that the Transition Probabilities fall within the boundaries of
     * > 0 AND < 1.
     */
    @Test
    public void testProportionalizeMatrix0Boundaries()
    {
        System.out.println("* ProportionalityFunctionTest: testProportionalizeMatrix0Boundaries()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries. 
        for (int i = 0; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix0, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than or equal to 0. 
                    if (pMatrix0[j][k] > 1 || pMatrix0[j][k] < 0)
                    {
                        valid = false;
                    }
                }
            }
            assertTrue(valid);
        }
    }

    /**
     * Test of testProportionalizeMatrix1Boundaries method, of class ProportionalityFunction.
     * * Validates that the Transition Probabilities fall within the boundaries of
     * > 0 AND < 1.
     */
    @Test
    public void testProportionalizeMatrix1Boundaries()
    {
        System.out.println("* ProportionalityFunctionTest: testProportionalizeMatrix1Boundaries()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries.
        for (int i = 0; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix1, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than or equal to 0.
                    if (pMatrix0[j][k] > 1 || pMatrix0[j][k] < 0)
                    {
                        valid = false;
                    }
                }
            }

            assertTrue(valid);
        }
    }

    /**
     * Test of testProportionalizeMatrix2Boundaries method, of class ProportionalityFunction.
     * * Validates that the Transition Probabilities fall within the boundaries of
     * > 0 AND < 1.
     */
    @Test
    public void testProportionalizeMatrix2Boundaries()
    {
        System.out.println("* ProportionalityFunctionTest: testProportionalizeMatrix2Boundaries()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries.
        for (int i = 0; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix2, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than or equal to 0.
                    if (pMatrix0[j][k] > 1 || pMatrix0[j][k] < 0)
                    {
                        valid = false;
                    }
                }
            }

            assertTrue(valid);
        }
    }

    /**
     * Test of testIndentityMatrixBoundaries method, of class ProportionalityFunction.
     * * Validates that the Transition Probabilities fall within the boundaries of
     * > 0 AND !=0 AND < 1.
     */
    @Test
    public void testIndentityMatrixBoundaries()
    {
        System.out.println("* ProportionalityFunctionTest: testIndentityMatrixBoundaries()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries.
        for (int i = 1; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix0, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than 0.
                    if (j == k)
                    {
                        if (pMatrix0[j][k] < 0 || pMatrix0[j][k] == 0 || pMatrix0[j][k] > 1)
                        {
                            System.out.println("Proprotionality Error:");
                            System.out.println("Feedback: " + ((double) i) / 1000);

                            this.mprint(pMatrix0);
                            valid = false;
                        }
                    }
                }
            }

            assertTrue(valid);
        }
    }

    /**
     * Test of testIndentityMatrix1Boundaries method, of class ProportionalityFunction.
     * * Validates that the Transition Probabilities fall within the boundaries of
     * > 0 AND !=0 AND < 1.
     */
    @Test
    public void testIndentityMatrix1Boundaries()
    {
        System.out.println("* ProportionalityFunctionTest: testIndentityMatrix1Boundaries()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries.
        for (int i = 1; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix1, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than 0.
                    if (j == k)
                    {
                        if (pMatrix0[j][k] < 0 || pMatrix0[j][k] == 0 || pMatrix0[j][k] > 1)
                        {
                            System.out.println("Proprotionality Error:");
                            System.out.println("Feedback: " + ((double) i) / 1000);

                            this.mprint(pMatrix0);
                            valid = false;
                        }
                    }
                }
            }

            assertTrue(valid);
        }
    }

    /**
     * Test of testIndentityMatrix2Boundaries method, of class ProportionalityFunction.
     * * Validates that the Transition Probabilities fall within the boundaries of
     * > 0 AND !=0 AND < 1.
     */
    @Test
    public void testIndentityMatrix2Boundaries()
    {
        System.out.println("* ProportionalityFunctionTest: testIndentityMatrix2Boundaries()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries.
        for (int i = 1; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix2, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than 0.
                    if (j == k)
                    {
                        if (pMatrix0[j][k] < 0 || pMatrix0[j][k] == 0 || pMatrix0[j][k] > 1)
                        {
                            System.out.println("Proprotionality Error:");
                            System.out.println("Feedback: " + ((double) i) / 1000);

                            this.mprint(pMatrix0);
                            valid = false;
                        }
                    }
                }
            }

            assertTrue(valid);
        }
    }

    /**
     * Test of testIndentityMatrixFeedback method, of class ProportionalityFunction.
     * Validates that identity matrix has the specified feedback.
     */
    @Test
    public void testIndentityMatrixFeedback()
    {
        System.out.println("* ProportionalityFunctionTest: testIndentityMatrixFeedback()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries.
        for (int i = 1; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix0, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than 0.
                    if (j == k)
                    {
                        if (pMatrix0[j][k] != ((double) i) / 1000)
                        {
                            System.out.println("Proprotionality Error:");
                            System.out.println("Feedback: " + ((double) i) / 1000);

                            this.mprint(pMatrix0);
                            valid = false;
                        }
                    }
                }
            }

            assertTrue(valid);
        }
    }

    /**
     * Test of testIndentityMatrix1Feedback method, of class ProportionalityFunction.
     * Validates that identity matrix has the specified feedback.
     */
    @Test
    public void testIndentityMatrix1Feedback()
    {
        System.out.println("* ProportionalityFunctionTest: testIndentityMatrix1Feedback()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries.
        for (int i = 1; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix1, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than 0.
                    if (j == k)
                    {
                        if (pMatrix0[j][k] != ((double) i) / 1000)
                        {
                            System.out.println("Proprotionality Error:");
                            System.out.println("Feedback: " + ((double) i) / 1000);

                            this.mprint(pMatrix0);
                            valid = false;
                        }
                    }
                }
            }

            assertTrue(valid);
        }
    }

    /**
     * Test of testIndentityMatrix1Feedback method, of class ProportionalityFunction.
     * Validates that identity matrix has the specified feedback.
     */
    @Test
    public void testIndentityMatrix2Feedback()
    {
        System.out.println("* ProportionalityFunctionTest: testIndentityMatrix2Feedback()");

        // Ensure that all of the values from the Proportional System
        // have valid boundaries.
        for (int i = 1; i < 1000; i++)
        {
            double[][] pMatrix0 = ProportionalityFunction.proportionalizeMatrix(matrix2, ((double) i) / 1000);

            boolean valid = true;

            for (int j = 0; j < pMatrix0.length; j++)
            {
                for (int k = 0; k < pMatrix0[j].length; k++)
                {
                    // The value must be less than or equal to 1 AND
                    // greater than 0.
                    if (j == k)
                    {
                        if (pMatrix0[j][k] != ((double) i) / 1000)
                        {
                            System.out.println("Proprotionality Error:");
                            System.out.println("Feedback: " + ((double) i) / 1000);

                            this.mprint(pMatrix0);
                            valid = false;
                        }
                    }
                }
            }

            assertTrue(valid);
        }
    }

    /**
     * Test of Negative Number Exception.
     */
    @Test(expected = NegativeNumberException.class)
    public void checkNegativeNumberException()
    {
        System.out.println("* ProportionalityFunctionTest: checkExpectedException()");
        ProportionalityFunction.proportionalizeMatrix(matrix3, 1 / 1000);

    }

    /**
     * Test of Non Square Matrix Exception.
     */
    @Test(expected = NonSquareMatrixException.class)
    public void checkNonSquareMatrixException()
    {
        System.out.println("* ProportionalityFunctionTest: checkNonSquareMatrixException()");
        ProportionalityFunction.proportionalizeMatrix(matrix4, 1 / 1000);

    }

    /**
     * Test of Non Square Matrix Exception.
     */
    @Test(expected = ValueToLargeException.class)
    public void checkValueToLargeException()
    {
        System.out.println("* ProportionalityFunctionTest: checkNonSquareMatrixException()");
        ProportionalityFunction.proportionalizeMatrix(matrix5, 1 / 1000);
    }

    /**
     * Matrix print.
     * @param a
     */
    private void mprint(double[][] a)
    {
        int rows = a.length;
        int cols = a[0].length;
        System.out.println("array[" + rows + "][" + cols + "] = {");
        for (int i = 0; i < rows; i++)
        {
            System.out.print("{");
            for (int j = 0; j < cols; j++)
            {
                System.out.print(" " + a[i][j] + ",");
            }
            System.out.println("},");
        }
        System.out.println(":;");
    }
}
