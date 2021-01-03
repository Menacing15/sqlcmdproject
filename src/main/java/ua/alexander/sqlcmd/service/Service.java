package ua.alexander.sqlcmd.service;

import ua.alexander.sqlcmd.module.DataBaseManager;

import java.util.List;
import java.util.Set;

public interface Service {
    List<String> commandsList();

    DataBaseManager connect(String dbName, String user, String password);

    List<List<String>> find(DataBaseManager dataBaseManager, String tableName);

    Set<String> tables(DataBaseManager dataBaseManager);




}
