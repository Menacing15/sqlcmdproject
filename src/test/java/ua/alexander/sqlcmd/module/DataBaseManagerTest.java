package ua.alexander.sqlcmd.module;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-applicationContext.xml"})

public class DataBaseManagerTest {

    @Autowired
    private DataBaseManager manager;

    private String database;
    private String user;
    private String password;

    public  void setManager(DataBaseManager manager) {
        this.manager = manager;
    }

    @Before
    public void setup() {
        try (FileReader fileReader = new FileReader("src\\test\\resources\\database.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            database = properties.getProperty("databaseName");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            manager.connect(database, user, password);
            manager.createTable("test", "id numeric, name text, password text");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void clean() {
        manager.connect(database, user, password);
        manager.dropTable("test");
    }

    @Test
    public void testIsConnected() {
        assertTrue(manager.isConnected());
    }

    @Test(expected = RuntimeException.class)
    public void testConnectBadParam() {
        manager.connect("sqlcmd", "postgres", "wrongpassword");
    }

    @Test
    public void testGetTableNames() {
        //when
        Set<String> tableNames = manager.getTableNames();

        //then
        System.out.println(tableNames);
        assertEquals("[test]", tableNames.toString());
    }

    @Test
    public void testGetTableColumnNames() {
        Set<String> columnNames = manager.getTableColumnNames("test");
        assertEquals("[id, name, password]", columnNames.toString());
    }

    @Test
    public void testGetTableData() {
        //given
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("name", "dana");
        data.put("password", "qwerty");

        //when
        manager.insertData("test", data);
        List<Data> users = manager.getTableData("test");
        Data user = users.get(0);

        //then
        System.out.println(user.toString());
        assertEquals("[id, name, password]", user.getNames().toString());
        assertEquals("[8, dana, qwerty]", user.getValues().toString());
    }

    @Test
    public void testClearTable() {
        //given
        Data data = new DataImpl();
        data.put("id", 8);

        //when
        manager.insertData("test", data);
        manager.clearTable("test");

        //then
        List<Data> dataAfterClearing = manager.getTableData("test");
        assertEquals(0, dataAfterClearing.size());
    }

    @Test
    public void testDropTable() {
        //given
        String data = "id numeric, name text, age numeric";

        //when
        manager.createTable("test2", data);
        manager.dropTable("test2");

        //then
        Set<String> tableNames = manager.getTableNames();
        System.out.println(tableNames);
        assertEquals("[test]", tableNames.toString());
    }

    @Test
    public void testCreateTable() {
        //given
        String data = "id numeric, name text, age numeric";

        //when
        manager.createTable("test2", data);

        //then
        Set<String> tableNames = manager.getTableNames();
        System.out.println(tableNames);
        assertEquals("[test, test2]", tableNames.toString());
        manager.dropTable("test2");
    }

    @Test
    public void testInsertData() {
        //given
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("name", "dana");
        data.put("password", "qwerty");

        //when
        manager.insertData("test", data);

        //then
        List<Data> tableData = manager.getTableData("test");
        Data user = tableData.get(0);
        System.out.println(user);
        assertEquals("[id, name, password]", user.getNames().toString());
        assertEquals("[8, dana, qwerty]", user.getValues().toString());
    }

    @Test
    public void testDeleteRecord() {
        //given
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("name", "dana");
        data.put("password", "qwerty");

        //when
        manager.insertData("test", data);
        manager.deleteRecord("test", "password", "qwerty");


        //then
        List<Data> tableData = manager.getTableData("test");
        Data user = tableData.get(0);
        System.out.println(user);
        assertEquals("[id, name, password]", user.getNames().toString());
        assertEquals("[8, dana, ]", user.getValues().toString());
    }

    @Test
    public void testUpdateTable() {
        //given
        Data data = new DataImpl();
        data.put("id", 8);
        data.put("name", "dana");
        data.put("password", "qwerty");

        //when
        manager.insertData("test", data);
        manager.updateTable("test", "name = 'dana'", "password = '1234'");

        //then
        List<Data> tableData = manager.getTableData("test");
        Data user = tableData.get(0);
        System.out.println( user);
        assertEquals("[id, name, password]", user.getNames().toString());
        assertEquals("[8, dana, 1234]", user.getValues().toString());
    }
}
