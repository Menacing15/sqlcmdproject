package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.DataImpl;
import ua.alexander.sqlcmd.view.View;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommandTool.class)

public class DeleteTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;
    private CommandTool tool;
    public DeleteTest(){
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Delete(view,dbManager);
    }

    @Before
    public void setup() {
        PowerMockito.mockStatic(CommandTool.class);
        tool = Mockito.mock(CommandTool.class);
        when(CommandTool.getCommandTool()).thenReturn(tool);
    }

    @Test
    public void testProcessAbleDeleteWithParameters() {
        boolean processAble = command.processAble("delete:user,column,value");
        assertTrue(processAble);
    }

    @Test
    public void testProcessAbleDeleteWithoutParameters() {
        boolean processAble = command.processAble("delete");
        assertFalse(processAble);
    }

    @Test
    public void testExecuteDeleteWrongParameters() {
        //given
        String[] commandExample2 = {"drop", "user"};
        String[] commandExample3 = {"drop", "user", "column"};

        //when
        Mockito.doThrow(new IllegalArgumentException("Something is missing... Quantity of parameters is 2 ,but you need 4")).
                when(tool).validateCommandWithFixedSize(commandExample2, "delete:user,column,value");
        //then
        try {
            command.execute("drop:user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 2 ,but you need 4", e.getMessage());
        }

        //when
        Mockito.doThrow(new IllegalArgumentException("Something is missing... Quantity of parameters is 3 ,but you need 4")).
                when(tool).validateCommandWithFixedSize(commandExample3, "delete:user,column,value");
        //then
        try {
            command.execute("drop:user,column");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 4", e.getMessage());
        }
    }

    @Test
    public void testDeleteThenConfirm() {
        //given
        Data data1 = new DataImpl();
        data1.put("id",8);
        data1.put("username","sasha");
        data1.put("password","love");

        Data data2 = new DataImpl();
        data2.put("id",20);
        data2.put("username","dana");
        data2.put("password","hope");

        //when
        when(dbManager.getTableColumnNames("user")).
                thenReturn(new LinkedHashSet<>(Arrays.asList("id", "username", "password")));

        List<Data> data = new LinkedList<>(Arrays.asList(data1,data2));
        when(dbManager.getTableData("user")).thenReturn(data);

        //when
        Mockito.when(tool.verification()).thenReturn(true);

        command.execute("delete:user,username,sasha");
        //then
        Mockito.verify(dbManager).deleteRecord("user","username","sasha");
        Mockito.verify(view).drawTable(dbManager.getTableColumnNames("user"),
                dbManager.getTableData("user"));
    }

    @Test
    public void testDeleteThenDiscard() {
        //given
        Data data1 = new DataImpl();
        data1.put("id",8);
        data1.put("username","sasha");
        data1.put("password","love");

        Data data2 = new DataImpl();
        data2.put("id",20);
        data2.put("username","dana");
        data2.put("password","hope");

        //when
        when(dbManager.getTableColumnNames("user")).
                thenReturn(new LinkedHashSet<>(Arrays.asList("id", "username", "password")));

        List<Data> data = new LinkedList<>(Arrays.asList(data1,data2));
        when(dbManager.getTableData("user")).thenReturn(data);

        //when
        Mockito.when(tool.verification()).thenReturn(false);

        command.execute("delete:user,username,sasha");
        //then
        Mockito.verify(dbManager,never()).deleteRecord("user","username","sasha");
        Mockito.verify(view, never()).drawTable(dbManager.getTableColumnNames("user"),
                dbManager.getTableData("user"));
        Mockito.verify(view).type("Record wasn't deleted.");
    }

}
