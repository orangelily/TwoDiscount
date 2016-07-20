package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.model.Product;

public class CalculateTest {

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

	// 以下测试calculate()
	@Test
	public void calculate_one_item_not_discount_test() throws Exception {
		Calculate calculate = new Calculate();
		Map<String, Double> buyMap = calculate.parseInput("['ITEM000001-3']");
		String result = calculate.calculate(buyMap);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
	
	@Test
	public void calculate_one_item_has_discount2_1_test() throws Exception {
		Calculate calculate = new Calculate();
		Map<String, Double> buyMap = calculate.parseInput("['ITEM000000-3']");
		String result = calculate.calculate(buyMap);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
	
	@Test
	public void calculate_one_item_has_discount2_1_and_discount95_test() throws Exception {
		Calculate calculate = new Calculate();
		Map<String, Double> buyMap = calculate.parseInput("['ITEM000002-6']");
		String result = calculate.calculate(buyMap);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
	
	@Test
	public void calculate_one_item_has_discount95_test() throws Exception {
		Calculate calculate = new Calculate();
		Map<String, Double> buyMap = calculate.parseInput("['ITEM000003-6']");
		String result = calculate.calculate(buyMap);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
	
	@Test
	public void calculate_two_item_has_discount2_1_and_discount95_test() throws Exception {
		Calculate calculate = new Calculate();
		Map<String, Double> buyMap = calculate.parseInput("['ITEM000000-4','ITEM000003-6']");
		String result = calculate.calculate(buyMap);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
}
