package org.zongf.learn.jdbc.orm.test;

import org.junit.Test;
import org.zongf.learn.jdbc.orm.util.DbConnUtil;

import java.sql.Connection;

/**
 * @author: zongf
 * @created: 2019-07-05
 * @since 1.0
 */
public class TestDbUtil {

    @Test
    public void test1(){
        Connection connection = DbConnUtil.getConnection(true);
        System.out.println(connection);
    }
}
