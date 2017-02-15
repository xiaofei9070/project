package com.text.utils.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {
	
	public static Document parse(String html){
		return Jsoup.parse(html);
	}
	
	public static void main(String[] args) throws IOException {
		String html = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.connect("https://www.baidu.com").post();
		Elements elementsByTag = doc.getElementsByTag("a");
		for(Element e:elementsByTag){
			System.out.println(1);
		}
	}
	
}
