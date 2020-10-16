package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTools;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Clear implements Command {
    private static final String COMMAND_SAMPLE = "clear:user";
    private DataBaseManager dbManager;
    private View view;
    private CommandTools tool;

    public Clear(View view, DataBaseManager dbManager) {
        tool = new CommandTools();
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("clear:");
    }

    @Override
    public void execute(String command) {
        view.type("Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard");
        if (new CommandTools().verification())
            executeClearing(command);
        else
            view.type("Table wasn't cleared.");
    }


    public void executeClearing(String command) {
        String[] data = command.split("[:]");
        tool.validateCommandWithFixedSize(data, COMMAND_SAMPLE);
        String tableName = data[1];
        dbManager.clearTable(tableName);
        view.type(String.format("Table '%s' was cleared successfully!", tableName));
    }
}
