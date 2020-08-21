package com.aifeng.test.threads.account.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/5/12 16:55
 */
public interface ActionHandler<P, R> extends Action<P, R> {
    Logger logger = LoggerFactory.getLogger(ActionHandler.class);

    String LOG_REQUEST_FORMAT = "action-[{}] 请求，请求参数[{}]";
    String LOG_RESULT_FORMAT = "action-[{}] 结果, 请求参数[{}]，返回结果[{}]";
    String LOG_FAILED_FORMAT = "action-[{}] 失败，请求参数[{}]，失败编码[{}]， 失败原因[{}]";
    String LOG_ERROR_FORMAT = "action-[{}] 异常，请求参数[{}，异常原因[{}]";
    String LOG_FINALLY_FORMAT = "action-[{}] 请求耗时[{}]ms";

    default void check(P p) throws IllegalArgumentException {
        validate(p);
        checkParams(p);
    }

    default void validate(P t){
    }

    @Override
    default void checkParams(P p) throws IllegalArgumentException {

    }

    default R execute(P p, String serviceName, String userKey) {
        long start = System.currentTimeMillis();
        logger.debug(LOG_REQUEST_FORMAT, serviceName, p);
        try {
            // 参数校验
            check(p);

            // 业务处理
            R k = doAction(p);
            logger.debug(LOG_RESULT_FORMAT, serviceName, p, k);

            // transaction commit
            return k;
        }catch (Exception e) {
            logger.error(LOG_ERROR_FORMAT, serviceName, p, e.getMessage(), e);
            throw new IllegalArgumentException();
        } finally {
            logger.debug(LOG_FINALLY_FORMAT, serviceName, System.currentTimeMillis() - start);
        }
    }
}
