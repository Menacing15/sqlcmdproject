package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.DataImpl;
import ua.alexander.sqlcmd.view.View;

public class Insert implements Command {
    private final DataBaseManager dbManager;
    private final View view;
    private CommandTool tool;

    public Insert(View view, DataBaseManager dbManager) {
        tool = CommandTool.getCommandTool();
        this.dbManager = dbManager;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("insert:");
    }

    @Override
    public void execute(String command) {
        String[] input = tool.refactorCommand(command);
        tool.validateCommandWithCustomSize(input, command);
        String tableName = input[1];

        Data data = new DataImpl();
        for (int index = 2; index < input.length - 1; index = index + 2) {
            String columnName = input[index];
            String value = input[index + 1];
            data.put(columnName, value);
        }
        dbManager.insertData(tableName, data);
        view.type(String.format("Record %s was successfully added to the table '%s'.", data, tableName));
    }
}
