package com.aifeng.test.threads.account.respository;

import com.aifeng.test.threads.account.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: 账户
 * @author: imart·deng
 * @date: 2020/5/12 16:50
 */
public class AccountRepository {
    /**
     * 账户
     */
    private static final Map<String, Account> accounts = new ConcurrentHashMap<>(1<<8);

    /**
     * 全局唯一序列号
     */
    private static final AtomicLong COUNTER = new AtomicLong(100001L);

    public Account getAccount(String id){
        return accounts.get(id);
    }

    public void addAccount(Account account){
        if(null == account) return;
        if(null == account.getId()){
            String id = COUNTER.getAndAdd(1)+"";
            account.setId(id);
            account.setAccountNo(id);
        }
        accounts.put(account.getId(), account);
    }

}
