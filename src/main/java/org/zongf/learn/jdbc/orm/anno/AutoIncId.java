package org.zongf.learn.jdbc.orm.anno;

import java.lang.annotation.*;

/** 标识自增id
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIncId {
    String value();
}
