package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTools;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Drop implements Command {
    private static final String COMMAND_SAMPLE = "drop:user";
    private DataBaseManager dbManager;
    private View view;
    private CommandTools tool;

    public Drop(View view, DataBaseManager dbManager) {
        tool = new CommandTools();
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("drop:");
    }

    @Override
    public void execute(String command) {
        view.type("Are you sure you want to delete the table? Type 'y' to confirm, 'n' to discard");
        if(new CommandTools().verification())
            executeDropping(command);
        else
            view.type("Table wasn't deleted.");
    }

    public void executeDropping(String command) {
        String[] data = command.split("[:]");
        tool.validateCommandWithFixedSize(data,COMMAND_SAMPLE);
        String tableName = data[1];
        dbManager.dropTable(tableName);
        view.type(String.format("Table '%s' was deleted successfully!", tableName));
    }
}
