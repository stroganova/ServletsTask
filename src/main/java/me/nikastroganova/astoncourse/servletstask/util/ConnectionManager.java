package me.nikastroganova.astoncourse.servletstask.util;

import me.nikastroganova.astoncourse.servletstask.exception.DatabaseDriverLoaderException;
import me.nikastroganova.astoncourse.servletstask.exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DatabaseDriverLoaderException(e);
        }
    }

    private ConnectionManager() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get((USER_KEY)),
                    PropertiesUtil.get((PASSWORD_KEY))
            );
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }
    }


}
