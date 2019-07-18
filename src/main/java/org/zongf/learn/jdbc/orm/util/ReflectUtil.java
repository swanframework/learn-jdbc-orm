package org.zongf.learn.jdbc.orm.util;

import org.zongf.learn.jdbc.orm.converter.DateTypeConverter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/** 反射工具类
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class ReflectUtil {

    /**为对象属性赋值
     * @param target 目标对象
     * @param property 属性名
     * @param property 属性名
     * @return value 属性值
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static void setPropertyValue(Object target, String property, Object value) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
            Method writeMethod = descriptor.getWriteMethod();
            writeMethod.invoke(target, value);
        } catch (Exception e) {
            throw new RuntimeException("为对象属性赋值异常!",e);
        }
    }

    /** 获取对象属性值
     * @param target 目标对象
     * @param property 属性
     * @return Object 返回对象属性值
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static Object getPropertyValue(Object target, String property) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
            Method readMethod = descriptor.getReadMethod();
            return readMethod.invoke(target);
        } catch (Exception e) {
            throw new RuntimeException("获取对象属性异常!",e);
        }
    }

    /** 反射创建对象
     * @param clz 目标对象的类型
     * @return propertiesMap 目标对象的属性与值
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static <T> T newInstance(Class<T> clz, HashMap<String, Object> propertiesMap, DateTypeConverter typeConverter){

        // 如果属性为空, 则不进行创建, 返回null
        if (propertiesMap == null || propertiesMap.isEmpty()) {
            return null;
        }

        // 使用无参数构造方法创建对象
        T t = null;
        try {
            t = clz.newInstance();
            for (Map.Entry<String, Object> entry : propertiesMap.entrySet()) {

                // 获取对象属性与值
                String property = entry.getKey();
                Object value = entry.getValue();

                // 获取属性描述符
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(property, clz);
                // 获取属性类型
                Class<?> propertyType = propertyDescriptor.getPropertyType();

                // 使用类型转换器转换参数类型
                value = typeConverter.convert(value, propertyType);

                // 调用set方法, 赋值
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(t, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("反射创建对象失败!", e);
        }
        return t;
    }

}
