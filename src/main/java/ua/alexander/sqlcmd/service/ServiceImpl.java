package ua.alexander.sqlcmd.service;

import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ServiceImpl implements Service {

    private DataBaseManager manager;

    private List<String> commands;

    @Override
    public List<String> commandsList() {
        return commands;
    }

    @Override
    public DataBaseManager connect(String dbName, String user, String password) {
        manager.connect(dbName, user, password);
        return manager;
    }

    @Override
    public List<List<String>> find(String tableName) {
        List<List<String>> result = new ArrayList<>();

        List<String> columns = new ArrayList<>(manager.getTableColumnNames(tableName));
        List<Data> table = manager.getTableData(tableName);

        result.add(columns);

        for(Data data: table){
            List<String> row = new ArrayList<>(columns.size());
            result.add(row);
            for(String column : columns){
                row.add(data.get(column).toString());
            }
        }
        return result;
    }

    @Override
    public Set<String> tables() {
        return manager.getTableNames();
    }

    @Override
    public void drop(String tableName) {
        manager.dropTable(tableName);
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void setManager(DataBaseManager manager) {
        this.manager = manager;
    }
}
