package com.grud;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomSelection {
    private Connection connection;
    private static final String SUM_SALARY_DEVELOPERS_IN_PROJECT_SQL_QUERY = "SELECT SUM(salary) FROM developers \n"
            + "JOIN developers_projects ON developers.id = developers_projects.developers_id  \n"
            + "JOIN projects ON projects.id = developers_projects.project_id\n"
            + "WHERE project_name  = ?;";
    private static final String DEVELOPERS_IN_PROJECT_SQL_QUERY = "SELECT name FROM developers \n"
            + "JOIN developers_projects ON developers.id = developers_projects.developers_id  \n"
            + "JOIN projects ON projects.id = developers_projects.project_id\n"
            + "WHERE project_name  = ?;";
    private static final String DEVELOPERS_BY_LANGUAGE_SQL_QUERY = "SELECT name FROM developers \n"
            + "JOIN developers_skills ON developers.id = developers_skills.developer_id  \n"
            + "JOIN skills ON skills.id = developers_skills.skill_id\n"
            + "WHERE skill_name = ?;";
    private static final String DEVELOPERS_BY_LEVEL_SKILL_SQL_QUERY = "SELECT name FROM developers \n"
            + "JOIN developers_skills ON developers.id = developers_skills.developer_id  \n"
            + "JOIN skills ON skills.id = developers_skills.skill_id\n"
            + "WHERE level = ?;";
    private static final String NUMBER_OF_DEVELOPERS_IN_PROJECT_SQL_QUERY = "SELECT start_date, project_name, COUNT(project_id) FROM projects \n"
            + "JOIN developers_projects ON projects.id = developers_projects.project_id\n"
            + "GROUP BY developers_projects.project_id;";

    public CustomSelection() {
        connection = ConnectionDb.getConnection();
    }

    @SneakyThrows
    public int getSumSalaryDevelopersInProject(String project) {
        int sumSalaryDevelopersInProject;
        PreparedStatement preparedStatement = connection.prepareStatement(SUM_SALARY_DEVELOPERS_IN_PROJECT_SQL_QUERY);
        preparedStatement.setString(1, project);
        ResultSet result = preparedStatement.executeQuery();
        sumSalaryDevelopersInProject = (result.next()) ? result.getInt(1) : 0;
        result.close();
        preparedStatement.close();
        return sumSalaryDevelopersInProject;
    }

    private List<String> getStrings(String value, List<String> developers,
                                    PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, value);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            developers.add(result.getString(1));
        }
        result.close();
        preparedStatement.close();
        return developers;
    }

    @SneakyThrows
    public List<String> getDevelopersInProject(String project) {
        List<String> developersInProject = new ArrayList();
        PreparedStatement preparedStatement = connection.prepareStatement(DEVELOPERS_IN_PROJECT_SQL_QUERY);
        return getStrings(project, developersInProject, preparedStatement);
    }

    @SneakyThrows
    public List<String> getDevelopersByLanguage(String language) {
        List<String> developersByLanguage = new ArrayList();
        PreparedStatement preparedStatement = connection.prepareStatement(DEVELOPERS_BY_LANGUAGE_SQL_QUERY);
        return getStrings(language, developersByLanguage, preparedStatement);
    }

    @SneakyThrows
    public List<String> getDevelopersByLevel(String level) {
        List<String> developersByLevel = new ArrayList();
        PreparedStatement preparedStatement = connection.prepareStatement(DEVELOPERS_BY_LEVEL_SKILL_SQL_QUERY);
        return getStrings(level, developersByLevel, preparedStatement);
    }

    @SneakyThrows
    public List<String> getProjects() {
        List<String> projects = new ArrayList();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(NUMBER_OF_DEVELOPERS_IN_PROJECT_SQL_QUERY);
        while (result.next()) {
            projects.add("start project:" + result.getString(1)
                    + "\nname project: " + result.getString(2)
                    + "\nnumber of developers" + result.getInt(3));
        }
        result.close();
        statement.close();
        return projects;
    }

    @SneakyThrows
    public void closeConnection() {
        connection.close();
    }
}
