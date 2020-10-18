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
            throw new RuntimeException(String.format("Can't get connection to database '%s', with the user: '%s'", database, user), e);
        }
    }

    public Set<String> getTableNames() {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format("SELECT table_name FROM information_schema.tables " +
                     "WHERE table_type = 'BASE TABLE' AND table_schema = 'public'"))) {
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
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM INFORMATION_SCHEMA." +
                     "COLUMNS WHERE TABLE_NAME = '%s'", tableName))) {
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
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM public.%s", tableName))) {
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
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DELETE FROM public.%s", tableName));
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException(String.format("Can't clear table '%s' ", tableName) + message[0]);
        }
    }

    public void dropTable(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DROP TABLE public.%s", tableName));
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException(String.format("Can't drop table '%s' ", tableName) + message[0]);
        }
    }


    public void insertData(String tableName, Data input) {
        String tableNames = formatNames(input, "%s,");
        String values = formatValues(input, "'%s',");
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("INSERT INTO public." + tableName + "(%s)VALUES (%s)", tableNames, values));
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException(String.format("Can't insert in table '%s'. " +
                    "Column names: '%s', values %s ", tableName, tableNames, values) + message[0]);
        }
    }

    public void updateTableData(String tableName, Data updatedValue, int id) {
        String updatedNames = formatNames(updatedValue, "%s = ?,");
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format("UPDATE public.%s " +
                "SET %s WHERE id = ?", tableName, updatedNames))) {
            int index = 1;
            for (Object value : updatedValue.getValues()) {
                preparedStatement.setString(index, value.toString());
                index++;
            }
            preparedStatement.setInt(index, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String formatNames(Data input, String format) {
        String names = "";
        for (String name : input.getNames()) {
            names += String.format(format, name);
        }
        names = names.substring(0, names.length() - 1);
        return names;
    }

    public String formatValues(Data input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public void createTable(String tableName, String data) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("CREATE TABLE public.%s(%s);", tableName, data));
        } catch (SQLException e) {
            String[] message = e.getMessage().split("[\n]");
            throw new RuntimeException(String.format("Can't create table '%s' ", tableName) + message[0]);
        }
    }

    @Override
    public void updateTable(String tableName, String checkValue, String newValue) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format("UPDATE public.%s SET %sWHERE %s",
                tableName, newValue, checkValue))) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteRecord(String tableName, String columnName, String value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format("UPDATE public.%s SET %s = '' WHERE %s = '%s'",
                tableName, columnName, columnName, value))) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}

