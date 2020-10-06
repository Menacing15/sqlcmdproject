package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class HelpTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        dbManager = mock(DataBaseManager.class);
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
        Mockito.verify(view).type("\tinsert:tableName,column1,value1,column2,value2,...,columnN,valueN - to make a new record in the table");
        Mockito.verify(view).type("\tfind:tableName - to draw the table");
        Mockito.verify(view).type("\tclear:tableName - to clear table's content");
        Mockito.verify(view).type("\texit - to shut down the program.");
    }
}
