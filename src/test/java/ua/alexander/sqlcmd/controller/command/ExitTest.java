package ua.alexander.sqlcmd.controller.command;

import org.junit.Test;
import org.mockito.Mockito;
import ua.alexander.sqlcmd.view.View;

import static org.junit.Assert.*;


public class ExitTest {
    private View view = Mockito.mock(View.class);
    @Test
    public void testProcessAbleExit(){
        Command command = new Exit();
        boolean processAble = command.processAble("exit");
        assertTrue(processAble);
    }


    @Test
    public void testExecuteExitCommandThrowsExitException() {
        Command command = new Exit();
        try {
            command.execute("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {

        }
    }

}
