package org.zongf.learn.jdbc.orm.cache;

import java.util.ArrayList;
import java.util.List;

/** 类基本信息: 类属性, 映射标明, 字段列表, 增删改查sql语句
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class EntityMetadata {

    // 实体类型
    private Class clz;

    // 实体映射表名称
    private String tableName;

    // 实体映射表字段列表/实体属性列表,属性名与表中字段名, 一一对应
    private List<String> columnNames = new ArrayList<>();

    // id列名
    private String idColumnName;

    // 插入语句
    private String insertSql;

    // 更新语句
    private String updateSql;

    // 查询语句:通过id查询
    private String findByIdSql;

    // 删除语句
    private String deleteByIdSql;

    // 查询语句:查询所有记录
    private String queryAllSql;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }

    public String getInsertSql() {
        return insertSql;
    }

    public void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }

    public String getUpdateSql() {
        return updateSql;
    }

    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }

    public String getFindByIdSql() {
        return findByIdSql;
    }

    public void setFindByIdSql(String findByIdSql) {
        this.findByIdSql = findByIdSql;
    }

    public String getDeleteByIdSql() {
        return deleteByIdSql;
    }

    public void setDeleteByIdSql(String deleteByIdSql) {
        this.deleteByIdSql = deleteByIdSql;
    }

    public String getQueryAllSql() {
        return queryAllSql;
    }

    public void setQueryAllSql(String queryAllSql) {
        this.queryAllSql = queryAllSql;
    }

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }
}
