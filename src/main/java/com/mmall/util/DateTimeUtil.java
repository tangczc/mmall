package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @description: 格式化时间工具
 * @author: LiChen
 * @create: 2019-01-25 14:37
 */
public class DateTimeUtil {


    public static String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    //str -> date
    public static Date strToDate(String dateTiemStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTiemStr);
        return dateTime.toDate();
    }
    //date -> str
    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

//    public static void main(String[] args) {
//        System.out.println(DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateTimeUtil.strToDate("2001-01-01 11:11:11","yyyy-MM-dd HH:mm:ss"));
//    }
}