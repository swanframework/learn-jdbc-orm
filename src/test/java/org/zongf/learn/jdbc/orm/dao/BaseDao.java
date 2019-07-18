package org.zongf.learn.jdbc.orm.dao;

import org.zongf.learn.jdbc.orm.util.EntityManager;

import java.lang.reflect.ParameterizedType;
import java.util.List;


/** 基础Dao
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class BaseDao<T> {

    private Class<T> clz ;

    public BaseDao() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    /**保存实体
     * @param entity 任意实体
     * @return true: 保存成功, false:保存失败
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public boolean save(Object entity){
        return EntityManager.save(entity);
    }

    /**根据id删除实体
     * @param id 主键id
     * @return true: 保存成功, false:保存失败
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public boolean deleteById(Integer id) {
        return EntityManager.deleteById(clz, id);
    }

    /**更新实体
     * @param entity 数据库实体
     * @return true: 保存成功, false:保存失败
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public boolean update(Object entity) {
        return EntityManager.update(entity);
    }

    /** 根据id查询
     * @param id 主键id
     * @return T
     * @since 1.0
     * @author zongf
     * @created 2019-07-18 
     */
    public T findById(Integer id) {
        return EntityManager.findById(clz, id);
    }

    /** 查询所有实体
     * @return 数据库所有记录
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public List<T> queryAll() {
        return EntityManager.queryAll(clz);
    }
}
