package com.aifeng.account;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/11 20:05
 */
public class AccountRepository {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>(1<<8);

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public Long addBalance(String id, Long li){
        Account account = accounts.get(id);

        return account.getBalance().addAndGet(li);
    }

    public Long decreseBalance(String id, Long li){
        Account account = accounts.get(id);
        return account.getBalance().getAndAdd(-li);
    }
}
