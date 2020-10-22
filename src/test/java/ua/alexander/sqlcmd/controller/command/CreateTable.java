package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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
    DataBaseManager dbManager;
    View view;
    Command command;
    private CommandTool tool;

    public CreateTable() {
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Create(view, dbManager);
    }

    @Before
    public void setup() {
        PowerMockito.mockStatic(CommandTool.class);
        tool = Mockito.mock(CommandTool.class);
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

    @Test
    public void testCreateWrongParam() {
        String[] commandExample1 = {"create", "id", "numeric", "name"};

        //when
        Mockito.doThrow(new IllegalArgumentException("Something is missing... Quantity of parameters is 1 ,but you need 2")).
                when(tool).validateCommandWithCustomSize(commandExample1, "create:test,id,numeric,name");
        try{

            command.execute("create:test,id,numeric,name");
        }catch (IllegalArgumentException e){
            assertEquals("Some parameters are missing. " +
                    "The command should look like that: \n" +
                    "'create:tableName,column1,value1,column2,value2...columnN,valueN', " +
                    "but your is: 'create:test,id,numeric,name'", e.getMessage());
        }
        verify(dbManager, never()).createTable("test", "id numeric, name text");
    }
}
