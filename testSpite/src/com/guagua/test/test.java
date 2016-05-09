package com.guagua.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) {
		String phoneString = "哈哈,13888889999";
		// 提取数字
		// 1
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(phoneString);
		String all = matcher.replaceAll("");
		System.out.println("phone:" + all);
		// 2
		Pattern.compile("[^0-9]").matcher(phoneString).replaceAll("");
		System.out.println("".compareTo(null));

	}

}
