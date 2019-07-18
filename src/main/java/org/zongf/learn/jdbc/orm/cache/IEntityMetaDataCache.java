package org.zongf.learn.jdbc.orm.cache;

/** 实体基本信息
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public interface IEntityMetaDataCache {

    /**设置实体基本信息缓存
     * @param entityMetadata 实体基本信息
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    void setCache(EntityMetadata entityMetadata);

    /** 从缓存中获取实体基本类信息
     * @param clz 实体类型
     * @return EntityMetadata 类基本信息
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    EntityMetadata getCache(Class clz);
}
