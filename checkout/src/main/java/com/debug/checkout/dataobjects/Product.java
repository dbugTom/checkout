/**
 * 
 */
package com.debug.checkout.dataobjects;

/**
 * @author Deepak
 *
 */
public class Product {
	
	private String productName;
	private String productDescription;
	private int unitPrice;
	private int count;
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	/**
	 * @return the unitPrice
	 */
	public int getUnitPrice() {
		return unitPrice;
	}
	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	public String toString(){
		return "ProductName = " + this.productName + ", productCount= " + this.count + ", unitPrice=" + this.unitPrice;
	}

}
