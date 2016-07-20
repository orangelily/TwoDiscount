package com.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Discount {
	private List<String> list = new ArrayList<>();
	private String path=null;
	
	public Discount(){} 
	
	public Discount(String string) {
		path = string;
		readDiscountList(path);
	}
	
	/**
	 * 读取打折商品列表
	 * 
	 * @param path
	 *            某一类打折的文件路径
	 * @param list
	 *            保存获取的打折商品列表
	 */
	public void readDiscountList(String path) {
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
	
	public boolean contains(String barcode){
		return list.contains(barcode);
	}
	
	public void add(String barcode){
		if(!contains(barcode))
			list.add(barcode);
	}
	
	public void remove(String barcode){
		if(contains(barcode))
			list.remove(barcode);
	}
	
	//增删打折商品后，保存到txt文件中
	public void saveTotxt(){
		
	}
	
}
