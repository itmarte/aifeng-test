package com.aifeng.account;

import java.math.BigDecimal;

public class Money {
    private final static BigDecimal THOUSAND = new BigDecimal(1000L);
    private BigDecimal amount;
    private String currency;

    public Money(Long li, String currency){
        this.currency = currency;
        amount = BigDecimal.valueOf(li).divide(THOUSAND,BigDecimal.ROUND_CEILING,4);
    }
}
