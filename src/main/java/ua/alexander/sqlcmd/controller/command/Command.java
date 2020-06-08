package ua.alexander.sqlcmd.controller.command;

public interface Command {
    boolean processAble(String command);
    void execute(String command);

    default String[] getCommandRefactored(String command) {
        String[] refactored = command.split("[,]");
        String[] buffer = refactored[0].split("[:]");
        if (buffer.length == 2) {
            refactored[0] = buffer[1];
        } else {
            return new String[0];
        }
        return refactored;
    }
}
