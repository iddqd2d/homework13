package com.grud;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Grud {
    private Connection connection;

    public Grud() {
        connection = ConnectionDb.getConnection();
    }

    @SneakyThrows
    public void createData(String tableName, String[] values) {
        StringJoiner stringJoiner = new StringJoiner(",");
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO " + tableName + " VALUES(NULL, " + stringJoiner + ");");
    }

    @SneakyThrows
    public List readData(String tableName) {
        List<String> list = new ArrayList();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM " + tableName);
        int columns = result.getMetaData().getColumnCount();
        while (result.next()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i <= columns; i++) {
                stringBuilder.append(result.getString(i)).append("|");
            }
            list.add(stringBuilder.toString());
        }
        return list;
    }

    @SneakyThrows
    public void deleteData(String tableName, String column, String value) {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE " + column + " = ?;");
        preparedStatement.setString(1, value);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @SneakyThrows
    public void updateData(String tableName, String updateColumn, String updateValue, String conditionColumn, String conditionValue) {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + tableName + " SET " + updateColumn + " = ? WHERE " + conditionColumn + " LIKE ?;");
        preparedStatement.setString(1, updateValue);
        preparedStatement.setString(2, conditionValue);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @SneakyThrows
    public void closeConnection(){
        connection.close();
    }
}
