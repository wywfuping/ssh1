package com.yawei.util;


import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

public class SearchParam {

    private String type;
    private String protertyName;
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProtertyName() {
        return protertyName;
    }

    public void setProtertyName(String protertyName) {
        this.protertyName = protertyName;
    }

    public static List<SearchParam> buildSearchParm(HttpServletRequest request){
        List<SearchParam> searchParamList = Lists.newArrayList();
        Enumeration<String> enumeration = request.getParameterNames();

        while(enumeration.hasMoreElements()){
            String queryString = enumeration.nextElement();
            Object value = request.getParameter(queryString);
            //q_f_ge_bookprice
            if(queryString.startsWith("q_")&& value!=null && StringUtils.isNotEmpty(value.toString())){
                String[] array = queryString.split("_",4);
                if(array.length!=4){
                    throw new RuntimeException("地址栏格式错误"+queryString);
                }
                String type= array[2];
                String protertyName=array[3];
                String valueType=array[1];

                SearchParam searchParam = new SearchParam();
                searchParam.setProtertyName(protertyName);
                searchParam.setType(type);

                value = converterType(value,valueType);
                searchParam.setValue(value);
                request.setAttribute(queryString,value);

                searchParamList.add(searchParam);
            }
        }
        return searchParamList;
    }

    private static Object converterType(Object value,String valueType){
        if("i".equalsIgnoreCase(valueType)){
            return Integer.valueOf(value.toString());
        }else if("s".equalsIgnoreCase(valueType)){
            return Strings.toUtf8(value.toString());
        }else if("d".equalsIgnoreCase(valueType)){
            return Double.valueOf(value.toString());
        }else if("f".equalsIgnoreCase(valueType)){
            return Float.valueOf(value.toString());
        }else if("b".equalsIgnoreCase(valueType)){
            return Boolean.valueOf(value.toString());
        }
        return null;
    }
}
