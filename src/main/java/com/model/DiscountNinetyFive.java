package com.model;

import java.text.DecimalFormat;
import java.util.List;

public class DiscountNinetyFive extends Discount {

	
	public DiscountNinetyFive() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscountNinetyFive(List<Product> discountList) {
		super(discountList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculateDiscount(Product product, double number) {	  
		double result = 0.0;
		result = product.getPrice() * number * 0.05;
		DecimalFormat df = new DecimalFormat("######0.00"); 
		df.format(result);
		return result;
	}

}
