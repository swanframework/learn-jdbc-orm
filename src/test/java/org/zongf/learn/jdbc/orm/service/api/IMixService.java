package org.zongf.learn.jdbc.orm.service.api;

/**
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public interface IMixService {

    // 模拟正常
    void success();

    // 模拟异常操作, 事务回滚
    void error();

    void show();
}
