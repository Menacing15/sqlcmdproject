package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.DataImpl;
import ua.alexander.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FindTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Find(view, dbManager);
    }

    @Test
    public void testProcessAbleFindWithParameters() {
        boolean processAble = command.processAble("find:user");
        assertTrue(processAble);
    }

    @Test
    public void testProcessAbleFindWithoutParameters() {
        boolean processAble = command.processAble("find");
        assertFalse(processAble);
    }

    @Test
    public void testPrintTable(){
        setupTableColumns("user","id", "username", "password");

        Data data1 = new DataImpl();
        data1.put("id",8);
        data1.put("username","Sasha");
        data1.put("password","ilovedana");

        Data data2 = new DataImpl();
        data2.put("id",20);
        data2.put("username","Dana");
        data2.put("password","hopefully");

        List<Data> data = new ArrayList<>(Arrays.asList(data1,data2));
        when(dbManager.getTableData("user")).thenReturn(data);

        command.execute("find:user");

        // then
        shouldPrint("[|-----------------------|, " +
                "|id|username|password|, " +
                "|-----------------------|, " +
                "|8|Sasha|ilovedana|, " +
                "|20|Dana|hopefully|, " +
                "|-----------------------|]");
    }

    @Test
    public void testPrintEmptyTableData() {
        setupTableColumns("user","id", "username", "password");

        when(dbManager.getTableData("user")).thenReturn(new ArrayList<>(0));
        command.execute("find:user");

        // then
        shouldPrint("[|-----------------------|, " +
                "|id|username|password|, " +
                "|-----------------------|, " +
                "|-----------------------|]");
    }

    @Test
    public void testFindTableWrongParameters() {
        try {
            command.execute("find:");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 1 ,but you need 2", e.getMessage());
        }
        try {
            command.execute("find:find:user");
        }catch(IllegalArgumentException e){
            assertEquals("Something is missing... Quantity of parameters is 3 ,but you need 2", e.getMessage());
        }

    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).type(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    private void setupTableColumns(String tableName, String... columns) {
        when(dbManager.getTableColumnNames(tableName))
                .thenReturn(new LinkedHashSet<>(Arrays.asList(columns)));
    }
}
