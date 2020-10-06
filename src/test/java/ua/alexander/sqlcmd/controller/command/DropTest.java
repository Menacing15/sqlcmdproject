package ua.alexander.sqlcmd.controller.command;

import org.junit.Test;
import org.mockito.Mockito;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DropTest {
    private DataBaseManager dbManager;
    private View view;
    private Drop command;

    public DropTest(){
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Drop(view,dbManager);
    }

    @Test
    public void testProcessAbleDropWithParameters() {
        boolean processAble = command.processAble("drop:user");
        assertTrue(processAble);
    }

    @Test
    public void testProcessAbleDropWithoutParameters() {
        boolean processAble = command.processAble("drop");
        assertFalse(processAble);
    }

    @Test
    public void testExecuteDroppingWrongParameters() {
        try {
            command.executeDropping("dropuser");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 1 ,but you need 2", e.getMessage());
        }
        try {
            command.executeDropping("drop:user,user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }
        try {
            command.executeDropping("drop:user:user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }
    }

    @Test
    public void testExecuteDropping(){
        command.executeDropping("drop:user");
        Mockito.verify(dbManager).dropTable("user");
        Mockito.verify(view).type("Table 'user' was deleted successfully!");
    }
}
