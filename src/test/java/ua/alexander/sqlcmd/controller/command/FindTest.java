package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.DataImpl;
import ua.alexander.sqlcmd.view.View;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

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

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
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
    public void testPrintTable(){       //TODO как вывести таблицу????
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

        command.execute("find:user");
        verify(view, atLeastOnce()).
                drawTable(dbManager.getTableColumnNames("user"),dbManager.getTableData("user"));

        /*
        |-----------------------|
        |id|username|password|
        |-----------------------|
        |8|sasha|love|
        |20|dana|hope|
        |-----------------------|
         */
    }

    @Test
    public void testPrintEmptyTableData() {
        when(dbManager.getTableColumnNames("user")).
                thenReturn(new LinkedHashSet<>(Arrays.asList("id", "username", "password")));
        when(dbManager.getTableData("user")).
                thenReturn(new ArrayList<>(0));

        command.execute("find:user");

        verify(view, atLeastOnce()).
                drawTable(dbManager.getTableColumnNames("user"),dbManager.getTableData("user"));

        /*
        |-----------------------|
        |id|username|password|
        |-----------------------|
        |-----------------------|
         */
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
}
