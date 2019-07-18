package org.zongf.learn.jdbc.orm.util;

import org.zongf.learn.jdbc.orm.cache.DefaultEntityMetaDataCache;
import org.zongf.learn.jdbc.orm.cache.EntityMetadata;
import org.zongf.learn.jdbc.orm.cache.IEntityMetaDataCache;
import org.zongf.learn.jdbc.orm.factory.EntityMetadataFactory;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

/** 单表实体管理器
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class EntityManager {

    private static Logger logger = Logger.getLogger(EntityManager.class.toString());

    // 缓存,默认使用jvm内存做缓存
    private static IEntityMetaDataCache metaDataCache = new DefaultEntityMetaDataCache();

    /** 保存实体
     * @param entity 数据库实体
     * @return true: 保存成功, false: 保存失败
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static boolean save(Object entity) {
        // 获取entity 信息
        EntityMetadata entityMetadata = getEntityMetadata(entity.getClass());

        // 获取数据库连接
        Connection connection = DbConnUtil.getLocalConnection();

        // 自增主键回写结果集
        ResultSet callbackKeyRs;

        // sql 执行影响行数
        int cnt = 0;

        try {
            // 获取预编译sql语句
            PreparedStatement preparedStatement = connection.prepareStatement(entityMetadata.getInsertSql(), Statement.RETURN_GENERATED_KEYS);

            // 设置参数
            for (int i = 1; i <= entityMetadata.getColumnNames().size(); i++) {
                String columnName = entityMetadata.getColumnNames().get(i - 1);
                Object value = ReflectUtil.getPropertyValue(entity, columnName);
                preparedStatement.setObject(i, value);
            }

            // 执行插入语句
            cnt = preparedStatement.executeUpdate();

            // 回写自增主键
            callbackKeyRs = preparedStatement.getGeneratedKeys();
            if (callbackKeyRs.next()) {
                int id = callbackKeyRs.getInt(1);
                ReflectUtil.setPropertyValue(entity, entityMetadata.getIdColumnName(), id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 返回成功或失败
        return cnt > 0;
    }

    /**通过id 删除实体
     * @param clz 实体类型
     * @param id 主键id
     * @return true: 删除成功, false:删除失败
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public static <T> boolean deleteById(Class<T> clz, Integer id) {
        // 获取entity 信息
        EntityMetadata entityMetadata = getEntityMetadata(clz);

        // 获取数据库连接
        Connection connection = DbConnUtil.getLocalConnection();

        // sql 执行影响行数
        int cnt = 0;

        try {
            // 获取PreparedStatement 并设置参数
            PreparedStatement preparedStatement = connection.prepareStatement(entityMetadata.getDeleteByIdSql());
            preparedStatement.setInt(1, id);

            // 执行sql
            cnt = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnt >0;
    }

    /**更新实体
     * @param entity 实体
     * @return true: 更新成功, false:更新失败
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public static boolean update(Object entity) {

        // 获取entity 信息
        EntityMetadata entityMetadata = getEntityMetadata(entity.getClass());

        // 获取数据库连接
        Connection connection = DbConnUtil.getLocalConnection();

        // sql 执行影响行数
        int cnt = 0;

        try {
            // 获取PreparedStatement 并设置参数
            PreparedStatement preparedStatement = connection.prepareStatement(entityMetadata.getUpdateSql());

            // 设置参数
            for (int i = 1; i <= entityMetadata.getColumnNames().size(); i++) {
                String columnName = entityMetadata.getColumnNames().get(i - 1);
                Object value = ReflectUtil.getPropertyValue(entity, columnName);
                preparedStatement.setObject(i, value);
            }
            Object pkValue = ReflectUtil.getPropertyValue(entity, entityMetadata.getIdColumnName());
            preparedStatement.setObject(entityMetadata.getColumnNames().size()+1,pkValue);

            // 执行sql
            cnt = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnt > 0;
    }

    /**通过主键id查询
     * @param clz 返回类型
     * @param id 主键id
     * @return T
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public static <T> T findById(Class<T> clz, Integer id) {

        // 获取entity 信息
        EntityMetadata entityMetadata = getEntityMetadata(clz);

        // 获取数据库连接
        Connection connection = DbConnUtil.getLocalConnection();

        // sql执行成功标识
        T t = null;

        try {
            // 获取PreparedStatement 并设置参数
            PreparedStatement preparedStatement = connection.prepareStatement(entityMetadata.getFindByIdSql());
            preparedStatement.setInt(1, id);

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            // 结果集解析
            t = ResultSetUtil.toBean(resultSet, clz);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**通过主键id查询
     * @param clz 返回类型
     * @return T
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public static <T> List<T> queryAll(Class<T> clz) {

        // 获取entity 信息
        EntityMetadata entityMetadata = getEntityMetadata(clz);

        // 获取数据库连接
        Connection connection = DbConnUtil.getLocalConnection();

        // sql执行成功标识
        List<T> list = null;

        try {
            // 获取PreparedStatement 并设置参数
            PreparedStatement preparedStatement = connection.prepareStatement(entityMetadata.getQueryAllSql());

            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();

            // 结果集解析
            list = ResultSetUtil.toBeans(resultSet, clz);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**获取类相关信息
     * @param clz 类类型
     * @return EntityMetadata 类信息
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    private static EntityMetadata getEntityMetadata(Class clz) {

        // 尝试从缓存中获取
        EntityMetadata entityMetadata = metaDataCache.getCache(clz);

        // 如果缓存中entityMetadata为空, 则解析并存入缓存
        if (entityMetadata == null) {
            entityMetadata = EntityMetadataFactory.newInstance(clz);
            metaDataCache.setCache(entityMetadata);

            logger.info("获取实体信息:-第一次解析");
        }else {
            logger.info("获取实体信息:-从缓存中获取");
        }

        return entityMetadata;
    }
}
