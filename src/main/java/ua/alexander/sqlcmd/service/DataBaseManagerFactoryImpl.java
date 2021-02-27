package ua.alexander.sqlcmd.service;

import ua.alexander.sqlcmd.module.DataBaseManager;


public class DataBaseManagerFactoryImpl implements DataBaseManagerFactory {

    private DataBaseManager dataBaseManager;

    @Override
    public DataBaseManager createManager() {
        return dataBaseManager;
    }

    public void setDataBaseManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }
}
