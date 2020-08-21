package com.aifeng.test.threads.account.handle;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/12 16:54
 */
public interface Action<R, P> {
    /**
     * 执行前的参数检查
     *
     * @throws IllegalArgumentException
     */
    void checkParams(R p) throws IllegalArgumentException;

    /**
     * 逻辑执行
     *
     * @return
     * @throws Exception
     */
    P doAction(R p) throws Exception;
}
