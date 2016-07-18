package com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象的打折信息类，里面包含同一类打折的商品列表，
 * 增加一种打折方式时，继承此类，实现calculateDiscount方法，计算打折的优惠
 * @author yefengzhichen
 * 2016年7月17日
 */
public abstract class Discount {
	private List<Product> discountList = new ArrayList<Product>();

	public Discount() {

	}

	public Discount(List<Product> discountList) {
		this.discountList = discountList;
	}

	public boolean isDiscount(Product product) {
		int index = discountList.indexOf(product);
		return index != -1;
	}

	public void addItem(Product product) {
		discountList.add(product);
	}

	public void removeItem(Product product) {
		if (isDiscount(product)) {
			discountList.remove(product);
		}
	}

	public List<Product> getAll() {
		return discountList;
	}
	
	public abstract double calculateDiscount(Product product, double number);
}
