package com.aifeng.test.threads.account;

import java.math.BigDecimal;

public class Money {
    private final static BigDecimal THOUSAND = new BigDecimal(1000L);

    /**
     * 金额：元
     */
    private BigDecimal amount;
    private String currency;

    public Money(Long li, String currency){
        this.currency = currency;
        amount = BigDecimal.valueOf(li).divide(THOUSAND,BigDecimal.ROUND_CEILING,4);
    }

    public void setLi(long li) {
        this.amount = BigDecimal.valueOf(li).divide(THOUSAND, BigDecimal.ROUND_CEILING, 4);
    }

    public String getYuanAmount(){
        return this.amount.toString();
    }
}
