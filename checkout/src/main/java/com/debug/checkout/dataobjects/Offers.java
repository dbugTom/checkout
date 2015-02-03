/**
 * 
 */
package com.debug.checkout.dataobjects;

import java.util.List;

/**
 * @author Deepak
 *
 */
public class Offers {
	
	private List<Rule> rules;

	private Product product;

	/**
	 * @return the rules
	 */
	public List<Rule> getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	

}
