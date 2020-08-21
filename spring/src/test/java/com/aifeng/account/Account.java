package com.aifeng.account;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 账户
 */
public class Account{
    /**
     * 账户id
     */
    private String id;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     *   余额
     */
    private AtomicLong balance = new AtomicLong(0L);

    /**
     *  kyc证件号
     */
    private String idNo;

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

    public AtomicLong getBalance() {
        return balance;
    }

    public void setBalance(AtomicLong balance) {
        this.balance = balance;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}