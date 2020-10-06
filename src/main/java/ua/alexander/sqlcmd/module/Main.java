package ua.alexander.sqlcmd.module;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        JDBCDataBaseManager jdbcDataBaseManager = new JDBCDataBaseManager();
       jdbcDataBaseManager.connect("sqlcmd","postgres","1234");
       jdbcDataBaseManager.dropTable("test");
       jdbcDataBaseManager.createTable("test","id numeric, name text");
    }
}























