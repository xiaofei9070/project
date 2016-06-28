package com.star.annotation;


@DefaultVal
public class Test {
	
	public static void main(String[] args) {
		System.out.println(((DefaultVal)Test.class.getAnnotation(DefaultVal.class)).value());
	}
}
