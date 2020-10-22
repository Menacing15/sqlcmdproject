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
        jdbcDBManager.clearTable("user");
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
    public void testGetTableColumnNames(){
        Set<String> columnNames = jdbcDBManager.getTableColumnNames("user");
        assertEquals("[id, username, password]", columnNames.toString());
    }

    @Test
    public void testGetTableData() {
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("username", "dana");
        data.put("password", "qwerty");
        jdbcDBManager.insertData("user", data);

        List<Data> users = jdbcDBManager.getTableData("user");
        Data user = users.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, dana, qwerty]", user.getValues().toString());
    }

    @Test
    public void testClearTable(){
        Data data = new DataImpl();
        data.put("id", 8);
        jdbcDBManager.insertData("user",data);
        jdbcDBManager.clearTable("user");
        List<Data> dataAfterClearing = jdbcDBManager.getTableData("user");
        assertEquals(0, dataAfterClearing.size());
    }

    @Test
    public void testDropTable(){
        String data = "id numeric, name text, age numeric";
        jdbcDBManager.createTable("test",data);
        jdbcDBManager.dropTable("test");
        Set<String> tableNames = jdbcDBManager.getTableNames();
        assertEquals("[user]", tableNames.toString());
    }

    @Test
    public void testCreateTable(){
        String data = "id numeric, name text, age numeric";
        jdbcDBManager.createTable("test",data);
        Set<String> tableNames = jdbcDBManager.getTableNames();
        assertEquals("[user, test]", tableNames.toString());
        jdbcDBManager.dropTable("test");
    }

    @Test
    public void testInsertData(){
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("username", "dana");
        data.put("password", "qwerty");
        jdbcDBManager.insertData("user",data);

        List<Data> tableData = jdbcDBManager.getTableData("user");
        Data user = tableData.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, dana, qwerty]",user.getValues().toString());
    }

    @Test
    public void testDeleteRecord(){
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("username", "dana");
        data.put("password", "qwerty");

        jdbcDBManager.insertData("user",data);
        jdbcDBManager.deleteRecord("user","password","qwerty");

        List<Data> tableData = jdbcDBManager.getTableData("user");

        Data user = tableData.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, dana, ]",user.getValues().toString());
    }

    @Test
    public void testUpdateTable() {
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("username", "dana");
        data.put("password", "qwerty");
        jdbcDBManager.insertData("user", data);

        jdbcDBManager.updateTable("user","username = 'dana'","password = '1234'" );

        List<Data> tableData = jdbcDBManager.getTableData("user");
        Data user = tableData.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, dana, 1234]", user.getValues().toString());
    }
}
