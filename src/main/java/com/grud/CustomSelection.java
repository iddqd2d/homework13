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

    public CustomSelection() {
        connection = ConnectionDb.getConnection();
    }

    @SneakyThrows
    public String getSumSalaryDevelopersInProject(String project) {
        String sumSalaryDevelopersInProject;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT SUM(salary) FROM developers \n" +
                "JOIN developers_projects ON developers.id = developers_projects.developers_id  \n" +
                "JOIN projects ON projects.id = developers_projects.project_id\n" +
                "WHERE project_name  = ?;");
        preparedStatement.setString(1, project);
        ResultSet result = preparedStatement.executeQuery();
        sumSalaryDevelopersInProject = (result.next()) ? result.getString(1) : null;
        result.close();
        preparedStatement.close();
        return sumSalaryDevelopersInProject;
    }

    private List<String> getStrings(String value, List<String> developers, PreparedStatement preparedStatement) throws SQLException {
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
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM developers \n" +
                "JOIN developers_projects ON developers.id = developers_projects.developers_id  \n" +
                "JOIN projects ON projects.id = developers_projects.project_id\n" +
                "WHERE project_name  = ?;");
        return getStrings(project, developersInProject, preparedStatement);
    }

    @SneakyThrows
    public List<String> getDevelopersByLanguage(String language) {
        List<String> developersByLanguage = new ArrayList();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM developers \n" +
                "JOIN developers_skills ON developers.id = developers_skills.developer_id  \n" +
                "JOIN skills ON skills.id = developers_skills.skill_id\n" +
                "WHERE skill_name = ?;");
        return getStrings(language, developersByLanguage, preparedStatement);
    }

    @SneakyThrows
    public List<String> getDevelopersByLevel(String level) {
        List<String> developersByLevel = new ArrayList();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM developers \n" +
                "JOIN developers_skills ON developers.id = developers_skills.developer_id  \n" +
                "JOIN skills ON skills.id = developers_skills.skill_id\n" +
                "WHERE level = ?;");
        return getStrings(level, developersByLevel, preparedStatement);
    }

    @SneakyThrows
    public List<String> getProjects() {
        List<String> projects = new ArrayList();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT start_date, project_name, COUNT(project_id) FROM projects \n" +
                "JOIN developers_projects ON projects.id = developers_projects.project_id\n" +
                "GROUP BY developers_projects.project_id;");
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
    public void closeConnection(){
        connection.close();
    }

    public static void main(String[] args) {
        System.out.println(new CustomSelection().getSumSalaryDevelopersInProject("zzz"));
        System.out.println(new CustomSelection().getDevelopersInProject("zzz"));
        System.out.println(new CustomSelection().getDevelopersByLanguage("Java"));
        System.out.println(new CustomSelection().getDevelopersByLevel("Middle"));
        System.out.println(new CustomSelection().getProjects());
    }
}
