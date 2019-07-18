package org.zongf.learn.jdbc.orm.util;

import org.zongf.learn.jdbc.orm.converter.DateTypeConverter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/** 结果集解析工具类
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class ResultSetUtil {

    /** 转换单行结果集为Map结构. key为列别名, value为列值
     * @param resultSet 查询结果集
     * @return 结果集中为空时, 返回null
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static LinkedHashMap<String, Object> toPropertyMap(ResultSet resultSet) throws SQLException {

        // 如果结果集为空,则返回null
        if (!resultSet.next()) return null;

        LinkedHashMap<String, Object> cloumnMap = new LinkedHashMap<>();

        // 获取结果集元信息
        ResultSetMetaData metaData = resultSet.getMetaData();

        // 获取每一列列名与值
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            // 获取列别名为key
            String columnLabel = metaData.getColumnLabel(i);
            Object columnValue = resultSet.getObject(i);
            cloumnMap.put(columnLabel, columnValue);
        }
        return cloumnMap;
    }

    /** 转换结果集为单行对象
     * @param clz       目标对象类型
     * @param resultSet 结果集
     * @since 1.0
     * @return null
     * @author zongf
     * @created 2019-07-18
     */
    public static <T> T toBean(ResultSet resultSet, Class<T> clz) {
        try {
            LinkedHashMap<String, Object> propertyMap = toPropertyMap(resultSet);
            return ReflectUtil.newInstance(clz, propertyMap, new DateTypeConverter());
        } catch (SQLException e) {
            throw new RuntimeException("sql 执行异常!", e);
        }
    }

    /** 转换结果集为java对象集合.
     * @param clz       要转换的javaBean类
     * @param resultSet 结果集
     * @return 结果集中没有数据时, 返回null
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static <T> List<T> toBeans(ResultSet resultSet, Class<T> clz) throws SQLException {

        List<T> list = new ArrayList<>();

        LinkedHashMap<String, Object> propertyMap = null;

        // 解析结果集
        while ((propertyMap = toPropertyMap(resultSet)) != null) {
            T t = (T) ReflectUtil.newInstance(clz, propertyMap, new DateTypeConverter());
            if(t != null) list.add(t);
        }
        return list.size() > 0 ? list : null;
    }

}
