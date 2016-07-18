package com.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class DiscountNinetyFiveTest {

	@Test
	public void isDiscountTest() {
		List<Product> discountList = new ArrayList<Product>();
		Product product1 = new Product("Item000001", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		Product product2 = new Product("Item000002", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		Product product3 = new Product("Item000001", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		discountList.add(product1);
		discountList.add(product2);

		Discount discount = new  DiscountNinetyFive(discountList);
		boolean result1 = discount.isDiscount(product1);
		Assert.assertTrue(result1);

		boolean result2 = discount.isDiscount(product2);
		Assert.assertTrue(result2);

		boolean result3 = discount.isDiscount(product3);
		Assert.assertFalse(result3);
	}
	@Test
	public void addItemTest() {
		Discount discount = new DiscountNinetyFive();
		Product product1 = new Product("Item000001", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		discount.addItem(product1);
		Assert.assertTrue(discount.isDiscount(product1));

		Product product2 = new Product("Item000002", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		discount.addItem(product2);
		Assert.assertTrue(discount.isDiscount(product2));

		Product product3 = new Product("Item000003", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		Assert.assertFalse(discount.isDiscount(product3));
	}
	@Test
	public void removeItemTest() {
		List<Product> discountList = new ArrayList<Product>();
		Product product1 = new Product("Item000001", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		Product product2 = new Product("Item000002", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		Product product3 = new Product("Item000003", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		discountList.add(product1);
		discountList.add(product2);
		discountList.add(product3);
		Discount discount = new DiscountNinetyFive(discountList);

		discount.removeItem(product1);
		Assert.assertFalse(discount.isDiscount(product1));

		discount.removeItem(product2);
		Assert.assertFalse(discount.isDiscount(product2));

		discount.removeItem(product2);
		Assert.assertTrue(discount.isDiscount(product3));
	}
	@Test
	public void getAllTest() {
		List<Product> discountList = new ArrayList<Product>();
		Product product1 = new Product("Item000001", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		Product product2 = new Product("Item000002", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		Product product3 = new Product("Item000003", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		discountList.add(product1);
		discountList.add(product2);
		discountList.add(product3);
		Discount discount = new DiscountNinetyFive(discountList);
		Assert.assertTrue(discountList == discount.getAll());
	}
	
	@Test
	public void calculateDiscountTest(){
		Discount discount = new DiscountNinetyFive();
		Product product1 = new Product("Item000001", "可乐", "瓶", " 饮料", "碳酸饮料", 5.00);
		Product product2 = new Product("Item000002", "羽毛球", "个", " 用品", "运动器材", 1.00);
		Product product3 = new Product("Item000003", "苹果", "斤", "食品", "水果", 5.50);
		discount.addItem(product1);
		discount.addItem(product2);
		discount.addItem(product3);
		
		double result1 = discount.calculateDiscount(product1, 2);
		Assert.assertTrue(Math.abs(0.50 - result1)<0.000001);
		
		double result2 = discount.calculateDiscount(product2, 6);
		Assert.assertTrue(Math.abs(0.30 - result2)<0.000001);
		
		double result3 = discount.calculateDiscount(product3, 2);
		Assert.assertTrue(Math.abs(0.55 - result3)<0.000001);
	}
	
}
