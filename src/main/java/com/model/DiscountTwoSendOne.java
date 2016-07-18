package com.model;

import java.text.DecimalFormat;
import java.util.List;

public class DiscountTwoSendOne extends Discount {

	public DiscountTwoSendOne() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscountTwoSendOne(List<Product> discountList) {
		super(discountList);
		// TODO Auto-generated constructor stub
	}

	public double calculateDiscount(Product product, double number) {
		double result = 0.0;
		if (number >= 3.0) {
			int sendNumber = (int) number / 3;
			result = product.getPrice() * sendNumber;
		}
		DecimalFormat df = new DecimalFormat("######0.00"); 
		df.format(result);
		return result;
	}
}
