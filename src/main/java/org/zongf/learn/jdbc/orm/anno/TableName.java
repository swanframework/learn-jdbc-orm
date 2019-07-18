package org.zongf.learn.jdbc.orm.anno;

import java.lang.annotation.*;

/**
 * @Description: 标识数据库表名
 * @since 1.0
 * @author: zongf
 * @date: 2019-06-26 16:27:35
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {
    String value();
}
