package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Update implements Command {
    private DataBaseManager dbManager;
    private View view;
    private CommandTool tool;

    public Update(View view, DataBaseManager dbManger) {
        tool = CommandTool.getCommandTool();
        this.dbManager = dbManger;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("update:");
    }

    @Override
    public void execute(String command) {
        String[] input = tool.refactorCommand(command);
        tool.validateCommandWithCustomSize(input, command);
        String tableName = input[1];

        String checkParam = input[2] + " = '" + input[3] + "'";
        String newValue = getSqlSetFormat(input);

        dbManager.updateTable(tableName, checkParam, newValue);

        view.drawTable(dbManager.getTableColumnNames(tableName),dbManager.getTableData(tableName));

    }

    private String getSqlSetFormat(String[] input) {
        String newValue = " ";
        for (int i = 4; i < input.length - 1; i += 2) {
            newValue += input[i] + " = '" + input[i + 1] + "',";
        }
        newValue = newValue.substring(0, newValue.length() - 1);
        return newValue;
    }
}
