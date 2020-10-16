package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTools;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.List;
import java.util.Set;

public class Find implements Command {
    private static final String COMMAND_SAMPLE = "find:user";
    private DataBaseManager dbManager;
    private View view;
    private CommandTools tool;

    public Find(View view, DataBaseManager dbManager) {
        tool = new CommandTools();
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

        Set<String> tableColumns = dbManager.getTableColumnNames(tableName);

        if (tableColumns.size() != 0) {
            view.drawHeader(tableColumns);
            List<Data> tableData = dbManager.getTableData(tableName);
            view.drawTable(tableData);
        }
    }
}
