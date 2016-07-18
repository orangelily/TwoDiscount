package com.model;

import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

	@Test
	public void barcodeTest() {
		Product product = new Product();
		product.setBarcode("Item1001");
		Assert.assertEquals("Item1001", product.getBarcode());

		product.setBarcode("Item1002");
		Assert.assertEquals("Item1002", product.getBarcode());

		product.setBarcode("Item1001");
		Assert.assertNotEquals("Item1002", product.getBarcode());
	}

	@Test
	public void nameTest() {
		Product product = new Product();
		product.setName("可乐");
		Assert.assertEquals("可乐", product.getName());

		product.setName("篮球");
		Assert.assertEquals("篮球", product.getName());

		product.setName("乒乓球");
		Assert.assertNotEquals("篮球", product.getName());
	}

	@Test
	public void unitTest() {
		Product product = new Product();
		product.setUnit("瓶");
		Assert.assertEquals("瓶", product.getUnit());
		
		product.setUnit("袋");
		Assert.assertEquals("袋", product.getUnit());
		
		product.setUnit("瓶");
		Assert.assertNotEquals("袋", product.getUnit());
	}

	@Test
	public void categoryTest() {
		Product product = new Product();
		product.setCategory("饮料");
		Assert.assertEquals("饮料", product.getCategory());
		
		product.setCategory("水果");
		Assert.assertEquals("水果", product.getCategory());
		
		product.setCategory("饮料");
		Assert.assertNotEquals("水果", product.getCategory());
	}

	@Test
	public void subCategoryTest() {
		Product product = new Product();
		product.setSubCategory("碳酸饮料");
		Assert.assertEquals("碳酸饮料", product.getSubCategory());
		
		product.setSubCategory("热带水果");
		Assert.assertEquals("热带水果", product.getSubCategory());
		
		product.setSubCategory("热带水果");
		Assert.assertNotEquals("碳酸饮料", product.getSubCategory());
	}

	@Test
	public void priceTest() {
		Product product = new Product();
		product.setPrice(5.00);
		Assert.assertTrue(5.00 == product.getPrice());

		product.setPrice(6.00);
		Assert.assertTrue(6.00 == product.getPrice());

		product.setPrice(5.00);
		Assert.assertFalse(6.00 == product.getPrice());
	}

}
