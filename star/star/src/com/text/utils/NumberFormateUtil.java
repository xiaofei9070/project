package com.text.utils;

import java.text.DecimalFormat;

public class NumberFormateUtil {
	
	/**
	 * integer 数字格式化
	 * @author liuxiaofei
	 * @date 2016年5月13日 下午2:22:09
	 * @param number  
	 * @param c 可为空
	 * @return String
	 */
	public static String formateInteger(Integer number,NumberClass numberClass){
		String pattren = null;
		if(numberClass == null){
			pattren = "###,###";
		}else{
			pattren = numberClass.getVal();
		}
		DecimalFormat df = new DecimalFormat(pattren);
		String format = df.format(number);
		df = null;
		return format;
	}
	/**
	 * double 数字格式
	 * @author liuxiaofei
	 * @date 2016年5月13日 下午2:23:29
	 * @param number
	 * @param c 可为空
	 * @return
	 */
	public static String formateDouble(Double number,NumberClass numberClass){
		String pattren = null;
		if(numberClass == null){
			pattren = "###,##0.00";
		}else{
			pattren = numberClass.getVal();
		}
		DecimalFormat df = new DecimalFormat(pattren);
		String format = df.format(number);
		df = null;
		return format;
	}
	
	
	
	public enum NumberClass{
		$NNN_NNN("$###,###"),
		￥NNN_NNN("￥###,###"),
		NNN_NNN("###,###"),
		$DDD_DDD("$###,##0.00"),
		￥DDD_DDD("￥###,##0.00"),
		DDD_DDD("###,##0.00");
		
		private String val;
		private NumberClass(String val){
			this.val = val;
		}
		public String getVal() {
			return val;
		}
		public void setVal(String val) {
			this.val = val;
		}
	}
}
