/**
 * 
 */
package com.debug.checkout.dataobjects;

import java.util.List;

/**
 * @author Deepak
 *
 */
public class Rule {

	private List<String> params;
	private String ruleName;

	/**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * @return the params
	 */
	public List<String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(List<String> params) {
		this.params = params;
	}

	
	
}
