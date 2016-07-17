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
		product.setName("����");
		Assert.assertEquals("����", product.getName());

		product.setName("ѩ��");
		Assert.assertEquals("ѩ��", product.getName());

		product.setName("����");
		Assert.assertNotEquals("ѩ��", product.getName());
	}

	@Test
	public void unitTest() {
		Product product = new Product();
		product.setUnit("ƿ");
		Assert.assertEquals("ƿ", product.getUnit());

		product.setUnit("��");
		Assert.assertEquals("��", product.getUnit());

		product.setUnit("ƿ");
		Assert.assertNotEquals("��", product.getUnit());
	}

	@Test
	public void categoryTest() {
		Product product = new Product();
		product.setCategory("����");
		Assert.assertEquals("����", product.getCategory());

		product.setCategory("ˮ��");
		Assert.assertEquals("ˮ��", product.getCategory());

		product.setCategory("����");
		Assert.assertNotEquals("ˮ��", product.getCategory());
	}

	@Test
	public void subCategoryTest() {
		Product product = new Product();
		product.setSubCategory("̼������");
		Assert.assertEquals("̼������", product.getSubCategory());

		product.setSubCategory("�ȴ�ˮ��");
		Assert.assertEquals("�ȴ�ˮ��", product.getSubCategory());

		product.setSubCategory("�ȴ�ˮ��");
		Assert.assertNotEquals("̼������", product.getSubCategory());
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
