package com.process;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.model.Discount;
import com.model.DiscountNinetyFive;
import com.model.DiscountTwoSendOne;
import com.model.Product;
import com.model.ProductBuy;
import com.model.ProductBuy.ProductForBuy;

/**
 * 主要处理逻辑，计算价格，生成小票 2016年7月17日
 */
public class ProcessDetail {
	// 保存多个不同的打折类型
	private List<Discount> discounts = new ArrayList<Discount>();
	Discount discountTwoSendOne = new DiscountTwoSendOne();
	Discount discountNinetyFive = new DiscountNinetyFive();

	public ProcessDetail() {

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
		result.append("总计：" + df.format(realTotal) + "(元)/n");
		result.append("节省：" + df.format(total - realTotal) + "(元)");
		result.append("**********************");
		return result.toString();
	}

	public String calculatebuy(ProductBuy productBuy) {
		// 拼接商品结算列表的barcode字符串
		StringBuffer sb = new StringBuffer("***<没钱赚商店>购物清单***\n");
		// 价格保留两位小数
		DecimalFormat df = new DecimalFormat("#.00");
		// 商品的总价格（不使用优惠）
		Double totalPrice = 0.0;
		// 商品的总价格（优惠后的）
		Double discountTotalPrice = 0.0;
		
		// 获取待打印的商品信息
		Map<String, ProductForBuy> mapInCar = productBuy.getMapBuy();
		// 定义待打印的满二送一优惠商品信息
		Map<String, ProductForBuy> discount2_1 = new HashMap<String, ProductForBuy>();
		// 定义待打印的95折优惠商品信息
		Map<String, ProductForBuy> discount95 = new HashMap<String, ProductForBuy>();
		Set setInCar = mapInCar.entrySet();
		Iterator it = setInCar.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			String key = (String) entry.getKey();
			// 获取商品的数量、价格等信息
			ProductForBuy value = (ProductForBuy) entry.getValue();
			sb.append("名称：");
			sb.append(value.products.getName());
			sb.append(",数量：");
			sb.append(value.count);
			sb.append(value.products.getUnit());
			sb.append("，单价：");
			sb.append(value.products.getPrice());
			sb.append("(元)，小计：");

			// 根据折扣优惠信息，判断是否重新计算优惠商品的subprice
			if (value.discountOption.equals("BUY_TWO_GET_ONE_FREE")
					|| value.discountOption.equals("FIVE_PERCENT_OFF")) {

				// 折扣后的价格=原价格-优惠价
				// 如果有满二送一的商品
				if (value.discountOption.equals("BUY_TWO_GET_ONE_FREE")) {
					// 计算实际需要买的件数
					double i = 1;// 实际需要买的件数
					for (i = 1; i <= value.count; i++) {
						if (i / 2 + i < value.count) {
							continue;
						} else {
							break;
						}
					}
					value.real_count = i;// 记录下实际需要买的件数
					value.discountPrice = i * value.products.getPrice();
					// 标记满二送一的商品信息
					discount2_1.put(key, value);
				} else if (value.discountOption.equals("FIVE_PERCENT_OFF")) {
					value.real_count = value.count;
					value.discountPrice = value.subPrice - 0.05 * value.subPrice;
					// 标记满95折商品信息
					discount95.put(key, value);
				} else {

				}
				totalPrice += value.subPrice;
				discountTotalPrice += value.discountPrice;
				/*
				 * 名称：篮球，数量：2个，单价：98.00(元)， 小计：176.00(元)，优惠：10.00（元)
				 * 名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：9.00(元)
				 * 名称：羽毛球，数量：5个，单价：1.00(元)，小计：5.00(元)
				 * 名称：苹果，数量：2斤，单价：5.50(元)，小计：11.00(元)")
				 */

				sb.append(df.format(value.discountPrice));
				sb.append("(元)，优惠：");
				sb.append(value.subPrice - value.discountPrice);

			} else {
				totalPrice += value.subPrice;
				value.real_count = value.count;
				/*
				 * 名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：9.00(元)
				 * 名称：羽毛球，数量：5个，单价：1.00(元)，小计：5.00(元)
				 * 名称：苹果，数量：2斤，单价：5.50(元)，小计：11.00(元)
				 */
				// 表示未优惠，折扣后价格==原总价
				discountTotalPrice += value.subPrice;
				sb.append(df.format(value.subPrice));
				sb.append("(元)");
			}
			sb.append("\n");
		}
		/*
		 * 总计：25.00(元)
		 */
		if ((discount2_1 == null || discount2_1.size() <= 0) && (discount95 == null || discount95.size() <= 0)) {
			sb.append("总计：");
			sb.append(df.format(totalPrice));
			sb.append("元");
		} else {
			if (discount2_1.size() > 0) {
				sb.append("买二减一商品： ");
				// 遍历有优惠的商品信息
				Set setInDicount = discount2_1.entrySet();
				Iterator itDis = setInDicount.iterator();
				while (itDis.hasNext()) {
					Map.Entry entry = (Entry) itDis.next();
					String key = (String) entry.getKey();
					// 获取优惠商品的原总价、折扣价等信息
					ProductForBuy buyProduct = (ProductForBuy) entry.getValue();

					sb.append("\n");
					sb.append(buyProduct.products.getName() + ",");
					sb.append(buyProduct.count - buyProduct.real_count);
					sb.append(buyProduct.products.getUnit());
					sb.append(",原价");
					sb.append(df.format(buyProduct.subPrice));
					sb.append("(元),优惠");
					sb.append(buyProduct.subPrice - buyProduct.discountPrice);
					sb.append("元");
				}
			}
			if (discount95.size() > 0) {
				sb.append("\n");
				sb.append("95折商品： ");
				// 遍历有优惠的商品信息
				Set setInDicount = discount95.entrySet();
				Iterator itDis = setInDicount.iterator();
				while (itDis.hasNext()) {
					Map.Entry entry = (Entry) itDis.next();
					String key = (String) entry.getKey();
					// 获取优惠商品的原总价、折扣价等信息
					ProductForBuy buyProduct = (ProductForBuy) entry.getValue();
					/*
					 * 单品满100减10块商品： 篮球，原价：186.00(元)，优惠： 10.00(元) 总计：201.00(元)
					 * 节省：10.00(元)
					 */
					sb.append("\n");
					sb.append(buyProduct.products.getName());
					sb.append(",原价");
					sb.append(df.format(buyProduct.subPrice));
					sb.append("(元),优惠");
					sb.append(buyProduct.subPrice - buyProduct.discountPrice);
					sb.append("元");
				}
			}
			sb.append("\n");
			sb.append("总计：");
			sb.append(df.format(discountTotalPrice));
			sb.append("元 节省：");
			sb.append(totalPrice - discountTotalPrice);
			sb.append("元\n************");
		}
		return sb.toString();
	}
}
