package com.text.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class SetSort<T> extends HashSet<T>{

	private static final long serialVersionUID = -9168149676289448310L;
	
	/**
	 * 重新排序 返回list集合
	 * @date 2016年6月2日 下午4:28:00
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> sort(){
		List<String> list = new ArrayList<>();
		list.addAll((Collection<? extends String>) SetSort.this);
		Collections.sort(list,new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				return compareToStr(o1, o2);
			}
		});
		return list;
	}
	/**
	 * 提取数字与字符集合
	 * @date 2016年6月2日 下午4:26:38
	 * @param str
	 * @return
	 */
	private static List<String> match(String str){
		str += " ";
		char[] chars = str.toCharArray();
		List<String> list = new ArrayList<String>();
		String arr = "";
		for(char c:chars){
			if(ValidateUtil.validateInteger(String.valueOf(c))){
				arr += c;
			}else if(String.valueOf(c).equals(".") && !arr.equals("")){
				arr += c;
			}else{
				if(!arr.equals("")){
					list.add(arr);
					arr = "";
				}
				list.add(String.valueOf(c));
			}
		}
		return list;
	}
	/**
	 * 计算字符相差大小
	 * @date 2016年6月2日 下午4:26:58
	 * @param str1
	 * @param str2
	 * @return
	 */
	private static int compareToStr(String str1,String str2){
		List<String> list1 = match(str1);
		List<String> list2 = match(str2);
		int size1 = list1.size();
		int size2 = list2.size();
		int size = size1 - size2 > 0 ? size2:size1;
		int ef = 0;
		for(int i = 0;i < size-1;i++){
			String s1 = list1.get(i);
			String s2 = list2.get(i);
			if(ValidateUtil.validateDouble(s1) && ValidateUtil.validateDouble(s2)){
				ef = (int) ((Double.parseDouble(s1) - Double.parseDouble(s2)) * 1000);
			}else{
				if(ValidateUtil.validateInteger(s1) && !ValidateUtil.validateInteger(s2)){
					ef = -1;
				}else if(!ValidateUtil.validateInteger(s1) && ValidateUtil.validateInteger(s2)){
					ef = 1;
				}else if(!ValidateUtil.validateInteger(s1) && !ValidateUtil.validateInteger(s2)){
					ef = s1.charAt(0) - s2.charAt(0);
				}
			}
			if(ef != 0){
				break;
			}
		}
		return ef;
	}
	
}
