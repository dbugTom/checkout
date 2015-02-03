package com.dbug.checkout.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.springframework.util.StringUtils;

import com.dbug.checkout.constants.SupermarketConstants;
import com.dbug.checkout.exception.ServiceRuntimeException;
import com.debug.checkout.dataobjects.Offers;
import com.debug.checkout.dataobjects.Product;
import com.debug.checkout.dataobjects.Rule;
import com.debug.checkout.dataobjects.Sale;

/**
 * Implementation class of Supermarket
 * 
 * @author Deepak Thomas
 * @see Supermarket
 */
public class SupermarketService implements Supermarket{
	
	
	private Properties productDetails;

	
	
	/*(non-Javadoc)
	 * implements a cash check out register in a supermarket
	 * @param items 
	 * @throws ServiceRuntimeException
	 * @see com.dbug.checkout.service.Supermarket#checkOut(java.lang.String)
	 */
	public int checkOut(String items) throws ServiceRuntimeException{

		List<String> productList = Arrays.asList(productDetails.getProperty(SupermarketConstants.PRODUCT_LIST).split(SupermarketConstants.LIST_DELIMITER));
		List<Product> products = countProducts(items,productList);
		if(products.size()<1)
			throw new ServiceRuntimeException("Invalid product");
		Sale sale = loadOffers(products);
		return sale.getBillAmount();
	}

	/**
	 * parses the list of products from items against productlist 
	 * @param items
	 * @param  productList
	 *  @see com.dbug.checkout.service.Supermarket#checkOut(java.lang.String)
	 */
	private List<Product> countProducts(String items, List<String> productList){
		List<Product> products = new ArrayList<Product>();
		for(String productName : productList){
			Product product = new Product();
			int productCount = StringUtils.countOccurrencesOf(items,productName);
			if(productCount > 0){
				product.setUnitPrice(Integer.parseInt(productDetails.getProperty(productName + SupermarketConstants.UNIT_PRICE)));
				product.setCount(productCount);
				product.setProductName(productName);
				products.add(product);
			}
		}
		return products;
	}
	
	
	/**
	 * loads the offers applicable for <code>products</code> from properties
	 * Compare changes between <code>inputAttributeMap</code> and <code>dbAttributeMap</code>
	 * @param products - {@link List<Product>}
	 */
	private Sale loadOffers(List<Product> products){
		List<Product> regularProducts = new ArrayList<Product>();
		List<Offers> offers = new ArrayList<Offers>();
		for(Product product : products){
			Offers offer = new Offers();
			List<Rule> rules = new ArrayList<Rule>();
			String offerList = productDetails.getProperty(product.getProductName() + SupermarketConstants.OFFERS);
			List<String> ruleNames = (offerList == null) ?new ArrayList<String>() :Arrays.asList( offerList.split(SupermarketConstants.LIST_DELIMITER));
			Iterator<String> ruleIterator = ruleNames.iterator();
			while(ruleIterator.hasNext()){
				Rule rule = new Rule();
				String ruleName = ruleIterator.next();
				rule.setRuleName(ruleName);
				System.out.println(ruleName);
				rule.setParams(Arrays.asList(productDetails.getProperty(product.getProductName()+SupermarketConstants.KEY_DELIMITER+ruleName).split(SupermarketConstants.LIST_DELIMITER)));
				rules.add(rule);
			
			}
			if(rules.size()>0){
				offer.setRules(rules);
				offer.setProduct(product);
				offers.add(offer);
			} else{
				regularProducts.add(product);
			}
		}
		Sale sale = new Sale();
		sale = evaluateOffers(offers);
		for(Product regularProduct : regularProducts){
			evaluatRegularPrice(regularProduct,sale);
		}

			return sale;
	}

	/**
	 * @param offers
	 * @return
	 */
	private Sale evaluateOffers(List<Offers> offers){
		Sale sale = new Sale();
		if(offers.size() < 1) {
			
			return null;
		}
		for(Offers offer : offers){
			Product product = offer.getProduct();
			for(Rule rule: offer.getRules()){
				int balanceCount=0;
				if(rule.getRuleName() != null && rule.getRuleName().equals(SupermarketConstants.RULE1)){
					balanceCount = evaluateMforN(product,rule.getParams(),sale);
					
				} else if (rule.getRuleName() != null && rule.getRuleName().equals(SupermarketConstants.RULE2)){
					balanceCount = evaluateForPercentage(product,rule.getParams(),sale);
				} 
				if(balanceCount < 0){
					balanceCount = evaluatRegularPrice(product, sale);
				}
				if(balanceCount > 0){
					Product balProduct = new Product();
					balProduct.setCount(balanceCount);
					balProduct.setProductName(product.getProductName());
					balProduct.setUnitPrice(product.getUnitPrice());
					List<Offers> balOffers = new ArrayList<Offers>();
					//balOffers.get
					balOffers.addAll(offers);
					for(Offers modifiedOffer : balOffers){
						modifiedOffer.setProduct(balProduct);
					}
					int billAmount = sale.getBillAmount();
					sale = evaluateOffers(balOffers);
					sale.setBillAmount(billAmount + sale.getBillAmount());
				} 
			}
		}
		sale.setSaleDate(new Date());
		return sale;
	}
	
	/**
	 * evaluates a <code>product</code> against a list <code>rule.params</code> and updates the <code>sale</code> object <br>
	 * @param product
	 * @param params
	 * @param sale
	 * @return
	 * @throws NumberFormatException
	 */
	private int evaluateMforN(Product product, List<String> params, Sale sale) throws NumberFormatException{
			int price = 0;
			int balanceCount =-1;
			if(product.getCount() >= Integer.parseInt(params.get(0))){
				price = (product.getUnitPrice() * Integer.parseInt(params.get(1))); 
				if((product.getCount()/Integer.parseInt(params.get(0))) > 0){
					price = price * (product.getCount()/Integer.parseInt(params.get(0)));
					if(sale.getBillAmount() >= 0){
						sale.setBillAmount(sale.getBillAmount() + price);
					} else {
						sale.setBillAmount(price);;
					}
					sale.setItemCount(product.getCount());
				}
				balanceCount = product.getCount() % Integer.parseInt(params.get(0));
			}
			return balanceCount;
		}
	

	
	/**
	 * Evaluate the product for <code>rule2</code> in properties. The rule calculated based on below logic:
	 * <ul>
	 * <li>if <code>productCount > param[0]</code> </li>
	 * <li> 	apply percentage <code>param[1]</code> on the product</li>
	 * @param product
	 * @param params
	 * @param sale
	 * @return
	 * @throws NumberFormatException
	 */
	private int evaluateForPercentage(Product product, List<String> params, Sale sale) throws NumberFormatException{
		int offerPercentage = Integer.parseInt(params.get(1))/100;
		sale.setBillAmount(product.getCount() * (product.getUnitPrice() - (product.getUnitPrice()*offerPercentage)));
		return 0;
		
	}




	/**
	 * evaluate <code>product</code> for regular unit price
	 * @param product
	 * @param sale
	 * @return
	 */
	private int evaluatRegularPrice(Product product,Sale sale){
		sale.setBillAmount(sale.getBillAmount() + product.getCount() * product.getUnitPrice());
		return 0;
	}
	/**
	 * @return the productDetails
	 */
	public Properties getProductDetails() {
		return productDetails;
	}


	/**
	 * @param productDetails the productDetails to set
	 */
	public void setProductDetails(Properties productDetails) {
		this.productDetails = productDetails;
	}

	
	
	
	
}
