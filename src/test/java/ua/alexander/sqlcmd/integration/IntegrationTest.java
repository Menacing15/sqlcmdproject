package ua.alexander.sqlcmd.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import ua.alexander.sqlcmd.controller.Runner.Main;

import java.io.PrintStream;

public class IntegrationTest {
    private static ConfigurableInputStream in;
    private static LogOutputStream out;

    @Before
    public void setup() {
        in = new ConfigurableInputStream();
        out = new LogOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @After
    public void clearAll(){
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("y");
        in.add("exit");

        Main.main(new String[0]);
    }



    @Test
    public void testExit() {
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testHelp() {
        in.add("help");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //help
                "There are such commands:\r\n" +
                "\thelp - to see all commands available.\r\n" +
                "\tconnect:database,username,password  - to connect to a certain database\r\n" +
                "\ttables -  to get all table names of the database you are connected to.\r\n" +
                "\tcreate:tableName,column1,column2,...,columnN -  to create a table with a given name and columns\r\n" +
                "\tinsert:tableName,column1,value1,column2,value2,...,columnN,valueN - \n" +
                "\t\tto make a new record in the table\r\n" +
                "\tupdate:tableName,column1,value1,column2,value2,...,columnN,valueN - \n" +
                "\t\tto update a record in a table , where there is a record column1 = value1\r\n" +
                "\tfind:tableName - to draw the table\r\n" +
                "\tdrop:tableName - to drop the table\r\n" +
                "\tclear:tableName - to clear table's content\r\n" +
                "\tdelete:tableName,column,value - to delete a record in a table\r\n" +
                "\texit - to shut down the program.\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testConnectAfterConnect() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("unsupported");
        in.add("connect:sqlcmd,postgres,1234");
        in.add("unsupported");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //unsupported
                "Sorry, such command doesn't exist! Try again!\r\n" +
                //connect
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //unsupported
                "Sorry, such command doesn't exist! Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testConnectError() {
        in.add("connect:error");
        in.add("connect:");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //error 1
                "[31mFailed, the reason is: Something is missing... Quantity of parameters is 2 ,but you need 4[0m\n" +
                "Try again!\r\n" +
                //error 2
                "[31mFailed, the reason is: Something is missing... Quantity of parameters is 1 ,but you need 4[0m\n" +
                "Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testCommandWithOutConnection() {
        in.add("tables");
        in.add("find");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //tables
                "Sorry, but you can't use command 'tables' before you connect the database. " +
                "To connect database type 'connect:database,username,password'\r\n" +
                //find
                "Sorry, but you can't use command 'find' before you connect the database." +
                " To connect database type 'connect:database,username,password'\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testFind() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("insert:user,id,20,username,d,password,1234");
        in.add("insert:user,id,8,username,s,password,4321");
        in.add("find:user");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //insert
                "Record names:[id, username, password]\n" +
                "values:[20, d, 1234] was successfully added to the table 'user'.\r\n" +
                "Record names:[id, username, password]\n" +
                "values:[8, s, 4321] was successfully added to the table 'user'.\r\n" +
                //find
                "|-----------------------|\r\n" +
                "|id|username|password|\r\n" +
                "|-----------------------|\r\n" +
                "|20|d|1234|\r\n" +
                "|8|s|4321|\r\n" +
                "|-----------------------|\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testFindError() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("insert:user,id,20,username,d,password,1234");
        in.add("find:");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //insert
                "Record names:[id, username, password]\n" +
                "values:[20, d, 1234] was successfully added to the table 'user'.\r\n" +
                //find
                "\u001B[31mFailed, the reason is: Something is missing... Quantity of parameters is 1 ,but you need 2\u001B[0m\n" +
                "Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testInsert() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("insert:user,id,20,username,d,password,z");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //insert
                "Record names:[id, username, password]\n" +
                "values:[20, d, z] was successfully added to the table 'user'.\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testInsertError() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("insert:user,id,20,username,d,password");
        in.add("insert:");

        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //insert
                "\u001B[31mFailed, the reason is: Some parameters are missing. The command should look like that: \n" +
                "'insert:tableName,column1,value1,column2,value2...columnN,valueN', but your is: 'insert:user,id,20,username,d,password'\u001B[0m\n" +
                "Try again!\r\n" +
                "\u001B[31mFailed, the reason is: Some parameters are missing. The command should look like that: \n" +
                "'insert:tableName,column1,value1,column2,value2...columnN,valueN', but your is: 'insert:'\u001B[0m\n" +
                "Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }



    @Test
    public void testTables() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("tables");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //tables
                "[user]\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }


    @Test
    public void testClear() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("y");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //error
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //verification
                "Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard\r\n" +
                "Table 'user' was cleared successfully!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testClearError() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:");
        in.add("y");
        in.add("clear:gf");
        in.add("y");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //verification
                "Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard\r\n" +
                //error 1
                "[31mFailed, the reason is: Something is missing... Quantity of parameters is 1 ,but you need 2[0m\n" +
                "Try again!\r\n" +
                //verification
                "Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard\r\n" +
                //error 2
                "[31mFailed, the reason is: Can't clear table 'gf' ERROR: relation \"public.gf\" does not exist[0m\n"+
                "Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testClearDiscardDeletion() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("n");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //error
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //verification
                "Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard\r\n" +
                "Table wasn't cleared.\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testClearWrongVerificationCommandFormat() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("a");
        in.add("b");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //error
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //verification
                "Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testClearExitFromClearVerification() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("clear:user");
        in.add("exit");


        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //error
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //verification
                "Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testUnsupported() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("unsupported");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. " +
                "Format: connect:database,username,password\r\n" +
                //connect
                "\u001B[34mSuccess!\u001B[0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                //unsupported
                "Sorry, such command doesn't exist! Try again!\r\n" +
                //exit
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testCreateAfterCreate() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("create:test,id,numeric,name,text");
        in.add("create:test,id,numeric,name,text");
        in.add("drop:test");
        in.add("y");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                "Table 'test' was created successfully!\r\n" +
                "\u001B[31mFailed, the reason is: Can't create table 'test' ERROR: relation \"test\" already exists\u001B[0m\n" +
                "Try again!\r\n"+
                "Are you sure you want to delete the table? Type 'y' to confirm, 'n' to discard\r\n" +
                "Table 'test' was deleted successfully!\r\n"+
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testCreateAndDropTable() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("create:test,id,numeric,name,text");
        in.add("drop:test");
        in.add("y");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                "Table 'test' was created successfully!\r\n" +
                "Are you sure you want to delete the table? Type 'y' to confirm, 'n' to discard\r\n" +
                "Table 'test' was deleted successfully!\r\n"+
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testUpdate() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("insert:user,id,20,username,d,password,cola");
        in.add("update:user,id,20,username,dana");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                "Record names:[id, username, password]\n" +
                "values:[20, d, cola] was successfully added to the table 'user'.\r\n" +
                "|-----------------------|\r\n" +
                "|id|username|password|\r\n" +
                "|-----------------------|\r\n" +
                "|20|dana|cola|\r\n" +
                "|-----------------------|\r\n"+
                "See ya!\r\n", out.getData());
    }

    @Test
    public void testUpdateWrongCheckValue() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("insert:user,id,20,username,dana,password,cola");
        in.add("update:user,id,19,username,sasha");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                "Record names:[id, username, password]\n" +
                "values:[20, dana, cola] was successfully added to the table 'user'.\r\n" +
                "|-----------------------|\r\n" +
                "|id|username|password|\r\n" +
                "|-----------------------|\r\n" +
                "|20|dana|cola|\r\n" +
                "|-----------------------|\r\n"+
                "See ya!\r\n", out.getData());
    }


    @Test
    public void testUpdateWrongParameters() {
        in.add("connect:sqlcmd,postgres,1234");
        in.add("insert:user,id,20,username,dana,password,cola");
        in.add("update:user,id,19,username");
        in.add("exit");

        Main.main(new String[0]);

        assertEquals("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password\r\n" +
                "[34mSuccess![0m\r\n" +
                "Please enter your command! Type 'help' to see available commands.\r\n" +
                "Record names:[id, username, password]\n" +
                "values:[20, dana, cola] was successfully added to the table 'user'.\r\n" +
                "\u001B[31mFailed, the reason is: Some parameters are missing. The command should look like that: \n" +
                "'update:tableName,column1,value1,column2,value2...columnN,valueN', but your is: 'update:user,id,19,username'\u001B[0m\n" +
                "Try again!\r\n" +
                "See ya!\r\n", out.getData());
    }

}
