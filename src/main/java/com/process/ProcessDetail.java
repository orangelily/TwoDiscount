package com.process;

import java.util.ArrayList;
import java.util.List;

import com.model.Discount;
import com.model.DiscountNinetyFive;
import com.model.DiscountTwoSendOne;

/**
 * 主要处理逻辑，计算价格，生成小票
 * 2016年7月17日
 */
public class ProcessDetail {
	//保存多个不同的打折类型
	private List<Discount> discount = new ArrayList<Discount>();
	
	private 
	
	ProcessDetail(){
		Discount discountNinetyFive = new DiscountNinetyFive();
		Discount discountTwoSendOne = new DiscountTwoSendOne();
		discount.add(discountNinetyFive);
		discount.add(discountTwoSendOne);
	} 
	
	
}
