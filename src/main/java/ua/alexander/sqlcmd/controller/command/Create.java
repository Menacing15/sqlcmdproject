package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Create implements Command {
    private DataBaseManager dbManager;
    private View view;
    private CommandTool tool;

    public Create(View view, DataBaseManager dbManager) {
        tool = CommandTool.getCommandTool();
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("create:");
    }

    @Override
    public void execute(String command) {
        String[] data = tool.refactorCommand(command);
        tool.validateCommandWithCustomSize(data, command);
        String columnsNameAndType = "";
        String tableName = data[1];
        columnsNameAndType = refactorToSqlFormat(data, columnsNameAndType);
        dbManager.createTable(tableName, columnsNameAndType);
        view.type(String.format("Table '%s' was created successfully!", tableName));
    }

    private String refactorToSqlFormat(String[] data, String columnsNameAndType) {
        for (int i = 2; i < data.length; i++) {
            if (i % 2 == 1)
                columnsNameAndType += data[i] + ", ";
            else
                columnsNameAndType += data[i] + " ";
        }
        columnsNameAndType = columnsNameAndType.substring(0, columnsNameAndType.length() - 2);
        return columnsNameAndType;
    }
}
