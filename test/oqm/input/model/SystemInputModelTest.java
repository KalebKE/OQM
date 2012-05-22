/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oqm.input.model;

import java.util.ArrayList;
import oqm.input.model.observer.SystemInputModelObserver;
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
public class SystemInputModelTest
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

    public SystemInputModelTest()
    {
        super();


    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        System.out.println("* SystemInputModelTest: @BeforeClass method");
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        System.out.println("* SystemInputModelTest: @AfterClass method");
    }

    @Before
    public void setUp()
    {
        System.out.println("* SystemInputModelTest: @Before method");
    }

    @After
    public void tearDown()
    {
        System.out.println("* SystemInputModelTest: @After method");
    }

    /**
     * Test of registerObserver method, of class SystemInputModel.
     */
    @Test
    public void testRegisterObserver()
    {
        System.out.println("* SystemInputModelTest: testRegisterObserver()");
        ArrayList<SystemInputModelObserver> observers = new ArrayList<SystemInputModelObserver>();
        SystemInputModel instance = new SystemInputModel();

        for (int i = 0; i < 1000; i++)
        {
            observers.add(new MockObserver());
        }

        for (int i = 0; i < observers.size(); i++)
        {
            instance.registerObserver(observers.get(i));
        }

        assertEquals(instance.getNumObservers(), observers.size());
    }

    /**
     * Test of removeObserver method, of class SystemInputModel.
     */
    @Test
    public void testRemoveObserver()
    {
        System.out.println("* SystemInputModelTest: testRemoveObserver()");
        ArrayList<SystemInputModelObserver> observers = new ArrayList<SystemInputModelObserver>();
        SystemInputModel instance = new SystemInputModel();

        for (int i = 0; i < 1000; i++)
        {
            observers.add(new MockObserver());
        }

        for (int i = 0; i < observers.size(); i++)
        {
            instance.registerObserver(observers.get(i));
        }

        for (int i = 0; i < observers.size(); i++)
        {
            instance.removeObserver(observers.get(i));
        }

        assertEquals(instance.getNumObservers(), 0);
    }

    /**
     * Test of notifyObservers method, of class SystemInputModel.
     */
    @Test
    public void testNotifyObservers()
    {
        System.out.println("* SystemInputModelTest: testNotifyObservers()");
        ArrayList<SystemInputModelObserver> observers = new ArrayList<SystemInputModelObserver>();
        SystemInputModel instance = new SystemInputModel();

        for (int i = 0; i < 1000; i++)
        {
            observers.add(new MockObserver());
        }

        for (int i = 0; i < observers.size(); i++)
        {
            instance.registerObserver(observers.get(i));
        }

        instance.setModelInput(matrix0);
    }

    // Mock Observer for notification tests.
    private class MockObserver implements SystemInputModelObserver
    {

        public MockObserver()
        {
            super();
        }

        public void updateSystemInputModel(double[][] modelInput)
        {
            boolean valid = true;

            // Make sure the System we sent to the input model
            // is the same as the System that the input model
            // pushes to observers.
            for (int i = 0; i < modelInput.length; i++)
            {
                for (int j = 0; j < modelInput[i].length; j++)
                {
                    if (modelInput[i][j] != matrix0[i][j])
                    {
                        valid = false;
                    }
                }
            }

            assertTrue(valid);
        }
    }
}
