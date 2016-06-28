package com.star.anno;

import org.junit.Test;
import org.springframework.stereotype.Component;

@Component
public class TestMain extends SuperClass{
	
	@TestVal(value = "aaaa",url = "1")
	private String val;
	@Test
	public void test(){
		System.out.println(val);
	}
}
