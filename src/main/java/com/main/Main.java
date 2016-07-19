package com.main;

import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Scanner scanner = new Scanner(System.in);
		Calculate calculate = new Calculate();
		String input=null;
//		System.out.println("菜单选择：");
		System.out.println("输入条形码列表计算总价或输入'exit'退出");
		while(!(input=scanner.nextLine()).equals("exit")){
//			System.out.println("2、显示打折商品列表");
//			System.out.println("3、添加/删除打折商品");
			Map<String, Double> buyMap = calculate.parseInput(input);
			String result = calculate.calculate(buyMap);
			System.out.println(result);	
			System.out.println("输入条形码列表计算总价或输入'exit'退出");
		}
	}

}
