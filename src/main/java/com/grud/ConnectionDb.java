package com.grud;

import lombok.SneakyThrows;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDb {
    private static final String url = "jdbc:mysql://localhost/freelancers_site?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(url, user, password);
    }
}
