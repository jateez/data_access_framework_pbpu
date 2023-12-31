package com.pbpu_framework;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteFileHandler implements DataFileHandler<List<Data>> {

    private final String jdbcUrl = "jdbc:sqlite:data/sqlite_data.sqlite";

    @Override
    public List<Data> readFile(String tableName) throws IOException {
        List<Data> dataList = new ArrayList<>();

        // Check if the database file exists; if not, create an empty database
        // File databaseFile = new File("data/sqlite_data.sqlite");
        // if (!databaseFile.exists()) {
        // createEmptyDatabase();
        // }

        String query = "SELECT * FROM " + tableName;

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String subject = resultSet.getString("subject");
                String description = resultSet.getString("description");
                dataList.add(new Data(subject, description));
            }

        } catch (SQLException e) {
            throw new IOException("Failed to read from SQLite database", e);
        }

        return dataList;
    }

    @Override
    public void writeFile(String tableName, List<Data> dataList) throws IOException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (subject TEXT, description TEXT)";
        String deleteDataQuery = "DELETE FROM " + tableName;
        String insertDataQuery = "INSERT INTO " + tableName + " (subject, description) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
                Statement statement = connection.createStatement();
                PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
                PreparedStatement deleteDataStatement = connection.prepareStatement(deleteDataQuery);
                PreparedStatement insertDataStatement = connection.prepareStatement(insertDataQuery)) {

            // Create the table if it doesn't exist
            createTableStatement.executeUpdate();

            // Clear existing data
            deleteDataStatement.executeUpdate();

            // Insert new data
            for (Data data : dataList) {
                String subject = data.getSubject();
                String description = data.getDescription();

                // Use prepared statement to prevent SQL injection
                insertDataStatement.setString(1, subject);
                insertDataStatement.setString(2, description);
                insertDataStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new IOException("Failed to write to SQLite database", e);
        }
    }

    // Doesn't work (╯°□°）╯︵ ┻━┻)
    // private void createEmptyDatabase() throws IOException {
    // try (Connection connection = DriverManager.getConnection(jdbcUrl);
    // Statement statement = connection.createStatement()) {
    // // Create the database file
    // statement.executeUpdate("CREATE DATABASE IF NOT EXISTS sqlite_data");

    // // Use the created database
    // statement.executeUpdate("USE sqlite_data");

    // // Create the dataTable table
    // String createTableQuery = "CREATE TABLE IF NOT EXISTS dataTable (id INTEGER
    // PRIMARY KEY AUTOINCREMENT, subject TEXT, description TEXT)";
    // statement.executeUpdate(createTableQuery);
    // } catch (SQLException e) {
    // throw new IOException("Failed to create empty SQLite database", e);
    // }
    // }
}
