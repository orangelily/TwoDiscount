package com.model;

import org.junit.Assert;
import org.junit.Test;

public class DiscountTest {
	
	@Test
	public void readDiscountList_one_item_has_ITEM000000(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		boolean result = dis2_1.contains("ITEM000000");
		Assert.assertTrue(result);	
	}
	
	@Test
	public void readDiscountList_one_item_has_ITEM000002(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		boolean result = dis2_1.contains("ITEM000002");
		Assert.assertTrue(result);	
	}
	
	@Test
	public void readDiscountList_one_item_not_ITEM000001(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		boolean result = dis2_1.contains("ITEM000001");
		Assert.assertFalse(result);	
	}
	
	@Test
	public void add_one_item_ITEM000001(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		dis2_1.add("ITEM000001");
		boolean result = dis2_1.contains("ITEM000001");
		Assert.assertTrue(result);	
	}
	
	@Test
	public void add_one_item_ITEM000003(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		dis2_1.add("ITEM000003");
		boolean result = dis2_1.contains("ITEM000003");
		Assert.assertTrue(result);	
	}
	
	@Test
	public void add_one_item_ITEM000003_test_ITEM000004(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		dis2_1.add("ITEM000003");
		boolean result = dis2_1.contains("ITEM000004");
		Assert.assertFalse(result);	
	}
	
	@Test
	public void remove_one_item_ITEM000001(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		dis2_1.remove("ITEM000000");
		boolean result = !dis2_1.contains("ITEM000000");
		Assert.assertTrue(result);	
	}
	
	@Test
	public void remove_one_item_ITEM000003(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		dis2_1.remove("ITEM000002");
		boolean result = !dis2_1.contains("ITEM000002");
		Assert.assertTrue(result);	
	}
	
	@Test
	public void remove_one_item_ITEM000003_test_ITEM000004(){
		Discount dis2_1 = new Discount("/discountTwoSendOne.txt");
		dis2_1.remove("ITEM000000");
		boolean result = !dis2_1.contains("ITEM000002");
		Assert.assertFalse(result);	
	}
	

}
