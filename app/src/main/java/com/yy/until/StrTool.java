package com.yy.until;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrTool {
    /**
     * 功能描述：检测str字符串是否为空值，null字符串或者""字符串，如果为真则返回true
     *
     * @param str
     * @return
     */
    public static boolean isnull(String str) {
        // TODO Auto-generated method stub
        if (null == str || "".equals(str) || "null".equals(str)) {
            return true;
        } else {
            return false;
        }

    }
    /**
     * 功能描述：检测str字符串是包含[字符串或者""字符串，
     *
     * @param str //
     * @return
     */
//	public static String[] StringFormate(String[] str) {
//		String[] str1 = str;
//		// TODO Auto-generated method stub
//		if (null==str1) {
//			return str1;
//		}else {
//			for(int i=0;i<str1.length;i++){
//				str1[i] = str1[i].replaceAll("[", "").replaceAll("\"", "");
//			}
//			return str1;
//		}
//
//	}

    /**
     * 功能描述：将int类型数据转换成string类型数据
     * @param value 整型数据
     * @param digit 如果需要不够长度补充全为“0”，字符串长度
     * @return
     */
    public static String IntToString(int value, int digit) {
        String strvalue = String.valueOf(value);
        if (strvalue.length() < digit) {
            for (int i = 0; i < digit - strvalue.length(); i++) {
                strvalue = "0" + strvalue;
            }
        }
        return strvalue;
    }
    /**
     * 功能描述： 检查字符串是否为null 如果为null返回：“”
     *
     * @param str
     * @return
     * @author GuXulei
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     * @since 2014年9月22日
     */
    public static String null2str(String str) {
        if (str == null || "null".equals(str) || "".equals(str)) {
            str = "";
        }
        return str;
    }

    ;

    /**
     * 功能描述： 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     * @author GuXulei
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     * @since 2014年9月22日
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String replacestr(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\"|\\[|\\]");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /*
     * 去除html中的标签
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        java.util.regex.Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try {
            String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
            String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }

    /**
     * 功能描述：utf8编码格式转为汉字
     *
     * @param strUtf8 utf8字符串
     * @return 汉字
     */
    public static String Utf8ToChinese(String strUtf8) {
        String strChinese = null;

        try {
            strChinese = new String(strUtf8.getBytes("UTF-8"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strChinese;
    }


}
