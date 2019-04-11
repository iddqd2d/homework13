package com.grud;


import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

public abstract class Crud <T> {
    public abstract List<T> findAll();
    public abstract boolean deleteDataById(Integer id);

    @SneakyThrows
    public void close(Statement statement) {
        if (Objects.nonNull(statement)) {
            statement.close();
        }
    }

    @SneakyThrows
    public void close(Connection connection) {
        if (Objects.nonNull(connection)) {
            connection.close();
        }
    }
}
