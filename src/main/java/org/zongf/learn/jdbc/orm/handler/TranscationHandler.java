package org.zongf.learn.jdbc.orm.handler;

import org.zongf.learn.jdbc.orm.anno.EnableTranscation;
import org.zongf.learn.jdbc.orm.util.DbConnUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**事务动态代理处理器
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class TranscationHandler implements InvocationHandler {

    private Object target;

    public TranscationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 获取当前数据库连接
        TxConnection txConnection = DbConnUtil.getTxConnection();

        // 保存老的连接对象
        TxConnection oldTxConnection = null;

        try {

            // 获取目标对象方法
            Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

            // 看当前方法是否开启了事务
            boolean enableTx = targetMethod.isAnnotationPresent(EnableTranscation.class);

            // 如果开启事务, 则设置当前连接为手动提交事务
            if (enableTx) {

                // 获取注解信息
                EnableTranscation annotation = targetMethod.getAnnotation(EnableTranscation.class);

                // 获取是否开启新事务
                boolean openNewTx = annotation.openNewTx();

                if (!txConnection.getConnection().getAutoCommit()) { //为false, 表示已经开启了事务
                    if (openNewTx) { // 如果需要开启的事务

                        // 保存原数据库连接
                        oldTxConnection = txConnection;

                        // 获取新的连接
                        txConnection = new TxConnection(DbConnUtil.getConnection(false), this.toString());

                        // 替换当前线程中的数据库连接
                        DbConnUtil.getLocalTxConnection().set(txConnection);

                    }
                } else { // 为true, 表示未开启事务
                    // 没有开启事务, 设置自动提交为false. 表示已经开始了事务
                    txConnection.getConnection().setAutoCommit(false);
                    txConnection.setCreator(this.toString());
                }
            }

            // 执行目标方法
            Object object = targetMethod.invoke(this.target, args);

            // 如果事务是当前handler对象创建, 那么提交事务
            if (this.toString().equals(txConnection.getCreator())) {
                txConnection.getConnection().commit();
            }

            return object;
        } catch (Exception e) {
            if (txConnection != null && this.toString().equals(txConnection.getCreator())) {
                if (txConnection.getConnection() != null && !txConnection.getConnection().isClosed()) {
                    txConnection.getConnection().rollback();
                    txConnection.getConnection().setAutoCommit(true);
                }
            }
            throw new RuntimeException("发生异常, 事务已回滚!", e);
        } finally {
            // 释放数据库连接
            if (txConnection != null && this.toString().equals(txConnection.getCreator())) {
                DbConnUtil.release(txConnection.getConnection());
            }

            // 如果新连接不为null, 则表示开启了新事务. 则回滚原连接
            if (oldTxConnection != null) {
                DbConnUtil.getLocalTxConnection().set(oldTxConnection);
            }
        }
    }

}
