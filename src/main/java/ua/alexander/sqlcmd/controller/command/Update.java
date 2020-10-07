package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.List;
import java.util.Set;

public class Update implements Command {
    private DataBaseManager dbManager;
    private View view;

    public Update( View view, DataBaseManager dbManger){
        this.dbManager = dbManger;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("update:");
    }

    @Override
    public void execute(String command) {
        String[] input = refactorCommandWithMultipleParam(command);
        if (input.length % 2 != 1) {
            throw new IllegalArgumentException(String.format("Some parameters are missing. " +
                    "The command should look like that: \n" +
                    "'update:tableName,column1,value1,column2,value2...columnN,valueN', " +
                    "but your is: '%s'", command));
        }
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
