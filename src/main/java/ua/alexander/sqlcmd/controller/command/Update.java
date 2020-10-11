package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTools;
import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.List;
import java.util.Set;

public class Update implements Command {
    private DataBaseManager dbManager;
    private View view;
    private CommandTools tool;

    public Update( View view, DataBaseManager dbManger){
        tool = new CommandTools();
        this.dbManager = dbManger;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("update:");
    }

    @Override
    public void execute(String command) {
        String[] input = tool.refactorCommandWithMultipleParam(command);
        tool.validateCommandWithCustomSize(input,command);
        String tableName = input[0];

        String checkParam = input[1] + " = '" + input[2] + "'";
        String newValue = getSqlSetFormat(input);

        dbManager.updateTable(tableName, checkParam,newValue);

        Set<String> tableColumns = dbManager.getTableColumnNames(tableName);
        if (tableColumns.size() != 0) {
            view.drawHeader(tableColumns);
            List<Data> tableData = dbManager.getTableData(tableName);
            view.drawTable(tableData);
        }

    }

    private String getSqlSetFormat(String[] input) {
        String newValue = " ";
        for(int i = 3;i < input.length; i+=2){
            newValue += input[i] + " = '" + input[i+1] + "',";
        }
        newValue = newValue.substring(0,newValue.length()-1);
        return newValue;
    }
}
