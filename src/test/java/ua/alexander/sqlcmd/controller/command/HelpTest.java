package ua.alexander.sqlcmd.controller.command;

import org.junit.Test;
import org.mockito.Mockito;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class HelpTest {
    private View view;
    private Command command;

    public HelpTest(){
        view = mock(View.class);
        command = new Help(view);
    }

    @Test
    public void testProcessAbleHelp() {
        boolean processAble = command.processAble("help");
        assertTrue(processAble);
    }

    @Test
    public void testHelp(){
        command.execute("help");
        Mockito.verify(view).type("There are such commands:");
        Mockito.verify(view).type("\thelp - to see all commands available.");
        Mockito.verify(view).type("\tconnect:database,username,password  - to connect to a certain database");
        Mockito.verify(view).type("\ttables -  to get all table names of the database you are connected to.");
        Mockito.verify(view).type("\tcreate:tableName,column1,column2,...,columnN -  to create a table with a given name and columns");
        Mockito.verify(view).type("\tinsert:tableName,column1,value1,column2,value2,...,columnN,valueN - \n" +
                "\t\tto make a new record in the table");
        Mockito.verify(view).type("\tupdate:tableName,column1,value1,column2,value2,...,columnN,valueN - \n" +
                "\t\tto update a record in a table , where there is a record column1 = value1");
        Mockito.verify(view).type("\tfind:tableName - to draw the table");
        Mockito.verify(view).type("\tdrop:tableName - to drop the table");
        Mockito.verify(view).type("\tclear:tableName - to clear table's content");
        Mockito.verify(view).type("\tdelete:tableName,column,value - to delete a record in a table");
        Mockito.verify(view).type("\texit - to shut down the program.");
    }
}
