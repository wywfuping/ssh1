package com.yawei.util;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public class Strings {
    public static String toUtf8(String str){
        if(StringUtils.isNotEmpty(str)){
            try {
                return new String(str.getBytes("ISO8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("字符串转换异常！");
            }
        }
        return "";
    }
}
