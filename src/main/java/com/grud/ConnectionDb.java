package com.grud;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionDb {

    @SneakyThrows
    public static Connection getConnection() {
        Properties propertiesDb = new Properties();
        propertiesDb.load(new FileInputStream("src/main/resources/config.properties"));
        String host = propertiesDb.getProperty("db.host");
        String login = propertiesDb.getProperty("db.login");
        String password = propertiesDb.getProperty("db.password");
        return DriverManager.getConnection(host, login, password);
    }
}
