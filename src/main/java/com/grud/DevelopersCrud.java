package com.grud;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DevelopersCrud extends Crud <Developer> {
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
    public List<Developer> findAll() {
        List<Developer> list = new ArrayList();
        Connection connection = ConnectionDb.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(READ_ALL_DATA_SQL_QUERY);
        while (result.next()) {
            list.add(new Developer().setName(result.getString("name"))
                    .setAge(result.getInt("age"))
                    .setSex(result.getBoolean("sex"))
                    .setSalary(result.getInt("salary")));
        }
        close(statement);
        close(connection);
        return list;
    }

    @SneakyThrows
    @Override
    public boolean deleteDataById(Integer id) {
        Connection connection = ConnectionDb.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL_QUERY);
        preparedStatement.setInt(1, id);
        boolean isDeleted = preparedStatement.execute();
        close(preparedStatement);
        close(connection);
        return isDeleted;
    }

    @SneakyThrows
    public boolean updateData(String name, int salary) {
        Connection connection = ConnectionDb.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_SALARY_DEVELOPER_SQL_QUERY);
        preparedStatement.setInt(1, salary);
        preparedStatement.setString(2, name);
        boolean isUpdated = preparedStatement.execute();
        close(preparedStatement);
        close(connection);
        return isUpdated;
    }
}
