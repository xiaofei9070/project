package com.github.pagehelper;

import javax.servlet.http.HttpServletRequest;


/** 
 * Mybatis - 创建分页样式
 *
 * @author ghost
 *
 * <pre>
 * 类名称    : PageUtils
 * 类描述    : 构造默认分页样式，使用时页面如引入jquery
 * 创建人    : ghost
 * 创建时间: 2014-12-1下午01:03:55
 * 修改人    : ghost
 * 修改时间: 2014-12-1下午01:03:55
 * 修改备注:
 * </pre>
 */
public class PageUtils {

	/**
	 * 
	 * @param request HttpServletRequest
	 * @param page PageInfo<??
	 * @param isAjax 是否Ajax分页
	 * @return page html
	 */
	public static String createDefaultPageHtml(HttpServletRequest request,PageInfo<?> page,boolean isAjax){
		
		String preUrl = request.getRequestURI();
		
		String k1 = request.getParameter("k1");if(k1 == null) k1 = "";
		String k2 = request.getParameter("k2");if(k2 == null) k2 = "";
		String k3 = request.getParameter("k3");if(k3 == null) k3 = "";
		String k4 = request.getParameter("k4");if(k4 == null) k4 = "";
		String k5 = request.getParameter("k5");if(k5 == null) k5 = "";
		
		
		StringBuffer buffer = new StringBuffer();
		
		//分页css
		String a_style = "\"padding:8px 16px; margin: 10px 3px;font-size: 14px;border: 1px solid #DFDFDF;background-color: #FFF;color: #9d9d9d;text-decoration: none;outline: none;\"";
		String go_page_wrap = "\"display: inline-block;width: 44px;height: 18px;border: 1px solid #DFDFDF;margin: 0px 1px;padding: 0px;position: relative;left: 0px;top: 5px;border-color: rgb(223, 223, 223);\"";
		String page_btn_go = "\"width: 44px;height: 20px;line-height: 20px;padding: 0px;font-family: arial,宋体,sans-serif;text-align: center;border: 0px;background-color: #C70007;color: #FFF;position: absolute;top: -1px;display: none; left: 0px;\"";
		String page_btn_go_input = "\"width: 42px;height: 16px;text-align: center;border: 0px;position: absolute;left: 0px;top: 0px;outline: none;\"";
		String onMouse_over = "\"this.style.cssText='background-color:#FFEEE5;border:1px solid #C70007;padding:8px 16px; margin: 10px 3px;font-size: 14px;color: #9d9d9d;text-decoration: none;'\"";
		String onMouse_out = "\"this.style.cssText='background-color:#FFF;border: 1px solid #DFDFDF;padding:8px 16px; margin: 10px 3px;font-size: 14px;color:#9d9d9d;text-decoration: none;'\"";
		
		buffer.append("<div  style=\"width:100%;text-align: center; display: block;clear: both;height: 50px;line-height: 30px;margin: 0 auto;padding: 20px 0;color: #999999;font-size: 14px;\">");
		
		//分页js
		buffer.append("<script type=\"text/javascript\">");
		
				buffer.append("function focus_gopage(){");
						buffer.append("$(\"#page_btn_go\").css(\"display\",\"inline-block\").css(\"left\",\"44px\");");
						buffer.append("$(\"#gopage_wrap\").css(\"border-color\",\"#C70007\");");
				buffer.append("}");
 		
				buffer.append("function blur_gopage(){");
						
						if(isAjax){
							buffer.append("var pageNum = $(\"#page_btn_go_input\").val();");
							buffer.append("$(\"#page_btn_go\").attr(\"onclick\",\"ajax_go_page(\"+pageNum+\","+page.getPageSize()+")\");");
						}
						
						buffer.append("setTimeout(function(){");
						buffer.append("	$(\"#page_btn_go\").hide();");
						buffer.append("$(\"#gopage_wrap\").css(\"border-color\",\"white\");");
						buffer.append("},300);");
				buffer.append("}");
					
				buffer.append("function gopage(){");
						buffer.append("var pageNum = $(\"#page_btn_go_input\").val();");
						buffer.append("window.location.href=\""+preUrl+"?pageNum=\"+pageNum+\"&pageSize="+page.getPageSize()+"&k1="+k1+"&k2="+k2+"&k3="+k3+"&k4="+k4+"&k5="+k5+"\";");
				buffer.append("}");
	  buffer.append("</script>");
	  
 	
			if(isAjax){
				
				buffer.append("<a page="+page.getPrePage()+" onmouseover="+onMouse_over+" onmouseout="+onMouse_out+" style="+a_style+" href=\"javascript:ajax_go_page('"+page.getPrePage()+"','"+page.getPageSize()+"');\">前一页</a>");
			
			}else{
				
				buffer.append("<a page="+page.getPrePage()+" onmouseover="+onMouse_over+" onmouseout="+onMouse_out+" style="+a_style+" href=\""+preUrl+"?pageNum="+page.getPrePage()+"&pageSize="+page.getPageSize()+"&k1="+k1+"&k2="+k2+"&k3="+k3+"&k4="+k4+"&k5="+k5+"\">前一页</a>");
				
			}
			
		for(int i=0;i<page.getNavigatepageNums().length;i++){
			
			int index  = page.getNavigatepageNums()[i];
			if(index == page.getPageNum()){
				
				buffer.append(" <span style=\"padding: 8px 16px;margin: 10px 3px;font-size: 12px;border: 1px solid #C70007;background-color: #C70007;color: #FFF;\">"+index+"</span>");
			
			}else{
				
				if(isAjax){
					buffer.append("<a  page="+index+"   onmouseover="+onMouse_over+" onmouseout="+onMouse_out+" style="+a_style+" href=\"javascript:ajax_go_page('"+index+"','"+page.getPageSize()+"')\">"+index+"</a>");
				}else{
					buffer.append("<a  page="+index+"   onmouseover="+onMouse_over+" onmouseout="+onMouse_out+" style="+a_style+" href=\""+preUrl+"?pageNum="+index+"&pageSize="+page.getPageSize()+"&k1="+k1+"&k2="+k2+"&k3="+k3+"&k4="+k4+"&k5="+k5+"\">"+index+"</a>");
				}
			}
		}
		
		buffer.append("...");
		
		if(isAjax){
			
			buffer.append("<a page="+page.getNextPage()+"    onmouseover="+onMouse_over+" onmouseout="+onMouse_out+" style="+a_style+" href=\"javascript:ajax_go_page('"+page.getNextPage()+"','"+page.getPageSize()+"')\">下一页</a>");
			
		}else{
			
			buffer.append("<a page="+page.getNextPage()+"    onmouseover="+onMouse_over+" onmouseout="+onMouse_out+" style="+a_style+" href=\""+preUrl+"?pageNum="+page.getNextPage()+"&pageSize="+page.getPageSize()+"&k1="+k1+"&k2="+k2+"&k3="+k3+"&k4="+k4+"&k5="+k5+"\">下一页</a>");
		}
         
			buffer.append("共"+page.getPages()+"页/"+page.getTotal()+"条数据&nbsp;转到");
			buffer.append("<span id=\"gopage_wrap\" style="+go_page_wrap+" >");
			buffer.append("<input type=\"button\" id=\"page_btn_go\"  style="+page_btn_go+" onclick=\"gopage();\" value=\"确定\" >");
			buffer.append("	<input type=\"text\" id=\"page_btn_go_input\"  style="+page_btn_go_input+" onfocus=\"focus_gopage();\" onblur=\"blur_gopage();\" value="+page.getPageNum()+" hidefocus=\"true\">");
			buffer.append("</span>");
			buffer.append("页");
		
     	buffer.append("</div>");
		
	  return buffer.toString();
	}
}
