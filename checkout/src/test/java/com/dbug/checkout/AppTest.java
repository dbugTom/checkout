package com.dbug.checkout;

import java.util.Calendar;
import java.util.Properties;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;

import com.dbug.checkout.service.SupermarketService;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/application-test-context.xml")

public class AppTest{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	@Autowired
	Properties productDetails;
	
	@Autowired
	SupermarketService supermarketService;
    public AppTest()
    {
       
    }

    /**
     * @return the suite of tests being tested
     */
    @Test
    public void testForRegularPrice() throws Exception
    {
        String items = "AAABBBBCC";
        System.out.println("Bill Amount for " + items + " = " +supermarketService.checkOut(items));
    }
    @Test
    public void testForProductBCount5Random() throws Exception
    {
        String items = "ABBACBBAB";
        System.out.println("Bill Amount for " + items + " = " +supermarketService.checkOut(items));
    }
    @Test
    public void testForProductBCount5Sequential() throws Exception
    {
        String items = "ABBBBBACA";
        System.out.println("Bill Amount for " + items + " = " +supermarketService.checkOut(items));
    }
}
