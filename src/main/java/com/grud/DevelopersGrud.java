package com.grud;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DevelopersGrud extends Grud {
    private static final String ADD_NEW_DEVELOPER_SQL_QUERY = "INSERT INTO `developers` VALUES(NULL,? ,? ,? ,? );";
    private static final String READ_ALL_DATA_SQL_QUERY = "SELECT * FROM `developers`;";
    private static final String DELETE_BY_ID_SQL_QUERY = "DELETE FROM `developers` WHERE `id` = ?;";
    private static final String CHANGE_SALARY_DEVELOPER_SQL_QUERY = "UPDATE `developers` SET `salary` = ? WHERE `name` = ?;";

    @SneakyThrows
    public int createData(String name, int age, boolean sex, int salary) {
        Connection connection = ConnectionDb.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_DEVELOPER_SQL_QUERY);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setBoolean(3, sex);
        preparedStatement.setInt(4, salary);
        int rowInserted = preparedStatement.executeUpdate();
        close(preparedStatement);
        close(connection);
        return rowInserted;
    }

    @SneakyThrows
    @Override
    public List readAllData() {
        List<String> list = new ArrayList();
        Connection connection = ConnectionDb.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(READ_ALL_DATA_SQL_QUERY);
        while (result.next()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(result.getString("name")).append("|")
                    .append(result.getString("age")).append("|")
                    .append(result.getString("sex")).append("|")
                    .append(result.getString("salary")).append("|");
            list.add(stringBuilder.toString());
        }
        close(statement);
        close(connection);
        return list;
    }

    @SneakyThrows
    @Override
    public int deleteDataById(Integer id) {
        Connection connection = ConnectionDb.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL_QUERY);
        preparedStatement.setInt(1, id);
        int rowDeleted = preparedStatement.executeUpdate();
        close(preparedStatement);
        close(connection);
        return rowDeleted;
    }

    @SneakyThrows
    public int updateData(String name, int salary) {
        Connection connection = ConnectionDb.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_SALARY_DEVELOPER_SQL_QUERY);
        preparedStatement.setInt(1, salary);
        preparedStatement.setString(2, name);
        int rowUpdated = preparedStatement.executeUpdate();
        close(preparedStatement);
        close(connection);
        return rowUpdated;
    }
}
