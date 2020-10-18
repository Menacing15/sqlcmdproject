package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Find implements Command {
    private static final String COMMAND_SAMPLE = "find:user";
    private DataBaseManager dbManager;
    private View view;
    private CommandTool tool;

    public Find(View view, DataBaseManager dbManager) {
        tool = CommandTool.getCommandTool();
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("find:");
    }

    @Override
    public void execute(String command) {
        String[] data = command.split("[:]");
        tool.validateCommandWithFixedSize(data, COMMAND_SAMPLE);
        String tableName = data[1];

        view.drawTable(dbManager.getTableColumnNames(tableName),dbManager.getTableData(tableName));
    }
}
