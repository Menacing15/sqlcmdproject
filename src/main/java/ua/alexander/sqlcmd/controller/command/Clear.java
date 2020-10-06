package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Clear implements Command {
    private static final String COMMAND_SAMPLE = "clear:user";
    private DataBaseManager dbManager;
    private View view;

    public Clear(View view, DataBaseManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("clear:");
    }

    @Override
    public void execute(String command) {
        view.type("Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard");
        if(verification())
            executeClearing(command);
        else
            view.type("Table wasn't cleared.");
    }


    public void executeClearing(String command){
        String[] data = command.split("[:]");
        if (data.length  != getParameterLength(COMMAND_SAMPLE)) {
            throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length +
                    " ,but you need " + getParameterLength(COMMAND_SAMPLE));
        }
        String tableName = data[1];
        dbManager.clearTable(tableName);
        view.type(String.format("Table '%s' was cleared successfully!", tableName));
    }


}
