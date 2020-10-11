package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTools;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.module.DataImpl;
import ua.alexander.sqlcmd.view.View;

public class Insert implements Command {
    private final DataBaseManager dbManager;
    private final View view;
    private CommandTools tool;

    public Insert(View view, DataBaseManager dbManager) {
        tool = new CommandTools();
        this.dbManager = dbManager;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("insert:");
    }

    @Override
    public void execute(String command) {
        String[] input = tool.refactorCommandWithMultipleParam(command);
        tool.validateCommandWithCustomSize(input,command);
        String tableName = input[1];

        Data data = new DataImpl();
        for (int index = 2; index < input.length; index = index + 2) {
            String columnName = input[index];
            String value = input[index + 1];

            data.put(columnName, value);
        }
        dbManager.insertData(tableName, data);
        view.type(String.format("Record %s was successfully added to the table '%s'.", data, tableName));
    }
}
