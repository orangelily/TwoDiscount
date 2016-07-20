package com.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exception.ProductBuyInfoException;
import com.model.Discount;
import com.model.Product;
import com.model.ProductManager;

/**
 * @author yefengzhichen 2016年7月20日
 */
public class Calculate {

	// private Map<String, Product> map = new HashMap<String, Product>();
	// private List<String> dis2_1 = new ArrayList<String>();
	// private List<String> dis95 = new ArrayList<String>();
	// 替换上面的简单方式，方便扩展
	// 买二送一的商品列表
	private Discount dis2_1;
	// 95折的商品列表
	private Discount dis95;
	// 保存读取的product.txt
	private ProductManager map;

	public Calculate() {
		map = new ProductManager();
		dis2_1 = new Discount("/discountTwoSendOne.txt");
		dis95 = new Discount("/discountNinetyFive.txt");
	}

	/**
	 * 解析条形码输入，输入中包含数量，进行分割
	 * 
	 * @param inputString
	 *            输入的json格式字符串
	 * @return 包含的商品条形码和数量
	 * @throws Exception
	 */
	public Map<String, Double> parseInput(String inputString) throws Exception {
		// 解析和去掉[]
		String newShopCar = inputString.trim();
		Integer index = newShopCar.indexOf('[');
		if (index < 0) {
			throw new ProductBuyInfoException();
		}
		index = newShopCar.indexOf(']');
		if (index < 0) {
			throw new ProductBuyInfoException();
		}
		inputString = newShopCar.substring(1, newShopCar.length() - 1).trim();
		// 解析每一项,用，分割
		String[] input = inputString.split(",");
		Map<String, Double> buy = new HashMap<>();
		for (String str : input) {
			//去掉首尾的''
			str = str.substring(1, str.length() - 1);
			String[] content = str.split("-");
			double num = 0.0;
			if (content.length == 1) {
				num = 1.0;
			} else if (content.length == 2) {
				num = Double.parseDouble(content[1]);
			}
			String key = content[0];
			if (buy.containsKey(key)) {
				num += buy.get(key);
				buy.put(key, num);
			} else {
				buy.put(key, num);
			}
		}
		return buy;
	}

	/**
	 * @param map
	 * @return
	 */
	public String calculate(Map<String, Double> buyMap) {
		StringBuffer result = new StringBuffer("***<没钱赚商店>购物清单***\n");
		StringBuffer twoSendOne = new StringBuffer("");
		// 总金额
		double total = 0.0;
		// 实际需要支付总金额
		double realTotal = 0.0;
		DecimalFormat df = new DecimalFormat("######0.00");
		for (Map.Entry<String, Double> entry : buyMap.entrySet()) {
			String barcode = entry.getKey();
			Product product = map.get(barcode);
			double price = product.getPrice();
			double number = entry.getValue();
			// 每一项的总金额
			double subTotal = price * number;
			// 每一项实际需要支付总金额
			double subrRealTotal = 0.0;
			if (dis2_1.contains(barcode) && (number - 3.0) >= 0.0) {
				//是买二送一商品，且个数大于三个
				int sendNumber = (int) number / 3;
				subrRealTotal = price * (number - sendNumber);
				if (twoSendOne.length() == 0) {
					twoSendOne.append("----------------------\n");
					twoSendOne.append("买二赠一商品：\n");
				}
				twoSendOne.append("名称：" + product.getName() + "，数量：" + sendNumber + product.getUnit());
				result.append("名称：" + product.getName() + "，数量：" + number + product.getUnit() + "，单价："
						+ product.getPrice() + "(元)，小计：" + df.format(subrRealTotal) + "(元)\n");
			} else if (dis95.contains(barcode)) {
				//是95折商品
				subrRealTotal = product.getPrice() * number * 0.95;
				result.append("名称：" + product.getName() + "，数量：" + number + product.getUnit() + "，单价："
						+ product.getPrice() + "(元)，小计：" + df.format(subrRealTotal) + "(元)，节省"
						+ df.format(subTotal - subrRealTotal) + "(元)\n");
			} else {
				//不满足优惠条件
				subrRealTotal = subTotal;
				result.append("名称：" + product.getName() + "，数量：" + number + product.getUnit() + "，单价："
						+ product.getPrice() + "(元)，小计：" + df.format(subrRealTotal) + "(元)\n");
			}
			//累加起来
			total += subTotal;
			realTotal += subrRealTotal;
		}
		//有买二送一优惠，则输出
		if (twoSendOne.length() > 0) {
			result.append(twoSendOne.toString());
			result.append("\n");
		}
		result.append("----------------------\n");
		result.append("总计：" + df.format(realTotal) + "(元)\n");
		//有优惠商品，则输出节省金额
		if (Math.abs(total - realTotal) > 0.000001)
			result.append("节省：" + df.format(total - realTotal) + "(元)\n");
		result.append("**********************");
		return result.toString();
	}

}
