package ua.alexander.sqlcmd.module;

import java.sql.*;
import java.util.*;

public class JDBCDataBaseManager implements DataBaseManager {
    private Connection connection;

    public void connect(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add you PosgreSQL Driver to the project!", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, user, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException("Can't get connection to database " + database + "with the user: " + user, e);
        }
    }

    public Set<String> getTableNames() {
        String query = "SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE' AND table_schema = 'public'";
        Set<String> tables = new LinkedHashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tables.add(resultSet.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return tables;
        }
    }

    @Override
    public Set<String> getTableColumnNames(String tableName) {
        Set<String> names = new LinkedHashSet<>();
        String query = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString("column_name"));
            }
            return names;
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException(message[0]);
        }
    }

    public List<Data> getTableData(String tableName) {
        List<Data> output = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData resultSetMD = resultSet.getMetaData();
            while (resultSet.next()) {
                Data data = new DataImpl();
                output.add(data);
                for (int i = 1; i <= resultSetMD.getColumnCount(); i++) {
                    data.put(resultSetMD.getColumnName(i), resultSet.getObject(i));
                }
            }
            return output;
        } catch (SQLException e) {
            String message = e.getMessage();
            System.out.println(message);
            return output;
        }
    }

    public void clearTable(String tableName) {
        String query = "DELETE FROM" + tableName;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException("Can't clear table " + tableName + message[0]);
        }
    }

    public void dropTable(String tableName) {
        String query = "DROP TABLE " + tableName;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException("Can't drop table " + tableName + message[0]);
        }
    }

    @Override
    public void createTable(String tableName, String data) {
        String query = "CREATE TABLE " + tableName + "(?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, data);
            statement.executeUpdate();
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException("Can't create table " + tableName + message[0]);
        }
    }

    public void insertData(String tableName, Data input) {
        String query = "INSERT INTO " + tableName + "(?)VALUES (?)";
        String tableNames = formatNames(input);
        String values = formatValues(input);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tableNames);
            statement.setString(2, values);
            statement.executeUpdate();
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException("Can't insert in table " + tableName +
                    "Column names: " + tableNames + ", values: " + values + message[0]);
        }
    }

    @Override
    public void deleteRecord(String tableName, String columnName, String value) {
        String query = "UPDATE " + tableName + " SET ? = '' WHERE ? = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, columnName);
            statement.setString(2, columnName);
            statement.setString(3, value);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateTable(String tableName, String checkValue, String newValue) {
        String query = "UPDATE " + tableName + " SET ? WHERE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, checkValue);
            statement.setString(2, newValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String formatNames(Data input) {
        StringBuilder names = new StringBuilder();
        for (String name : input.getNames()) {
            names.append(name).append(",");
        }
        names = new StringBuilder(names.substring(0, names.length() - 1));
        return names.toString();
    }

    private String formatValues(Data input) {
        StringBuilder values = new StringBuilder();
        for (Object value : input.getValues()) {
            values.append("'").append(value).append("',");
        }
        values = new StringBuilder(values.substring(0, values.length() - 1));
        return values.toString();
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

}

