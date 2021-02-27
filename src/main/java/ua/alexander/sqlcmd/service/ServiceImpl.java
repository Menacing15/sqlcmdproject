package ua.alexander.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class ServiceImpl implements Service {

    @Autowired
    private DataBaseManagerFactory dataBaseManagerFactory;

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help","connect","tables", "find");
    }

    @Override
    public DataBaseManager connect(String dbName, String user, String password) {
        DataBaseManager dbManager = dataBaseManagerFactory.createManager();
        dbManager.connect(dbName, user, password);
        return dbManager;
    }

    @Override
    public List<List<String>> find(DataBaseManager dataBaseManager, String tableName) {
        List<List<String>> result = new ArrayList<>();

        List<String> columns = new ArrayList<>(dataBaseManager.getTableColumnNames(tableName));
        List<Data> table = dataBaseManager.getTableData(tableName);

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
    public Set<String> tables(DataBaseManager dataBaseManager) {
        return dataBaseManager.getTableNames();
    }
}
