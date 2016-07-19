package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.model.Product;

public class CalculateTest {

	// 以下三个测试readProductList
	@Test
	public void readProductList_have_ITEM000000_test() {
		Calculate calculate = new Calculate();
		calculate.readProductList();
		Map<String, Product> map = calculate.getMap();
		Product product = new Product("ITEM000000", "可口可乐", "瓶", "食品", "碳酸饮料", 3.00);
		boolean result = product.equals(map.get("ITEM000000"));
		Assert.assertTrue(result);
	}

	@Test
	public void readProductList_have_ITEM000001_test() {
		Calculate calculate = new Calculate();
		calculate.readProductList();
		Map<String, Product> map = calculate.getMap();
		// ITEM000001,雪碧,瓶,食品,碳酸饮料,3.00
		Product product = new Product("ITEM000001", "雪碧", "瓶", "食品", "碳酸饮料", 3.00);
		boolean result = product.equals(map.get("ITEM000001"));
		Assert.assertTrue(result);
	}

	@Test
	public void readProductList_not_have_ITEM000001_test() {
		Calculate calculate = new Calculate();
		calculate.readProductList();
		Map<String, Product> map = calculate.getMap();
		// ITEM000001,雪碧,瓶,食品,碳酸饮料,3.00
		Product product = new Product("ITEM000001", "雪碧", "瓶", "食品", "碳酸饮料", 3.00);
		boolean result = product.equals(map.get("ITEM000000"));
		Assert.assertFalse(result);
	}

	// 以下三个测试readDiscountList
	@Test
	public void readDiscountList_have_ITEM000000_test() {
		Calculate calculate = new Calculate();
		List<String> list = new ArrayList<>();
		calculate.readDiscountList("/discountTwoSendOne.txt", list);
		boolean result = list.contains("ITEM000000");
		Assert.assertTrue(result);
	}

	@Test
	public void readDiscountList_have_ITEM000002_test() {
		Calculate calculate = new Calculate();
		List<String> list = new ArrayList<>();
		calculate.readDiscountList("/discountTwoSendOne.txt", list);
		boolean result = list.contains("ITEM000002");
		Assert.assertTrue(result);
	}

	@Test
	public void readDiscountList_not_have_ITEM000001_test() {
		Calculate calculate = new Calculate();
		List<String> list = new ArrayList<>();
		calculate.readDiscountList("/discountTwoSendOne.txt", list);
		boolean result = list.contains("ITEM000001");
		Assert.assertFalse(result);
	}

	// 以下三个测试parseInput
	@Test
	public void parseInput_one_item_has_one_test() throws Exception {
		Calculate calculate = new Calculate();
		Map<String, Double> buyMap = new HashMap<>();
		buyMap = calculate.parseInput("['ITEM000000']");
		double number = buyMap.get("ITEM000000");
		boolean result = Math.abs(number - 1.00) < 0.000001;
		Assert.assertTrue(result);
	}

	@Test
	public void parseInput_one_item_has_many_test() throws Exception {
		Calculate calculate = new Calculate();
		Map<String, Double> buyMap = new HashMap<>();
		buyMap = calculate.parseInput("['ITEM000000-3']");
		double number = buyMap.get("ITEM000000");
		boolean result = Math.abs(number - 3.00) < 0.000001;
		Assert.assertTrue(result);
	}

	@Test
	public void parseInput_many_item_has_many_test() throws Exception {
		Calculate calculate = new Calculate();
		Map<String, Double> buyMap = new HashMap<>();
		buyMap = calculate.parseInput("['ITEM000000-3','ITEM000003-5','ITEM000000-2']");
		double number1 = buyMap.get("ITEM000000");
		boolean result1 = Math.abs(number1 - 5.00) < 0.000001;
		double number2 = buyMap.get("ITEM000003");
		boolean result2 = Math.abs(number2 - 5.00) < 0.000001;
		Assert.assertTrue(result1 && result2);
	}

}
