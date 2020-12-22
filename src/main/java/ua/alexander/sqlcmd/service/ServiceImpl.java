package ua.alexander.sqlcmd.service;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.JDBCDataBaseManager;

import java.util.Arrays;
import java.util.List;

public class ServiceImpl implements Service {

    private DataBaseManager dbManager;

    public ServiceImpl(){
        dbManager = new JDBCDataBaseManager();
    }

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help","menu","connect");
    }

    @Override
    public void connect(String dbName, String user, String password) {
        dbManager.connect(dbName, user, password);
    }
}
