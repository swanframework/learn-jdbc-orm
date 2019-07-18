package org.zongf.learn.jdbc.orm.converter;

import java.time.LocalDate;
import java.util.Date;

/** 日期类型转换器
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class DateTypeConverter {

    /** 转换对象的类型
     * @param value 值
     * @param javaType java类型
     * @return 转换后的类型
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public Object convert(Object value, Class javaType) {
        Object obj = value;

        // 如果是java 日期
        if(javaType.equals(Date.class)) {
            java.sql.Date date = (java.sql.Date) value;
            obj = date.toInstant().getEpochSecond();

            // 如果是java8 日期
        } else if(javaType.equals(LocalDate.class)){
            obj = ((java.sql.Date) value).toLocalDate();
        } else {
            obj = value;
        }
        return obj;
    }
}
