package org.zongf.learn.jdbc.orm.factory;


import org.zongf.learn.jdbc.orm.anno.EnableTranscation;
import org.zongf.learn.jdbc.orm.handler.TranscationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/** Service工厂, 模拟spring 容器
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class ServiceFactory {

    /** 获取Service 实例
     * @param clz Service 实现类类型
     * @return T Service 对象或动态代理对象
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static <T> T getService(Class<T> clz) {

        T t = null;
        try {
            t = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建对象失败");
        }

        // 判断不能是接口, 接口不能创建实现类
        if(clz.isInterface()){
            throw new RuntimeException("接口不能创建实例!");
        }

        // 是否开启动态代理
        boolean enableTx = false;

        // 遍历所有非私有方法, 如果方法有@EnableTx注解, 则说明需要创建代理
        Method[] methods = clz.getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(EnableTranscation.class) != null) {
                enableTx = true;
                break;
            }
        }

        // 如果需要创建代理, 则返回代理对象
        if (enableTx) {
            return (T) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), new TranscationHandler(t));
        }
        return t;
    }

}
