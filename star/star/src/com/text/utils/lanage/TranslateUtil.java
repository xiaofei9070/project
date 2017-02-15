package com.text.utils.lanage;

import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class TranslateUtil {
	 
    private static Log logger = LogFactory.getLog(TranslateUtil.class);
 
    private static final String URL_TEMPLATE = "http://translate.google.cn/translate_a/single?client=t";
    private static final String ENCODING = "UTF-8";
 
    private DefaultHttpClient client;
 
    private static TranslateUtil translateUtil = new TranslateUtil();
 
    private TranslateUtil() {
        client = ConnectionManager.getHttpClient();
    }
 
    public static TranslateUtil getInstance() {
        return translateUtil;
    }
 
    /**
     * google翻译(文本自动识别)
     * 
     * @param text
     *            要翻译的文本
     * @param target_lang
     *            目标语言
     * @return
     * @throws Exception
     */
    public String translate(final String text, final String target_lang) {
        return translate(text, GoogleLanage.AUTO.getValue(), target_lang);
    }
 
    /**
     * 中午转为英文
     * 
     * @param text
     * @return
     */
    public String cn2en(final String text) {
        return translate(text, GoogleLanage.CHINA.getValue(),
                GoogleLanage.ENGLISH.getValue());
    }
 
    /**
     * 英文转中文
     * 
     * @param text
     * @return
     */
    public String en2cn(final String text) {
        return translate(text, GoogleLanage.ENGLISH.getValue(),
                GoogleLanage.CHINA.getValue());
    }
 
    /**
     * google翻译
     * 
     * @param text
     *            要翻译的文本
     * @param src_lang
     *            源文本语言
     * @param target_lang
     *            目标语言
     * @return
     * @throws Exception
     */
    public String translate(final String text, final String src_lang,
            final String target_lang) {
        String result = null;
        try {
            String url = URL_TEMPLATE
                    + "&sl="
                    + src_lang
                    + "&tl="
                    + target_lang
                    + "&hl="
                    + src_lang
                    + "&dt=bd&dt=ex&dt=ld&dt=md&dt=qc&dt=rw&dt=rm&dt=ss&dt=t&dt=at&dt=sw&ie="
                    + ENCODING + "&oe=" + ENCODING
                    + "&prev=btn&ssel=0&tsel=0&q="
                    + URLEncoder.encode(text, ENCODING);
 
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            String allInfo = EntityUtils.toString(response.getEntity());
            System.out.println(allInfo);
            String resultArray[] = allInfo.split("]]")[0].split("]");
            StringBuffer resultBuffer = new StringBuffer();
            for (int i = 0; i < resultArray.length - 1; i++) {
                resultBuffer.append(resultArray[i].split("\"")[1]);
            }
            result = resultBuffer.toString();
            result = result.replace("\\n", "\r\n");
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        System.out.println(TranslateUtil.getInstance().translate("你的名字叫什么",
                GoogleLanage.CHINA.getValue(), GoogleLanage.ENGLISH.getValue()));
    }
}
