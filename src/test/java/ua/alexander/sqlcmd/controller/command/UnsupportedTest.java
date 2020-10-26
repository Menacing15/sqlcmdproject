package ua.alexander.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class UnsupportedTest {
    private View view;
    private Command command;

    @Before
    public void setup() {
        view = mock(View.class);
        command = new Unsupported(view);
    }

    @Test
    public void testProcessAbleUnsupported() {
        boolean processAble = command.processAble("unsupported");
        assertTrue(processAble);
    }

    @Test
    public void testUnsupported() {
        command.execute("unsupported");
        Mockito.verify(view).type("Sorry, such command doesn't exist! Try again!");
    }
}
