package com.aifeng.test.threads.forkjoin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/27 16:23
 */
public class TestJDKStream {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Calendar> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Calendar startDay = new GregorianCalendar();
            Calendar checkDay = new GregorianCalendar();
            checkDay.setTime(startDay.getTime());//不污染入参
            checkDay.add(checkDay.DATE,i);
            list.add(checkDay);
        }

        list.stream().forEach(day ->  System.out.println(sdf.format(day.getTime())));
        System.out.println("-----------------------");

        /**
         * jdk8 foreach 实际使用的Arraylist 的Iterator，并非线程安全的
         */
        list.parallelStream().forEach(day ->  System.out.println(sdf.format(day.getTime())));
        System.out.println("-----------------------");
    }
}
