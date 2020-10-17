package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.DataImpl;
import ua.alexander.sqlcmd.view.View;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class UpdateTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Update(view, dbManager);
    }

    @Test
    public void testProcessAble() {
        boolean processAble = command.processAble("update:");
        assertTrue(processAble);
    }
    @Test
    public void testProcessAbleDropWithoutParameters() {
        boolean processAble = command.processAble("update");
        assertFalse(processAble);
    }

    @Test
    public void testUpdate(){
        when(dbManager.getTableColumnNames("user")).
                thenReturn(new LinkedHashSet<>(Arrays.asList("id", "username", "password")));

        Data data1 = new DataImpl();
        data1.put("id",8);
        data1.put("username","sasha");
        data1.put("password","love");

        Data data2 = new DataImpl();
        data2.put("id",20);
        data2.put("username","dana");
        data2.put("password","hope");

        List<Data> data = new LinkedList<>(Arrays.asList(data1,data2));

        when(dbManager.getTableData("user")).thenReturn(data);

        command.execute("update:user,id,1,username,sasha");

        verify(dbManager).updateTable("user","id = '1'"," username = 'sasha'");
        verify(view, atLeastOnce()).
                drawTable(dbManager.getTableColumnNames("user"),dbManager.getTableData("user"));
    }

}
