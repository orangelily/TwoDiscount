package com.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.exception.ProductBuyInfoException;
import com.process.ParseInput;
import com.process.ProcessDetail;

public class ParseInputTest {
	// 购买商品列表
	private ProductBuy productBuy = new ProductBuy();

	@Test
	public void json_parse_to_product_with_one_item_no_discount() throws Exception {
		//单个商品结算
		String infoProductBuy = "['ITEM000001']";
		ParseInput parseInput = new ParseInput();
		productBuy = parseInput.parseInput(infoProductBuy);
		ProcessDetail processDetail = new ProcessDetail();
		String printInfo = processDetail.calculatebuy(productBuy);
		
		Assert.assertNotNull(printInfo);
		System.out.println(printInfo);
	}

	@Test
	public void json_parse_to_product_with_list_same_item_no_count() throws Exception {
		//多个相同商品结算
		String infoProductBuy = "['ITEM000001','ITEM000001', 'ITEM000001']";
		
	}
	
	@Test
	public void json_parse_to_product_with_list_same_item_and_count() throws Exception {
		
	}
}
