/**
 * 
 */
package com.debug.checkout.dataobjects;

import java.util.Date;
import java.util.List;

/**
 * @author Deepak
 *
 */
public class Sale {
	
	private int itemCount;
	private List<Product> products;
	private int billAmount;
	private Date saleDate;
	
	/**
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}
	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	/**
	 * @return the billAmount
	 */
	public int getBillAmount() {
		return billAmount;
	}
	/**
	 * @param billAmount the billAmount to set
	 */
	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}
	/**
	 * @return the saleDate
	 */
	public Date getSaleDate() {
		return saleDate;
	}
	/**
	 * @param saleDate the saleDate to set
	 */
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	
}
