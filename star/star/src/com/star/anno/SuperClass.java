package com.star.anno;

import java.lang.reflect.Field;

public class SuperClass {
	public SuperClass(){
		/*Field[] fields = this.getClass().getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(TestVal.class)){
				TestVal testVal = field.getAnnotation(TestVal.class);
				field.setAccessible(true);
				try {
					field.set(this, testVal.value());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(TestVal.class)){
				TestVal testVal = field.getAnnotation(TestVal.class);
				field.setAccessible(true);
				try {
					System.out.println(testVal.value());
					System.out.println(testVal.url());
					field.set(this, testVal.url());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
