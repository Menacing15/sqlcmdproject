package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ClearTest {
    private DataBaseManager dbManager;
    private View view;
    private Clear command;

    @Before
    public void setup() {
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Clear(view, dbManager);
    }

    @Test
    public void testProcessAbleClearWithParameters() {
        boolean processAble = command.processAble("clear:user");
        assertTrue(processAble);
    }

    @Test
    public void testProcessAbleClearWithoutParameters() {
        boolean processAble = command.processAble("clear");
        assertFalse(processAble);
    }


    @Test
    public void testExecuteClearingWrongParameters() {
        try {
            command.executeClearing("clearuser");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 1 ,but you need 2", e.getMessage());
        }
        try {
            command.executeClearing("clear:user,user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }
        try {
            command.executeClearing("clear:user:user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }
    }

    @Test
    public void testExecuteClearing() {
        command.executeClearing("clear:user");
        Mockito.verify(dbManager).clearTable("user");
        Mockito.verify(view).type("Table 'user' was cleared successfully!");
    }
}
