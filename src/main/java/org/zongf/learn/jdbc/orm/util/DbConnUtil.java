package org.zongf.learn.jdbc.orm.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.zongf.learn.jdbc.orm.handler.TxConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/** 数据库工具类
 * @since 1.0
 * @author zongf
 * @created 2019-07-18
 */
public class DbConnUtil {

    // c3P0配置名
    private static final String c3p0PoolName = "myC3p0Pool";

    // 配置数据源
    private static final DataSource dataSource = new ComboPooledDataSource(c3p0PoolName);

    // 配置本地连接
    private static ThreadLocal<TxConnection> txConnectionLocal = new ThreadLocal<>();

    /** 获取数据库连接
     * @param autoCommitTx 是否开启提供提交事务
     * @return Connection 数据库连接
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static Connection getConnection(boolean autoCommitTx) {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(autoCommitTx);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 获取本线程连接
     * @return: Connection 数据库连接
     * @author: zongf
     * @time: 2019-06-26 14:37:00
     * @since 1.0
     */
    public static TxConnection getTxConnection() {
        TxConnection txConnection = null;

        // 如果ThreadLocal 中连接为空, 则创建新的连接
        if (txConnectionLocal.get() == null || txConnectionLocal.get().getConnection() == null) {
            txConnection = new TxConnection(getConnection(true));
            txConnectionLocal.set(txConnection);
        } else {
            txConnection = txConnectionLocal.get();
        }
        return txConnection;
    }


    /** 获取当前线程内的数据库连接
     * @return Connection
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static Connection getLocalConnection() {
        return getTxConnection().getConnection();
    }


    /** 获取当前线程的数据库连接对象
     * @return ThreadLocal<TxConnection>
     * @since 1.0
     * @author zongf
     * @created 2019-07-18
     */
    public static ThreadLocal<TxConnection> getLocalTxConnection() {
        return txConnectionLocal;
    }

    /**
     * 当归还连接时, 需要设置自动提交事务为true.
     *
     * @param connection 关闭连接
     * @author zongf
     * @created 2019-07-04
     * @since 1.0
     */
    public static void release(Connection connection) throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

}
