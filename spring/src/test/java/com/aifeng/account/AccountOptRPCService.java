package com.aifeng.account;

/**
 * 账户操作RPC
 */
public interface AccountOptRPCService {
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
