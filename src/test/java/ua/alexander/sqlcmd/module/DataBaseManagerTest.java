package ua.alexander.sqlcmd.module;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DataBaseManagerTest {

    private DataBaseManager jdbcDBManager;
    public abstract DataBaseManager getDataBaseManager();

    @Before
    public void setup() {
        jdbcDBManager = getDataBaseManager();
        jdbcDBManager.connect("sqlcmd", "postgres", "1234");
    }

    @Test
    public void testIsConnected(){
        assertTrue(jdbcDBManager.isConnected());
    }

    @Test
    public void testGetTableNames() {
        Set<String> tableNames = jdbcDBManager.getTableNames();
        System.out.println(tableNames);
        assertEquals("[user]", tableNames.toString());

    }

    @Test
    public void testGetTableData() {
        jdbcDBManager.clearTable("user");

        Data input = new DataImpl();
        input.put("id", 8);
        input.put("username", "Andreev");
        input.put("password", "qwerty");
        jdbcDBManager.insertData("user", input);

        List<Data> users = jdbcDBManager.getTableData("user");
        Data user = users.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, Andreev, qwerty]", user.getValues().toString());

        System.out.println("#Table Data:#\n\n" + jdbcDBManager.getTableData("user"));
    }

    @Test
    public void testGetTableHeader(){
        Set<String> columnNames = jdbcDBManager.getTableColumnNames("user");
        System.out.println(columnNames);
        assertEquals("[id, username, password]", columnNames.toString());
    }

    @Test
    public void testUpdateTableData() {
        jdbcDBManager.clearTable("user");

        Data input = new DataImpl();
        input.put("id", 8);
        input.put("username", "Andreev");
        input.put("password", "qwerty");
        jdbcDBManager.insertData("user",input);

        Data update = new DataImpl();
        update.put("password", "qwerty1234");
        jdbcDBManager.updateTableData("user", update ,8);

        List<Data> users = jdbcDBManager.getTableData("user");
        Data user = users.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, Andreev, qwerty1234]",user.getValues().toString());

        System.out.println("#Updated table#\n\n" + jdbcDBManager.getTableData("user"));
    }

}
