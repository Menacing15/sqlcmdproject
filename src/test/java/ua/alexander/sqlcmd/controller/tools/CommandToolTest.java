package ua.alexander.sqlcmd.controller.tools;

import org.junit.Before;
import org.junit.Test;
import ua.alexander.sqlcmd.controller.command.ExitException;
import ua.alexander.sqlcmd.integration.ConfigurableInputStream;
import ua.alexander.sqlcmd.integration.LogOutputStream;

import java.io.PrintStream;
import java.util.Arrays;

import static junit.framework.TestCase.*;


public class CommandToolTest {

    private static ConfigurableInputStream in;
    private static LogOutputStream out;
    private CommandTool commandTool = new CommandTool();

    @Before
    public void setup() {
        in = new ConfigurableInputStream();
        out = new LogOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void verificationTestTrue() {
        in.add("y");
        boolean result = commandTool.verification();
        assertTrue(result);
    }

    @Test
    public void verificationTestFalse() {
        in.add("n");
        boolean result = commandTool.verification();
        assertFalse(result);
    }

    @Test
    public void verificationTest() {
        in.add("exit");
        try {
            commandTool.verification();
        } catch (ExitException e) {
            System.out.println("Success");
        }
    }

    @Test
    public void refactorCommandTest() {
        String[] findRefactorTest = commandTool.refactorCommand("find:test");
        assertEquals("[find, test]", Arrays.toString(findRefactorTest));

        String[] connectRefactorTest = commandTool.refactorCommand("connect:sqlcmd,postgres,1234");
        assertEquals("[connect, sqlcmd, postgres, 1234]", Arrays.toString(connectRefactorTest));
    }

    @Test
    public void validateCommandWithFixedSizeTest() {
        String[] connect = {"connect", "sqlcmd", "postgres", "1234"};
        try {
            commandTool.validateCommandWithFixedSize(connect, "connect:sqlcmd,postgres,1234");
            System.out.println("It's fine");
        } catch (IllegalArgumentException e) {
            System.out.println("It's not fine");
        }

        String[] connectBadParameters = {"connect", "sqlcmd", "postgres", "1234"};
        try {
            commandTool.validateCommandWithFixedSize(connectBadParameters, "connect:sqlcmd,postgres");
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("It's not fine");
        }
    }

    @Test
    public void validateCommandWithCustomSizeTest() {
        String[] insert = {"insert", "user", "name", "dana", "age", "18"};
        try {
            commandTool.validateCommandWithCustomSize(insert, "insert:user,name,dana,age,18");
            System.out.println("It's fine");
        } catch (IllegalArgumentException e) {
            System.out.println("It's not fine");
        }

        String[] insertBadParameters = {"insert", "user", "name", "dana", "age"};
        try {
            commandTool.validateCommandWithCustomSize(insertBadParameters, "insert:user,name,dana,age");
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("It's not fine");
        }
    }

    @Test
    public void getParameterLength() {
        int findLength = commandTool.getParameterLength("find:test");
        assertEquals(2, findLength);

        int connectLength = commandTool.getParameterLength("connect:sqlcmd,postgres,1234");
        assertEquals(4, connectLength);
    }
}
