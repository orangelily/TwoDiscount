package com.model;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.main.Calculate;

public class ProductManagerTest {
	@Test
	public void readProductList_have_ITEM000000_test() {
		ProductManager productManager = new ProductManager();
		Product product = new Product("ITEM000000", "可口可乐", "瓶", "食品", "碳酸饮料", 3.00);
		boolean result = product.equals(productManager.get("ITEM000000"));
		Assert.assertTrue(result);
	}

	@Test
	public void readProductList_have_ITEM000001_test() {
		ProductManager productManager = new ProductManager();
		Product product = new Product("ITEM000001", "雪碧", "瓶", "食品", "碳酸饮料", 3.00);
		boolean result = product.equals(productManager.get("ITEM000001"));
		Assert.assertTrue(result);
	}

	@Test
	public void readProductList_not_have_ITEM000001_test() {
		ProductManager productManager = new ProductManager();
		Product product = new Product("ITEM000001", "雪碧", "瓶", "食品", "碳酸饮料", 3.00);
		boolean result = product.equals(productManager.get("ITEM00000"));
		Assert.assertFalse(result);
	}
	
}
