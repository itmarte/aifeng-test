package com.aifeng.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 账户操作RPC服务
 */
public class AccountOptRPCServiceIml implements AccountOptRPCService{
    private final Logger logger = LoggerFactory.getLogger(AccountOptRPCServiceIml.class);

    /**
     * 账户存储
     */
    private final Map<String, Account> accounts = new ConcurrentHashMap<>(1<<8);

    /**
     * 幂等唯一key存储
     */
    private final Map<String, Object> requestIds = new ConcurrentHashMap<>(1<<8);
    public static final String CNY = "CNY";
    private static final Object ACCOUNT_MUTEX = new Object();
    private static final Object TOP_UP_MUTEX = new Object();
    private static final Object TRANS_MUTEX = new Object();
    private static final Object EMPTY = new Object();
    /**
     * 全局唯一序列号
     */
    private static final AtomicLong COUNTER = new AtomicLong(100001L);
    /**
     * 没有统一result和统一exception用null表示业务失败或异常
      */

    @Override
    public String createAccount(String idNo, String serialId) {
        try {

            // 参数校验
            if(StringUtils.isEmpty(idNo)|| StringUtils.isEmpty(serialId)){
                return null;
            }

            // 幂等判定
            if(null != idempotentRequest("createAccount:" + serialId, ACCOUNT_MUTEX)){
                return (String)idempotentRequest("createAccount:" + serialId, ACCOUNT_MUTEX);
            }

            Account account = new Account();
            String id = COUNTER.getAndAdd(1L)+"";
            account.setAccountNo(id);
            account.setIdNo(idNo);
            account.setId(id);
            try {
                accounts.put(id, account);

                // 完善幂等请求结果
                idempotentRequestPut(serialId, id);
            } catch (Exception e) {
                // 失败释放锁
                idempotentRequestRelease("createAccount:" + serialId);
            }

            return id;
        } catch (Exception e) {
            logger.error("[开户]失败，请求参数[idNo={}, serialId={}], 失败原因:", idNo, serialId, e);
            return null;
        }
    }

    @Override
    public Money topUp(Long value, String accountId, String serialId) {

        try {
            if(null == value || StringUtils.isEmpty(accountId) || StringUtils.isEmpty(serialId)){
                return null;
            }

            if(null != idempotentRequest("topUp:" + serialId, TOP_UP_MUTEX)){
                return (Money) idempotentRequest("topUp:" + serialId, TOP_UP_MUTEX);
            }

            // 查看账户是否存在
            Account account = accounts.get(accountId);
            if(null == account){
                return null;
            }

            // 充值并获取余额
            Long sourceBalance = account.getBalance().get();
            try {
                Long balance = account.getBalance().addAndGet(value);
                Money money = new Money(balance, CNY);

                // 完善幂等请求结果
                idempotentRequestPut(serialId, money);
                return money;
            } catch (Exception e) {
                // 失败释放锁
                idempotentRequestRelease("topUp:" + serialId);
                // 回滚
                account.getBalance().set(sourceBalance);
                return null;
            }
        } catch (Exception e) {
            logger.error("[充值]失败，请求参数[value={}, accountId={}, serialId={}], 失败原因:", value, accountId, serialId, e);
            return null;
        }
    }

    @Override
    public Money trans(String sourceAccountId, String targetAccountId, Long value, String serialId) {

        if(StringUtils.isEmpty(sourceAccountId) || StringUtils.isEmpty(targetAccountId) || StringUtils.isEmpty(serialId) || null == value){
            return null;
        }

        if(null != idempotentRequest("trans:" + serialId, TRANS_MUTEX)){
            return (Money) idempotentRequest("trans:" + serialId, TRANS_MUTEX);
        }

        Account source = accounts.get(sourceAccountId);
        Account target = accounts.get(targetAccountId);

        if(null == source || null == target){
            return null;
        }

        // 余额校验
        if(source.getBalance().get() < value){
            // 没有统一result和统一exception用null表示异常
            return null;
        }

        Account first;
        Account second;
        if(sourceAccountId.compareTo(serialId) > 0){
            first = source;
            second = target;
        }else {
            first = target;
            second = source;
        }

        // 开始转账，只有转账有加锁，所以简单加顺序加锁,防止死锁  source--->target
        synchronized (first){
            synchronized (second){
                // 余额校验
                if(source.getBalance().get() < value){
                    // 没有统一result和统一exception用null表示异常
                    return null;
                }

                // 记录初始值
                long sourceLi = source.getBalance().get();
                long targetLi = target.getBalance().get();

                try {
                    // 原账户扣钱
                    long balance = source.getBalance().addAndGet(-value);

                    // 目标账户加款
                    target.getBalance().addAndGet(value);
                    Money money = new Money(balance, CNY);
                    // 完善幂等请求结果
                    idempotentRequestPut(serialId, money);
                    return money;
                } catch (Exception e) {
                    // 异常，还原两个账户
                    source.getBalance().set(sourceLi);
                    target.getBalance().set(targetLi);
                    // 失败释放锁
                    idempotentRequestRelease("trans:" + serialId);
                    logger.error("[转账失败]失败，请求参数[sourceAccountId={}, targetAccountId={}, value={}， serialId={}], 失败原因:", sourceAccountId, targetAccountId, value, serialId, e);
                }
            }
        }

        return null;
    }

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

    private Object idempotentRequest(String serialId, Object mutex) {
        Object account = requestIds.get(serialId);
        if(null == account){
            synchronized (mutex){
                if(null != requestIds.get(serialId)){
                    return requestIds.get(serialId);
                }
                // 预占
                requestIds.put(serialId, EMPTY);
            }
        }
        return account;
    }

    private void idempotentRequestRelease(String serialId) {
        requestIds.remove(serialId);
    }

    private void idempotentRequestPut(String serialId, Object value){
        requestIds.put(serialId, value);
    }
}
