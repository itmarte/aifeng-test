package com.aifeng.account;

import java.io.Serializable;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/11 20:00
 */
public class AccountDTO implements Serializable {
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
    private Long balance;

    /**
     *  卡号
     */
    private String cardNo;

}