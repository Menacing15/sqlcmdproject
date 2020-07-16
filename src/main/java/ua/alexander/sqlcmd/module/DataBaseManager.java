package ua.alexander.sqlcmd.module;

import java.util.List;
import java.util.Set;

public interface DataBaseManager {
    void connect(String database, String user, String password);

    Set<String> getTableNames();

    List<Data> getTableData(String tableName);

    Set<String> getTableColumnNames(String tableName);

    void clearTable(String tableName);

    void insertData(String tableName,Data input);

    void updateTableData(String tableName, Data updatedValue, int id);

    boolean isConnected();
}
