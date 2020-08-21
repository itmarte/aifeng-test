package com.aifeng.account;

import java.io.Serializable;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/11 20:32
 */
public interface IdempotencyRequest extends Serializable {
    /**
     * 幂等id
     */
    public IdempotencyRequest getByIdempotentId(String idempotencyId);

    /**
     * 设置idempotencyId（请求唯一幂等号）
     * @param idempotencyId
     */
    public void setIdempotencyId(String idempotencyId);
}
