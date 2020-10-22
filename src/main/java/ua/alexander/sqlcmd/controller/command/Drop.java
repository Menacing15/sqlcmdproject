package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Drop implements Command {
    private static final String COMMAND_SAMPLE = "drop:user";
    private DataBaseManager dbManager;
    private View view;

    public Drop(View view, DataBaseManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("drop:");
    }

    @Override
    public void execute(String command) {
        String[] data = command.split("[:]");
        CommandTool.getCommandTool().validateCommandWithFixedSize(data, COMMAND_SAMPLE);
        view.type("Are you sure you want to delete the table? Type 'y' to confirm, 'n' to discard");
        if (CommandTool.getCommandTool().verification()) {
            String tableName = data[1];
            dbManager.dropTable(tableName);
            view.type(String.format("Table '%s' was deleted successfully!", tableName));
        } else
            view.type("Table wasn't deleted.");
    }
}
