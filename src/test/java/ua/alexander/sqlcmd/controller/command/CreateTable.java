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

public class CreateTable {
    private DataBaseManager dbManager;
    private View view;
    private Command command;

    public CreateTable() {
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Create(view, dbManager);
    }

    @Before
    public void setup() {
        PowerMockito.mockStatic(CommandTool.class);
        CommandTool tool = Mockito.mock(CommandTool.class);
        when(CommandTool.getCommandTool()).thenReturn(tool);
    }

    @Test
    public void testProcessAbleCreateWithParameters() {
        boolean processAble = command.processAble("create:user");
        assertTrue(processAble);
    }

    @Test
    public void testProcessAbleCreateWithoutParameters() {
        boolean processAble = command.processAble("create");
        assertFalse(processAble);
    }

    @Test
    public void testCreate() {
        command.execute("create:test,id,numeric,name,text");
        verify(dbManager).createTable("test", "id numeric, name text");
        verify(view).type("Table 'test' was created successfully!");
    }
}
