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
import com.model.Product;

public class Calculate {
	// 保存读取的product.txt
	private Map<String, Product> map = new HashMap<String, Product>();
	// 买二送一的商品列表
	private List<String> dis2_1 = new ArrayList<String>();
	// 95折的商品列表
	private List<String> dis95 = new ArrayList<String>();

	public Calculate() {
		readProductList();
		readDiscountList("/discountTwoSendOne.txt", dis2_1);
		readDiscountList("/discountNinetyFive.txt", dis95);
	}

	/**
	 * 读取商品信息列表，放到全局变量map中
	 */
	public void readProductList() {
		InputStream is = this.getClass().getResourceAsStream("/product.txt");
		InputStreamReader read = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(read);
		String lineTxt = null;
		try {
			while ((lineTxt = br.readLine()) != null) {
				String[] content = lineTxt.split(",");
				String barcode = content[0];
				String name = content[1];
				String unit = content[2];
				String category = content[3];
				String subCategory = content[4];
				double price = Double.parseDouble(content[5]);
				Product product = new Product(barcode, name, unit, category, subCategory, price);
				map.put(barcode, product);
			}

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取打折商品列表
	 * 
	 * @param path
	 *            某一类打折的文件路径
	 * @param list
	 *            保存获取的打折商品列表
	 */
	public void readDiscountList(String path, List<String> list) {
		InputStream is = this.getClass().getResourceAsStream(path);
		InputStreamReader read = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(read);
		String lineTxt = null;
		try {
			while ((lineTxt = br.readLine()) != null) {
				list.add(lineTxt);
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		// 解析每一项，返回map
		String[] input = inputString.split(",");
		Map<String, Double> buy = new HashMap<>();
		for (String str : input) {
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
			double subTotal = price * number;
			double subrRealTotal = 0.0;
			if (dis2_1.contains(barcode) && (number - 3.0) >= 0.0) {
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
				subrRealTotal = product.getPrice() * number * 0.95;
				result.append("名称：" + product.getName() + "，数量：" + number + product.getUnit() + "，单价："
						+ product.getPrice() + "(元)，小计：" + df.format(subrRealTotal) + "(元)，节省"
						+ df.format(subTotal - subrRealTotal) + "(元)\n");
			} else {
				subrRealTotal = subTotal;
				result.append("名称：" + product.getName() + "，数量：" + number + product.getUnit() + "，单价："
						+ product.getPrice() + "(元)，小计：" + df.format(subrRealTotal) + "(元)\n");
			}
			total += subTotal;
			realTotal += subrRealTotal;
		}
		if (twoSendOne.length() > 0) {
			result.append(twoSendOne.toString());
			result.append("\n");
		}
		result.append("----------------------\n");
		result.append("总计：" + df.format(realTotal) + "(元)\n");
		if (Math.abs(total - realTotal) > 0.000001)
			result.append("节省：" + df.format(total - realTotal) + "(元)\n");
		result.append("**********************");
		return result.toString();
	}

	public Map<String, Product> getMap() {
		return map;
	}

	public void setMap(Map<String, Product> map) {
		this.map = map;
	}

	public List<String> getDis2_1() {
		return dis2_1;
	}

	public void setDis2_1(List<String> dis2_1) {
		this.dis2_1 = dis2_1;
	}

	public List<String> getDis95() {
		return dis95;
	}

	public void setDis95(List<String> dis95) {
		this.dis95 = dis95;
	}
}
