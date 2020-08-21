package com.aifeng.test.threads.lock.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Description:  AQS定义为同步锁：
 *两个目标：
 *          1.  资源被占用：用CAS对int类型变量state表示占用状态
 *          2.  阻塞队列：   用CLH双向队列做阻塞队列
 * <p>To enqueue into a CLH lock, you atomically splice it in as new
 * tail. To dequeue, you just set the head field.
 * <pre>
 *      +------+  prev +-----+       +-----+
 * head |      | <---- |     | <---- |     |  tail
 *      +------+       +-----+       +-----+
 * </pre>
 * @author: imart·deng
 * @date: 2020/5/21 17:20
 */
public class MyAQS extends AbstractQueuedSynchronizer {
    @Override
    protected boolean tryAcquire(int arg) {
        return super.tryAcquire(arg);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return super.tryAcquireShared(arg);
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        return super.tryReleaseShared(arg);
    }
}
