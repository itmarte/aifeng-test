package com.aifeng.test.memory;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/25 16:56
 */
public class TestGC {
    /**
     * 执行次方法的时候需要在jvm参数加上 -XX:+PrintGCDetails
     * 输出如下信息：
     * Heap
     *  PSYoungGen（年轻代）      total 75776K, used 41508K [0x000000076b700000, 0x0000000770b80000, 0x00000007c0000000)
     *   eden space 65024K, 63% used [0x000000076b700000,0x000000076df89348,0x000000076f680000)
     *   from space 10752K, 0% used [0x0000000770100000,0x0000000770100000,0x0000000770b80000)
     *   to   space 10752K, 0% used [0x000000076f680000,0x000000076f680000,0x0000000770100000)
     *  ParOldGen（老年代）       total 173568K, used 0K [0x00000006c2400000, 0x00000006ccd80000, 0x000000076b700000)
     *   object space 173568K, 0% used [0x00000006c2400000,0x00000006c2400000,0x00000006ccd80000)
     *  Metaspace（元数据，jdk8）       used 3220K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 350K, capacity 388K, committed 512K, reserved 1048576K
     * @param args
     */
    public static void main(String[] args) {
//        byte[] allocation1, allocation2;
//        allocation1 = new byte[30900*1024];
//        allocation2 = new byte[900*1024];

        byte[] allocation1, allocation2,allocation3,allocation4,allocation5;
        allocation1 = new byte[32000*1024];
        allocation2 = new byte[1000*1024];
        allocation3 = new byte[1000*1024];
        allocation4 = new byte[1000*1024];
//        allocation5 = new byte[1000*1024];
        allocation5 = new byte[32000*1024];


    }
}
