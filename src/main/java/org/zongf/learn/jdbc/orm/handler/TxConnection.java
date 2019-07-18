package org.zongf.learn.jdbc.orm.handler;

import java.sql.Connection;

/**数据库事务连接
 * @since 1.0
 * @author zongf
 * @created 2019-07-18 
 */
public class TxConnection {

    private Connection connection;

    private String creator;

    public TxConnection(Connection connection) {
        this.connection = connection;
    }

    public TxConnection(Connection connection, String creator) {
        this.connection = connection;
        this.creator = creator;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
