package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTools;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.List;
import java.util.Set;

public class Delete implements Command {
    private DataBaseManager dbManager;
    private View view;
    private CommandTools tool;

    public Delete(View view, DataBaseManager dbManger) {
        tool = new CommandTools();
        this.dbManager = dbManger;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("delete:");
    }

    @Override
    public void execute(String command) {
        String[] data = tool.refactorCommandWithMultipleParam(command);
        tool.validateCommandWithCustomSize(data, command);
        String tableName = data[1];

        String columnName = data[2];
        String value = data[3];

        dbManager.deleteCell(tableName, columnName, value);

        view.drawTable(dbManager.getTableColumnNames(tableName),dbManager.getTableData(tableName));
    }
}

