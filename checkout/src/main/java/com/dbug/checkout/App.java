package com.dbug.checkout;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dbug.checkout.exception.ServiceRuntimeException;
import com.dbug.checkout.service.SupermarketService;

/**
 * Application Entry point
 *
 */
public class App 
{
	private static final int EXIT_ERROR = -1;
    /**
     * Application Entry point for checkout
     * @param args   - arguments
     * <code>java App --items AABBC</code>
     */
    public static void main( String[] args )
    {
		String argCmd = "";
		String items = null;
		for (String arg : args) {
			if ("--items".equals(argCmd)) {
				items = arg;
			}
			if (arg.startsWith("-")) {
				argCmd = arg;
			}
		}

		if (items == null) {
			throw new ServiceRuntimeException(
					"List of items purchased must be privided. --items <AABBCCDD>");
		}

		try {
			int i = 2%5;
			System.out.println(i);
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:spring/application-context.xml");

			SupermarketService service = (SupermarketService) context.getBean("supermarketService");
			if (service == null) {
				throw new ServiceRuntimeException(
						"Supermarket service misconfigured.");
			}
			System.out.println("Price for " + items + "purchased is " +service.checkOut(items));
		}catch(Exception ex){
			System.err.println("Error : " + ex);
			
			System.exit(EXIT_ERROR);
		}
    }
}
