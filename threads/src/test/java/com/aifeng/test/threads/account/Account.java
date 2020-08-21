package com.aifeng.test.threads.account;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: 账务
 * @author: imart·deng
 * @date: 2020/5/12 16:38
 */
public class Account {
    /**
     * 账户id
     */
    private String id;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 名称
     */
    private String name;

    /**
     * 余额，厘
     */
    private volatile AtomicLong balance = new AtomicLong(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AtomicLong getBalance() {
        return balance;
    }

    public void setBalance(AtomicLong balance) {
        this.balance = balance;
    }
}
