package com.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exception.AddProductBuyInfoException;
import com.exception.ProductBuyInfoException;
import com.model.Product;
import com.model.ProductBuy;

/**
 * 解析输入的商品条形码列表，返回map，key为商品，value为商品的数量
 * 
 * @author yefengzhichen 2016年7月17日
 */
public class ParseInput {
	private Map<String, Product> map = new HashMap<String, Product>();
	// 购物车商品列表
	ProductBuy productBuy = new ProductBuy();
	// 买二送一的商品列表
	private List<String> dis2_1 = new ArrayList<String>();
	// 95的商品列表
	private List<String> dis95 = new ArrayList<String>();

	public ParseInput() {
		readProductList();

	}

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

	// 解析条形码输入，输入中包含数量，进行分割————kuangli
	public ProductBuy parseInput(String inputString) throws Exception {
		/*
		 * 输入格式（样例）： // javascript [ 'ITEM000001', 'ITEM000001', 'ITEM000001',
		 * 'ITEM000001', 'ITEM000001', 'ITEM000003-2', 'ITEM000005',
		 * 'ITEM000005', 'ITEM000005' ]
		 */
		try {
			// 根据输入字符串，拆分去掉首尾[] , 得到购物车的商品信息
			String newShopCar = inputString.trim();
			Integer index = newShopCar.indexOf('[');
			if (index < 0) {
				throw new ProductBuyInfoException();
			}
			index = newShopCar.indexOf(']');
			if (index < 0) {
				throw new ProductBuyInfoException();
			}
			newShopCar = newShopCar.substring(1, newShopCar.length() - 1).trim();
			String[] barcodeArr = newShopCar.split(",");
			for (int i = 0; i < barcodeArr.length; i++) {
				// 去掉字符串中的首尾单引号
				String barcode = barcodeArr[i].trim();
				barcode = barcode.substring(1, barcode.length() - 1);
				int count = 1;
				// 拆分barcode类型为'ITEM000003-2'的情况
				String[] countSpli = barcode.split("-");
				if (countSpli.length <= 1) {
					count = 1;
				} else {
					count = Integer.parseInt(countSpli[1]);
				}
				// 判断购买商品的条形码是否属于商店商品条形码数据
				if (map.get(countSpli[0]) == null) {
					throw new AddProductBuyInfoException();
				} else {
					String discountOption = "";
					//如果有满二减一
					if(dis2_1.contains(countSpli[0])){
						discountOption="BUY_TWO_GET_ONE_FREE";
					}else if(dis95.contains(countSpli[0])){
						//如果满足95折
						discountOption="FIVE_PERCENT_OFF";
					}else{
					}
					// 将购买商品添加到购物车
					productBuy.addProduct(map.get(countSpli[0]), count, discountOption);
				}

			}
			return productBuy;
		} catch (Exception e) {
			if (e instanceof AddProductBuyInfoException) {
				throw e;
			} else {
				throw new ProductBuyInfoException();
			}
		}
	}

}
