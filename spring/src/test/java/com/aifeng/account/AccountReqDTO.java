package com.aifeng.account;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/11 20:00
 */
public class AccountReqDTO {

    /**
     * 账户号
     */
    private String accountNo;

    /**
     *  卡号
     */
    private String cardNo;


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}