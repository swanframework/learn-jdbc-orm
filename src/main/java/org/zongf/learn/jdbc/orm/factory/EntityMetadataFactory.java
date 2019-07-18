package org.zongf.learn.jdbc.orm.factory;

import org.zongf.learn.jdbc.orm.anno.AutoIncId;
import org.zongf.learn.jdbc.orm.anno.TableName;
import org.zongf.learn.jdbc.orm.cache.EntityMetadata;

import java.lang.reflect.Field;

/** 实体基础信息工厂类
 * @since 1.0
 * @author zongf
 * @created 2019-07-18 
 */
public class EntityMetadataFactory {

    /** sql 拼接中的空格 **/
    private static String SQL_SPACE = " ";

    /**获取类的相关信息
     * @param clz 类型
     * @return EntityMetadata
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public static EntityMetadata newInstance (Class clz){

        // 创建类对象信息
        EntityMetadata metadata = new EntityMetadata();

        // 获取所有声明的属性, 不区分共有私有属性
        Field[] declaredFields = clz.getDeclaredFields();

        TableName tableNameAnno = (TableName) clz.getAnnotation(TableName.class);
        if (tableNameAnno == null) {
            throw new RuntimeException(clz.getName() + "无未使用@TableName 注解指定映射表名!");
        }

        // 设置表名
        metadata.setClz(clz);
        metadata.setTableName(tableNameAnno.value());

        for (Field declaredField : declaredFields) {
            // 添加字段
            metadata.getColumnNames().add(declaredField.getName());

            // 解析id
            if (declaredField.isAnnotationPresent(AutoIncId.class)) {
                metadata.setIdColumnName(declaredField.getName());
            }
        }

        // 初始化sql语句
        initInsertSql(metadata);
        initUpdateSql(metadata);
        initDeleteByIdSql(metadata);
        initFindByIdSql(metadata);
        initQueryAllSql(metadata);

        return metadata;
    }

    /**初始化查询所有记录sql语句
     * @param metadata 类基本信息
     * @return null
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    private static void initQueryAllSql(EntityMetadata metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(SQL_SPACE);

        for (String columnName : metadata.getColumnNames()) {
            sb.append(columnName).append(",").append(SQL_SPACE);
        }
        sb.deleteCharAt(sb.length() - 2);

        sb.append("FROM");
        sb.append(SQL_SPACE);
        sb.append(metadata.getTableName());

        metadata.setQueryAllSql(sb.toString());
    }

    /**初始化通过id查询sql语句
     * @param metadata 类基本信息
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    private static void initFindByIdSql(EntityMetadata metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(SQL_SPACE);

        for (String columnName : metadata.getColumnNames()) {
            sb.append(columnName).append(",").append(SQL_SPACE);
        }
        sb.deleteCharAt(sb.length() - 2);

        sb.append("FROM");
        sb.append(SQL_SPACE);
        sb.append(metadata.getTableName());
        sb.append(SQL_SPACE);
        sb.append("WHERE");
        sb.append(SQL_SPACE);
        sb.append(metadata.getIdColumnName());
        sb.append("=?");

        metadata.setFindByIdSql(sb.toString());
    }

    /**初始化通过id 删除sql
     * @param metadata 类基本信息
     * @return null
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    private static void initDeleteByIdSql(EntityMetadata metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM");
        sb.append(SQL_SPACE);
        sb.append(metadata.getTableName());
        sb.append(SQL_SPACE);
        sb.append("WHERE");
        sb.append(SQL_SPACE);
        sb.append(metadata.getIdColumnName());
        sb.append("=?");

        metadata.setDeleteByIdSql(sb.toString());
    }

    /**初始化更新sql语句
     * @param metadata 类基本信息
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    private static void initUpdateSql(EntityMetadata metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE");
        sb.append(SQL_SPACE);
        sb.append(metadata.getTableName());
        sb.append(SQL_SPACE);
        sb.append("set");
        sb.append(SQL_SPACE);
        for (String column : metadata.getColumnNames()) {
            sb.append(column).append("=?, ");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("WHERE");
        sb.append(SQL_SPACE);
        sb.append(metadata.getIdColumnName());
        sb.append("=?");

        metadata.setUpdateSql(sb.toString());
    }

    /**初始化插入sql 语句
     * @param metadata 类基本信息
     * @return null
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    private static void initInsertSql(EntityMetadata metadata) {

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO");
        sb.append(SQL_SPACE);
        sb.append(metadata.getTableName());
        sb.append("(");
        for (String columnName : metadata.getColumnNames()) {
            sb.append(columnName).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");
        sb.append(SQL_SPACE);
        sb.append("VALUES(");

        for (int i = 0; i < metadata.getColumnNames().size(); i++) {
            sb.append("?, ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");

        metadata.setInsertSql(sb.toString());
    }
}
