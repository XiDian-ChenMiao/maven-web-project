package com.seckill.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件描述：自定义日期转换器
 * 创建作者：陈苗
 * 创建时间：2016年6月3日 15:31
 */
public class CustomDateConverter implements Converter<String,Date> {
    public Date convert(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(s);//如果参数绑定成功，则直接返回
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//如果参数绑定失败，则返回null
    }
}
