package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommandTool.class)

public class DropTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;
    private CommandTool tool;

    public DropTest(){
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Drop(view,dbManager);
    }

    @Before
    public void setup() {
        PowerMockito.mockStatic(CommandTool.class);
        tool = Mockito.mock(CommandTool.class);
        when(CommandTool.getCommandTool()).thenReturn(tool);
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
        //given
        String[] commandExample2 = {"drop", "user", "user"};
        String[] commandExample3 = {"drop", "user", "user"};

        //when
        Mockito.doThrow(new IllegalArgumentException("Something is missing... Quantity of parameters is 1 ,but you need 2")).
                when(tool).validateCommandWithFixedSize(commandExample2, "drop:user");
        //then
        try {
            command.execute("drop:user,user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }

        //when
        Mockito.doThrow(new IllegalArgumentException("Something is missing... Quantity of parameters is 3 ,but you need 2")).
                when(tool).validateCommandWithFixedSize(commandExample3, "drop:user");
        //then
        try {
            command.execute("drop:user:user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }
    }

    @Test
    public void testDropThenConfirm() {
        Mockito.when(tool.verification()).thenReturn(true);
        command.execute("drop:user");
        Mockito.verify(dbManager).dropTable("user");
        Mockito.verify(view).type("Table 'user' was deleted successfully!");
    }

    @Test
    public void testDropThenDiscard() {
        Mockito.when(tool.verification()).thenReturn(false);
        command.execute("drop:user");
        Mockito.verify(dbManager, never()).dropTable("user");
        Mockito.verify(view).type("Table wasn't deleted.");
    }
}
