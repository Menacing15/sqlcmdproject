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
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommandTool.class)

public class ClearTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;
    private CommandTool tool;

    public ClearTest(){
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Clear(view,dbManager);
    }

    @Before
    public void setup() {
        PowerMockito.mockStatic(CommandTool.class);
        tool = Mockito.mock(CommandTool.class);
        when(CommandTool.getCommandTool()).thenReturn(tool);
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
        //given
        String[] commandExample2 = {"clear", "user", "user"};
        String[] commandExample3 = {"clear", "user", "user"};

        //when
        Mockito.doThrow(new IllegalArgumentException("Something is missing... Quantity of parameters is 3 ,but you need 2")).
                when(tool).validateCommandWithFixedSize(commandExample2, "clear:user");

        //then
        try {
            command.execute("clear:user,user");
        } catch (IllegalArgumentException e) {
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }

        //when
        Mockito.doThrow(new IllegalArgumentException("Something is missing... Quantity of parameters is 3 ,but you need 2")).
                when(tool).validateCommandWithFixedSize(commandExample3, "clear:user");

        //then
        try {
            command.execute("clear:user:user");
        } catch (IllegalArgumentException e) {
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }
    }

    @Test
    public void testClearThenConfirm() {
        Mockito.when(tool.verification()).thenReturn(true);
        command.execute("clear:user");
        Mockito.verify(dbManager).clearTable("user");
        Mockito.verify(view).type("Table 'user' was cleared successfully!");
    }

    @Test
    public void testClearThenDiscard() {
        Mockito.when(tool.verification()).thenReturn(false);
        command.execute("clear:user");
        Mockito.verify(dbManager, never()).clearTable("user");
        Mockito.verify(view).type("Table wasn't cleared.");
    }
}
