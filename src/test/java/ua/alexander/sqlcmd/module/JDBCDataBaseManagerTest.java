package ua.alexander.sqlcmd.module;

public class JDBCDataBaseManagerTest extends DataBaseManagerTest{
    @Override
    public DataBaseManager getDataBaseManager() {
        return new JDBCDataBaseManager();
    }
}
