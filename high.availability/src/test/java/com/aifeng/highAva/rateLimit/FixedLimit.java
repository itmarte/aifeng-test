package com.aifeng.highAva.rateLimit;

import java.util.Random;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/4/20 15:16
 */
public class FixedLimit {
    static Random random = new Random();
    public static void main(String[] args) {
        long ll = 100 ;

        Long tmp = null;
        for (int i = 0; i < 1000;i++) {
            tmp = getNext(ll);

            long mx = ll - tmp;
            if((mx > ll * 0.03) && mx > 1) {
                System.out.println(String.format("订单金额[%d], [tmp=%d], [mx=%d]", ll, tmp, mx));
            }
        }
    }

    private static Long getNext(long ll) {
        Integer newValue = null;
        do {
            newValue = random.nextInt(100);
        } while (newValue > ll || newValue <= 0);
        return (long)newValue;
    }
}
