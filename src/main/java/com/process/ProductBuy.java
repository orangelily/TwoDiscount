package com.process;

import java.util.HashMap;
import java.util.Map;

import com.model.Product;

public class ProductBuy {
	//内部类，每一种商品的合计信息
		public class ProductForBuy{
			public Product products;
			public Double count=0.0;//数量(个、斤)
			public Double real_count=0.0;//实际需要购买数量
			public Double subPrice=0.0;//同种商品未进行优惠的累计价格
			public Double discountPrice=0.0;//优惠后的价格
			public String discountOption="";//优惠选项
			public ProductForBuy(Product products){
				this.products = products;
			}
		}
		//已选有的商品条形码转成map信息
		private Map<String, ProductForBuy> mapBuy = new HashMap<String, ProductForBuy>();
		//购物车中不打则情况下的总价格
		private Double totalPrice=0.0;
		//商品扫码
		public void addProduct(Product products,double count,String discountOption){
			if (products==null||products.getBarcode()==null||products.getBarcode().equals("")) {
				return;
			}
			ProductForBuy pb=mapBuy.get(products.getBarcode());
			if (pb==null) {
				//对于购物车里没有的此商品要新建
				pb = new ProductForBuy(products);
			}
			pb.count += count;
			pb.subPrice=pb.count*products.getPrice();
			totalPrice+=products.getPrice();
			pb.discountOption = discountOption;
			mapBuy.put(products.getBarcode(), pb);
		}
		

		public Map<String, ProductForBuy> getMapBuy() {
			return mapBuy;
		}


		public void setMapBuy(Map<String, ProductForBuy> mapBuy) {
			this.mapBuy = mapBuy;
		}


		public Double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
		}
}
