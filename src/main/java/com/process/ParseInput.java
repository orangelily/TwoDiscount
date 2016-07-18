package com.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.model.Product;

/**
 * 解析输入的商品条形码列表，返回map，key为商品，value为商品的数量
 * @author yefengzhichen
 * 2016年7月17日
 */
public class ParseInput {
	private Map<String, Product> map = new HashMap<String, Product>();

	public ParseInput(){
		readProductList();
	}

	public void readProductList(){
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
	
	// 解析条形码输入，输入中包含数量，进行分割
	public Map<Product, Double> parseInput(String inputString) {
		Map<Product, Double> buy = new HashMap<Product, Double>();
		String[] input = inputString.split(",");
		for (String str : input) {
			String[] content = str.split("-");
			double num = 0.0;
			if (content.length == 1) {
				num = 1.0;
			} else if (content.length == 2) {
				num = Double.parseDouble(content[1]);
			}
			String barcode = content[0];
			Product key = map.get(barcode);
			if (buy.containsKey(key)) {
				num += buy.get(key);
				buy.put(key, num);
			} else {
				buy.put(key, num);
			}
		}
		return buy;
	}
	
}
