package com.aifeng.test.threads.account.impl;

import com.aifeng.test.threads.account.Account;
import com.aifeng.test.threads.account.Money;
import com.aifeng.test.threads.account.api.AccountOptRPCService;
import com.aifeng.test.threads.account.handle.ActionHandler;
import com.aifeng.test.threads.account.respository.AccountRepository;

/**
 * @Description: AccountOptRPCService
 * @author: imart·deng
 * @date: 2020/5/12 16:49
 */
public class AccountOptRPCServiceImpl implements AccountOptRPCService {
    AccountRepository accountRepository = new AccountRepository();
    @Override
    public Account getAccount(String accountId) {
        return null;
    }

    @Override
    public String createAccount(String idNo, String serialId) {
        return ((ActionHandler<String, String>) p -> {
            Account account = new Account();
            account.setIdNo(p);
            accountRepository.addAccount(account);
            return account.getId();
        }).execute(idNo, "创建账户", serialId);
    }

    @Override
    public Money topUp(final Long value, String accountId, String serialId) {
        return ((ActionHandler<String, Money>) p -> {
            Account account = accountRepository.getAccount(p);
            if(null == account) throw new IllegalArgumentException("accountId is null.");
            account.getBalance().getAndAdd(value);
            return new Money(account.getBalance().get(), "CNY");
        }).execute(accountId, "充值", serialId);
    }

    @Override
    public Money trans(String sourceAccountId, final String targetAccountId, final Long value, String serialId) {
        return ((ActionHandler<String, Money>) p -> {
            Account source = accountRepository.getAccount(p);
            Account target = accountRepository.getAccount(targetAccountId);
            if(null == source || null == target){
                throw new IllegalArgumentException("account not exist.");
            }

            return null;
        }).execute(sourceAccountId, "转账", serialId);
    }
}
