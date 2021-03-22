package ua.alexander.sqlcmd.module;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DataBaseManagerTest {

    private static DataBaseManager manager;

    public abstract DataBaseManager getDataBaseManager();

    @BeforeClass
    public static void setup() {
        try (FileReader fileReader = new FileReader("src\\main\\resources\\database.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            String database = properties.getProperty("databaseName");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            manager.connect(database, user, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }

    @Test
    public void testGetTableNames() {
        Set<String> tableNames = manager.getTableNames();
        System.out.println(tableNames);
        assertEquals("[]", tableNames.toString());
    }

    @Test
    public void testGetTableColumnNames() {
        Set<String> columnNames = manager.getTableColumnNames("user");
        assertEquals("[id, username, password]", columnNames.toString());
    }

    @Test
    public void testGetTableData() {
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("username", "dana");
        data.put("password", "qwerty");
        manager.insertData("user", data);

        List<Data> users = manager.getTableData("user");
        Data user = users.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, dana, qwerty]", user.getValues().toString());
    }

    @Test
    public void testClearTable() {
        Data data = new DataImpl();
        data.put("id", 8);
        manager.insertData("user", data);
        manager.clearTable("user");
        List<Data> dataAfterClearing = manager.getTableData("user");
        assertEquals(0, dataAfterClearing.size());
    }

    @Test
    public void testDropTable() {
        String data = "id numeric, name text, age numeric";
        manager.createTable("test", data);
        manager.dropTable("test");
        Set<String> tableNames = manager.getTableNames();
        assertEquals("[]", tableNames.toString());
    }

    @Test
    public void testCreateTable() {
        String data = "id numeric, name text, age numeric";
        manager.createTable("test", data);
        Set<String> tableNames = manager.getTableNames();
        assertEquals("[user, test]", tableNames.toString());
        manager.dropTable("test");
    }

    @Test
    public void testInsertData() {
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("username", "dana");
        data.put("password", "qwerty");
        manager.insertData("user", data);

        List<Data> tableData = manager.getTableData("user");
        Data user = tableData.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, dana, qwerty]", user.getValues().toString());
    }

    @Test
    public void testDeleteRecord() {
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("username", "dana");
        data.put("password", "qwerty");

        manager.insertData("user", data);
        manager.deleteRecord("user", "password", "qwerty");

        List<Data> tableData = manager.getTableData("user");

        Data user = tableData.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, dana, ]", user.getValues().toString());
    }

    @Test
    public void testUpdateTable() {
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("username", "dana");
        data.put("password", "qwerty");
        manager.insertData("user", data);

        manager.updateTable("user", "username = 'dana'", "password = '1234'");

        List<Data> tableData = manager.getTableData("user");
        Data user = tableData.get(0);

        assertEquals("[id, username, password]", user.getNames().toString());
        assertEquals("[8, dana, 1234]", user.getValues().toString());
    }
}
