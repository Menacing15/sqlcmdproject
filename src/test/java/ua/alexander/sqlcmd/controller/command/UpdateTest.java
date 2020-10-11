package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

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



}
