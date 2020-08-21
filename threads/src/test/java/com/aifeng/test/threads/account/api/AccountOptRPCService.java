package com.aifeng.test.threads.account.api;

import com.aifeng.test.threads.account.Account;
import com.aifeng.test.threads.account.Money;

/**
 * @Description: 账户rpc服务
 * @author: imart·deng
 * @date: 2020/5/12 16:43
 */
public interface AccountOptRPCService {

    /**
     * 获取账户信息
     * @param accountId
     * @return
     */
    public Account getAccount(String accountId);

    /**
     *	 开户
     * @params idNo     KYC 证件号
     * @params serialId 请求一致性hash
     * @return 账号
     */
    public String createAccount(String idNo, String serialId);

    /**
     * @params value 充值金额
     *	@params accountId 账户id
     * @params serialId 请求一致性hash
     * @return 充值后的余额
     **/
    public Money topUp(Long value, String accountId, String serialId);

    /**
     * 转账功能
     * @param sourceAccountId   出账账户id
     * @param targetAccountId   目标账户id
     * @param value             充值金额，厘
     * @param serialId          请求一致性hash
     * @return 转出账号余额
     */
    public Money trans(String sourceAccountId, String targetAccountId, Long value, String serialId);
}
