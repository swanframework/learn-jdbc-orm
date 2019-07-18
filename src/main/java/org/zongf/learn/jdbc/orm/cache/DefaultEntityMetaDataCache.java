package org.zongf.learn.jdbc.orm.cache;

import java.util.HashMap;
import java.util.Map;

/** 默认使用jvm做缓存
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class DefaultEntityMetaDataCache implements IEntityMetaDataCache {

    private Map<Class, EntityMetadata> cacheMap = new HashMap<>();

    @Override
    public void setCache(EntityMetadata entityMetadata) {
        cacheMap.put(entityMetadata.getClz(), entityMetadata);
    }

    @Override
    public EntityMetadata getCache(Class clz) {
        return cacheMap.get(clz);
    }
}
