package ua.alexander.sqlcmd.controller.command;

import org.junit.Test;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTable {
    DataBaseManager dbManager;
    View view;
    Command command;

    public CreateTable(){
        dbManager = mock(DataBaseManager.class);
        view = mock(View.class);
        command = new Create(view, dbManager);
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
    public void testCreate(){
        command.execute("create:test,id,numeric,name,text");
        verify(dbManager).createTable("test","id numeric, name text, ");
        verify(view).type("Table 'test' was created successfully!");
    }

}
