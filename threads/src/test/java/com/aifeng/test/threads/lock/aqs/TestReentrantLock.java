package com.aifeng.test.threads.lock.aqs;

/**
 * @Description:
 * 知识点：
 *      1.公平锁：  FIFO加锁，如果当前临界资源被占用且没有其他线程排队那就自旋获得锁，否则排队
 *      2.非公平锁： 线程进来就直接去CAS一次获取临界资源，没有拿到则再尝试一次，拿不到就排队
 * @author: imart·deng
 * @date: 2020/5/21 17:26
 */
public class TestReentrantLock {
}
