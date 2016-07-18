package com.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.exception.ProductBuyInfoException;

public class ParseInputTest {
	// 读取txt文件，获取商店内部的商品信息
	private Map<String, Product> map = new HashMap<String, Product>();
	// 购买商品列表
	private ProductBuy productBuy = new ProductBuy();

	// 存储待打印的商品信息
	private Map<String, Product> mapProductBuy = new HashMap<String, Product>();

	@Test
	public void json_parse_to_product_with_one_item_no_count() throws Exception {
		String infoProductBuy = "['ITEM000001']";
		// 根据输入字符串，拆分去掉首尾[] , 得到需打印的商品条形码信息
		String newProductBuy = infoProductBuy.trim();
		Integer index = newProductBuy.indexOf('[');
		if (index < 0) {
			throw new ProductBuyInfoException();
		}
		index = newProductBuy.indexOf(']');
		if (index < 0) {
			throw new ProductBuyInfoException();
		}
		newProductBuy = newProductBuy.substring(1, newProductBuy.length() - 1).trim();
		// 验证是否存在该条形码对应的商品
		Assert.assertNull(map.get(newProductBuy));
	}

}
