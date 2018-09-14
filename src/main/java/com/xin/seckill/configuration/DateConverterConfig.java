package com.xin.seckill.configuration;

import com.xin.utils.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 日期转换
 * @date 2018-08-11 17:03
 * @Copyright (C)2018 , Luchaoxin
 */

@Component
public class DateConverterConfig implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        try {
            return DateUtil.toDate(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("日期格式不对");
        }
    }
}