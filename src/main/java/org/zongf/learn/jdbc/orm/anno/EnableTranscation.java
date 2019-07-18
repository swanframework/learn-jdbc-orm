package org.zongf.learn.jdbc.orm.anno;

import java.lang.annotation.*;

/** 启用事务注解
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableTranscation {

    // 是否开启新的事务
    boolean openNewTx() default false;
}
