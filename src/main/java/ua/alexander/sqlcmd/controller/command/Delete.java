package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Delete implements Command {
    private static final String COMMAND_SAMPLE = "delete:user,id,4";
    private DataBaseManager dbManager;
    private View view;
    private CommandTool tool;

    public Delete(View view, DataBaseManager dbManger) {
        tool = CommandTool.getCommandTool();
        this.dbManager = dbManger;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("delete:");
    }

    @Override
    public void execute(String command) {
        String[] data = tool.refactorCommand(command);
        tool.validateCommandWithFixedSize(data, COMMAND_SAMPLE);
        if (CommandTool.getCommandTool().verification()) {
            String tableName = data[1];
            String columnName = data[2];
            String value = data[3];

            dbManager.deleteRecord(tableName, columnName, value);

            view.drawTable(dbManager.getTableColumnNames(tableName),dbManager.getTableData(tableName));
        }
        else
            view.type("Record wasn't deleted.");
    }
}

