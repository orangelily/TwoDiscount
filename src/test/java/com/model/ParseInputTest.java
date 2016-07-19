package com.model;

import org.junit.Assert;
import org.junit.Test;

import com.process.ParseInput;
import com.process.ProcessDetail;

public class ParseInputTest {
	// 购买商品列表
	private ProductBuy productBuy = new ProductBuy();

	@Test
	public void json_parse_to_product_with_one_item_no_discount() throws Exception {
		// 单个商品结算
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
		// 多个相同商品结算,不含优惠
		String infoProductBuy = "['ITEM000001','ITEM000001', 'ITEM000001']";
		ParseInput parseInput = new ParseInput();
		productBuy = parseInput.parseInput(infoProductBuy);
		ProcessDetail processDetail = new ProcessDetail();
		String printInfo = processDetail.calculatebuy(productBuy);

		Assert.assertNotNull(printInfo);
		System.out.println(printInfo);
	}

	@Test
	public void json_parse_to_product_with_list_diff_item_no_count() throws Exception {
		// 多个不同商品结算，不含优惠
		String infoProductBuy = "['ITEM000001','ITEM000004', 'ITEM000001', 'ITEM000004']";
		ParseInput parseInput = new ParseInput();
		productBuy = parseInput.parseInput(infoProductBuy);
		ProcessDetail processDetail = new ProcessDetail();
		String printInfo = processDetail.calculatebuy(productBuy);

		Assert.assertNotNull(printInfo);
		System.out.println(printInfo);
	}

	// 输入一项商品，满足买二送一优惠时的输出结果
	@Test
	public void json_parse_to_product_with_one_item_has_dis2_1_count() throws Exception {
		String infoProductBuy = "['ITEM000000-3']";
		ParseInput parseInput = new ParseInput();
		productBuy = parseInput.parseInput(infoProductBuy);
		ProcessDetail processDetail = new ProcessDetail();
		String printInfo = processDetail.calculatebuy(productBuy);

		Assert.assertNotNull(printInfo);
		System.out.println(printInfo);
	}

	// 输入一项商品，满足两种优惠时的输出结果
	@Test
	public void json_parse_to_product_with_one_item_has_dis2_1_and_dis95_count() throws Exception {
		String infoProductBuy = "['ITEM000002-3']";
		ParseInput parseInput = new ParseInput();
		productBuy = parseInput.parseInput(infoProductBuy);
		ProcessDetail processDetail = new ProcessDetail();
		String printInfo = processDetail.calculatebuy(productBuy);

		Assert.assertNotNull(printInfo);
		System.out.println(printInfo);
	}

	// 输入一项商品，满足95折优惠时的输出结果
	@Test
	public void json_parse_to_product_with_one_item_has_dis95_count() throws Exception {
		String infoProductBuy = "['ITEM000003-3']";
		ParseInput parseInput = new ParseInput();
		productBuy = parseInput.parseInput(infoProductBuy);
		ProcessDetail processDetail = new ProcessDetail();
		String printInfo = processDetail.calculatebuy(productBuy);

		Assert.assertNotNull(printInfo);
		System.out.println(printInfo);
	}
	
	@Test
	public void json_parse_to_product_with_list_diff_item_has_count() throws Exception {
		// 多个不同商品结算，含优惠
		String infoProductBuy = "['ITEM000000-3','ITEM000002-4', 'ITEM000000', 'ITEM000003-3']";
		ParseInput parseInput = new ParseInput();
		productBuy = parseInput.parseInput(infoProductBuy);
		ProcessDetail processDetail = new ProcessDetail();
		String printInfo = processDetail.calculatebuy(productBuy);

		Assert.assertNotNull(printInfo);
		System.out.println(printInfo);
	}

}
