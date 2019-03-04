package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @description:
 * @author: LiChen
 * @create: 2019-03-04 14:22
 */
public class BigDecimalTest {
    @Test
    public void test1() {
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.2 / 100);
    }
    @Test
    public void test2() {
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }

    @Test
    public void test3() {
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
    }
    @Test
    public void test4(){
        Double i = 10.544654;
        System.out.println(i.toString());
    }
}