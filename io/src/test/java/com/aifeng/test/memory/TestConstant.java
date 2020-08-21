package com.aifeng.test.memory;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/25 15:55
 */
public class TestConstant {
    public static void main(String[] args) {

        /**
         * 直接使用双引号声明出来的 String 对象会直接存储在常量池中
         * String s1 = new String("abc");这句话创建了几个字符串对象?
         * 将创建 1 或 2 个字符串。如果池中已存在字符串常量“abc”，则只会在堆空间创建一个字符串常量“abc”。如果池中没有字符串常量“abc”，那么它将首先在池中创建，然后在堆空间中创建，因此将创建总共 2 个字符串对象。
         * new 出来的string固定引用的是堆内存，“”出来的是常量池，所以new出来的跟固定的一定不相等
         */
        System.out.println("------************ 字符串常量 ************------");
        String a1 = new String("abc");
        String a2 = "abc";
        String a3 = "abc";
        // 优先从常量池获取字符串 abc
        String a4 = a1.intern();
        System.out.println("a1==a2 ? " + (a1 == a2)); // a1==a2 ? false
        System.out.println("a2==a3 ? " + (a3 == a2)); // a2==a3 ? true
        System.out.println("a3==a4 ? " + (a3 == a4)); // a3==a4 ? true
        System.out.println("------************ 字符串常量 ************------");
        System.out.println();

        /**
         * Java 基本类型的包装类的大部分都实现了常量池技术
         * 即 Byte,Short,Integer,Long: 缓存范围为[-128,127] ；Character,Boolean: 缓存范围为[0,127]
         * 两种浮点数类型的包装类 Float,Double 并没有实现常量池技术
         */
        System.out.println("------************ 基础类型常量 ************------");
        Integer i1 = 33;
        Integer i2 = 33;
        Integer i3 = 333;
        Integer i4 = 333;
        int i5 = 333;
        int i6 = 333;
        System.out.println("i1==i2 ? " + (i1 == i2));// i1==i2 ? true
        System.out.println("i3==i4 ? " + (i3 == i4));// i3==i4 ? false
        System.out.println("i5==i6 ? " + (i5 == i6)); // i5==i6 ? true 基础类型是直接内存

        Integer ii1 = 40;
        Integer ii2 = 40;
        Integer ii3 = 0;
        Integer ii4 = new Integer(40);
        Integer ii5 = new Integer(40);
        Integer ii6 = new Integer(0);
        System.out.println("ii1==ii2 ? " + (ii1 == ii2) );
        System.out.println("ii1==ii2+ii3 ? " + (ii1 == ii2+ii3) );
        System.out.println("ii4==ii5 ? " + (ii4 == ii5) );
        // + 操作不适合 Integer，所以 + 两边的值会自动装箱成 int类型，所以 ii5+ii6 = (int)40
        // 然后 ii4==40，ii4跟int类型比较会自动装箱到int类型
        System.out.println("ii4==ii5+ii6 ? " + (ii4 == ii5 + ii6) );
        System.out.println("------************ 基础类型常量 ************------");
        System.out.println();


    }
}
