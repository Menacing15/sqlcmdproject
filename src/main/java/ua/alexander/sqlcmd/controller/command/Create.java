package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTools;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Create implements Command {
    private DataBaseManager dbManager;
    private View view;
    private CommandTools tool;

    public Create(View view, DataBaseManager dbManager) {
        tool = new CommandTools();
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("create:");
    }

    @Override
    public void execute(String command) {
        String[] data = tool.refactorCommandWithMultipleParam(command);
        tool.validateCommandWithCustomSize(data, command);
        String columnsNameAndType = "";
        String tableName = data[1];
        for (int i = 2; i < data.length; i++) {
            if (i % 2 == 1)
                columnsNameAndType += data[i] + ", ";
            else
                columnsNameAndType += data[i] + " ";
        }
        columnsNameAndType = columnsNameAndType.substring(0, columnsNameAndType.length() - 2);
        dbManager.createTable(tableName, columnsNameAndType);
        view.type(String.format("Table '%s' was created successfully!", tableName));
    }
}
