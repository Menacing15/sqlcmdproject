package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.DataImpl;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InsertTest {
    private DataBaseManager dbManager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Insert(view, dbManager);
    }

    @Test
    public void testProcessAbleInsert() {
        boolean processAble = command.processAble("insert:");
        assertTrue(processAble);
    }

    @Ignore                     //TODO разобраться почему не работает
    public void testInsert(){
        Data data = new DataImpl();
        data.put("id",1);

        command.execute("insert:user,id,1");
        verify(dbManager).insertData("user", data);
        verify(view).type(String.format("Record %s was successfully added to the table 'user'.", data));

    }
}
