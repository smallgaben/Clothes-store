package model.connection;

import java.sql.Connection;

public class ConnectionHolder {
    private static ThreadLocal<Connection> holder=new ThreadLocal<>();

    public static void setConnection(Connection connection) {
        holder.set(connection);
    }

    public static Connection getConnection() {
        return holder.get();
    }
}
