package org.zongf.learn.jdbc.orm.test;

import org.junit.Test;
import org.zongf.learn.jdbc.orm.po.UserPO;
import org.zongf.learn.jdbc.orm.util.DbConnUtil;
import org.zongf.learn.jdbc.orm.util.ResultSetUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: zongf
 * @created: 2019-07-18
 * @since 1.0
 */
public class ResultParserUtilTest {

    // 测试转换单个对象为Map 结构
    @Test
    public void toPropertyMap() throws SQLException {

        String str = "select id uId, name uName, password from t_user where id = 1001";

        Connection connection = DbConnUtil.getConnection(true);

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(str);

        LinkedHashMap<String, Object> map = ResultSetUtil.toPropertyMap(resultSet);
        map.forEach((key, val) -> System.out.println(key + ":" + val));
    }

    // 测试转换对象为单个bean
    @Test
    public void toBean() throws SQLException {

        String str = "select * from t_user where id = 1002";

        Connection connection = DbConnUtil.getConnection(true);

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(str);

        UserPO userPO = ResultSetUtil.toBean(resultSet, UserPO.class);

        System.out.println(userPO);
    }

    // 测试转换对象为bean列表
    @Test
    public void toBeans() throws SQLException {

        String str = "select * from t_user ";

        Connection connection = DbConnUtil.getConnection(true);

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(str);

        List<UserPO> userPOList = ResultSetUtil.toBeans(resultSet, UserPO.class);

        userPOList.forEach(System.out::println);
    }




}
