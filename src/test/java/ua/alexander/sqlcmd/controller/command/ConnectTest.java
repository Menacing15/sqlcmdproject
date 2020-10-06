package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class ConnectTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Connect(view, dbManager);
    }

    @Test
    public void testProcessAbleConnectWithParameters() {
        boolean processAble = command.processAble("connect:sqlcmd,postgres,1234");
        assertTrue(processAble);
    }

    @Test
    public void testProcessAbleConnectWithoutParameters() {
        boolean processAble = command.processAble("connect");
        assertFalse(processAble);
    }

    @Test
    public void testConnect(){
        command.execute("connect:sqlcmd,postgres,1234");
        verify(dbManager).connect("sqlcmd","postgres","1234");
        verify(view).type("\u001B[34m" + "Success!" + "\u001B[0m");
        verify(view).type("Please enter your command! Type 'help' to see available commands.");
    }

    @Test
    public void testConnectWrongParameters() {
        try {
            command.execute("connect:sqlcmd");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 1 ,but you need 3", e.getMessage());
        }
    }

    @Test(expected=RuntimeException.class)
    public void testConnectWrongPassword() {
        doThrow(RuntimeException.class).
                when(dbManager).connect("sqlcmd","postgres","wrongPassword");
        dbManager.connect("sqlcmd","postgres","wrongPassword");

    }
}
