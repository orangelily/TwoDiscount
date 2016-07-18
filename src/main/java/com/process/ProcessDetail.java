package com.process;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.Discount;
import com.model.DiscountNinetyFive;
import com.model.DiscountTwoSendOne;
import com.model.Product;

/**
 * 主要处理逻辑，计算价格，生成小票 2016年7月17日
 */
public class ProcessDetail {
	// 保存多个不同的打折类型
	private List<Discount> discounts = new ArrayList<Discount>();
	Discount discountTwoSendOne = new DiscountTwoSendOne();
	Discount discountNinetyFive = new DiscountNinetyFive();

	private ProcessDetail() {
		
	}

	public String calculate(Map<Product, Double> map) {
		StringBuffer result = new StringBuffer("***<没钱赚商店>购物清单***/n");
		StringBuffer twoSendOne = new StringBuffer("");
		// 总金额
		double total = 0.0;
		// 实际需要支付总金额
		double realTotal = 0.0;
		Set<Product> usedDiscount = new HashSet<>();
		DecimalFormat df = new DecimalFormat("######0.00");
		for (Map.Entry<Product, Double> entry : map.entrySet()) {
			Product product = entry.getKey();
			double price = product.getPrice();
			double number = entry.getValue();
			double subTotal = price * number;
			double subrRealTotal = 0.0;
			if (discountTwoSendOne.isDiscount(product)) {
				if (Math.abs(number - 3.0) >= 0.000001) {
					int sendNumber = (int) number / 3;
					subrRealTotal = price * (number - sendNumber);
					if (twoSendOne.length() == 0) {
						twoSendOne.append("----------------------/n");
						twoSendOne.append("买二赠一商品：/n");
					}
					twoSendOne.append("名称：" + product.getName() + "，数量：" + sendNumber + product.getUnit());
					result.append("名称：" + product.getName() + "，数量：" + number + product.getUnit() + "，单价："
							+ product.getPrice() + "(元)，小计：" + df.format(subrRealTotal) + "(元)");
				}
			} else if (discountNinetyFive.isDiscount(product)) {
				subrRealTotal = product.getPrice() * number * 0.95;

				result.append("名称：" + product.getName() + "，数量：" + number + product.getUnit() + "，单价："
						+ product.getPrice() + "(元)，小计：" + df.format(subrRealTotal) + "(元)，节省"
						+ df.format(subTotal - subrRealTotal) + "(元)");
			}

		}
		result.append(twoSendOne.toString());
		result.append("/n");
		result.append("----------------------/n");
		result.append("总计："+df.format(realTotal)+"(元)/n");
		result.append("节省："+df.format(total-realTotal)+"(元)");
		result.append("**********************");
		return result.toString();	
	}

}
