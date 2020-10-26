package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.Collections;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class TablesTest {
    private View view;
    private Command command;
    private DataBaseManager dbManger;

    @Before
    public void setup() {
        view = mock(View.class);
        dbManger = mock(DataBaseManager.class);
        command = new Tables(view, dbManger);
    }

    @Test
    public void testProcessAbleTables() {
        boolean processAble = command.processAble("tables");
        assertTrue(processAble);
    }

    @Test
    public void testTables() {
        Mockito.when(dbManger.getTableNames()).thenReturn(new LinkedHashSet<>(Collections.singletonList("user")));
        command.execute("tables");
        Mockito.verify(dbManger).getTableNames();
        Mockito.verify(view).type("[user]");
    }
}
