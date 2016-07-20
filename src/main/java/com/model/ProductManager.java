package com.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ProductManager {
	private Map<String, Product> map = new HashMap<String, Product>();
	
	public ProductManager() {
		readProductList();
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
	
	public Product get(String barcode){
		return map.get(barcode);
	}
	
	public void put(String barcode, Product product){
		map.put(barcode, product);
	}
}
