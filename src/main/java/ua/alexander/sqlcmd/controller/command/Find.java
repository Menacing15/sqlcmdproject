package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.Data;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.Set;

public class Find implements Command {
    private static final String COMMAND_SAMPLE = "find:user";
    private DataBaseManager dbManager;
    private View view;

    public Find(View view, DataBaseManager dbManager) {
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

            if (data.length != getParameterLength()) {
                throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length +
                        " ,but you need " + getParameterLength());
            }
            String tableName = data[1];

            Set<String> tableColumns = dbManager.getTableColumnNames(tableName);

            if (tableColumns.size() != 0) {
                drawHeader(tableColumns);
                java.util.List<Data> tableData = dbManager.getTableData(tableName);
                drawTable(tableData);
            }
    }

    private int getParameterLength() {
        return COMMAND_SAMPLE.split("[:]").length;
    }


    private void drawTable(java.util.List<Data> tableData) {
        for (Data row : tableData) {
            printRow(row);
        }
        view.type("|-----------------------|");
    }

    private void printRow(Data row) {
        String result = "|";
        for (Object value : row.getValues()) {
            result += value + "|";
        }
        view.type(result);
    }

    private void drawHeader(Set <String> tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.type("|-----------------------|");
        view.type(result);
        view.type("|-----------------------|");
    }
}
