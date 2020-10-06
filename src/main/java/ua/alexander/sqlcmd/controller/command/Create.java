package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Create implements Command{
    private static final String COMMAND_SAMPLE = "create:test,";
    private DataBaseManager dbManager;
    private View view;

    public Create(View view, DataBaseManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("create:");
    }

    @Override
    public void execute(String command) {
        String[] data = refactorCommandWithMultipleParam(command);
        if (data.length % 2 != 1) {
            throw new IllegalArgumentException(String.format("Some parameters are missing. " +
                    "The command should look like that: \n" +
                    "'create:tableName,column1,type1,column2,type2...columnN,typeN', " +
                    "but your is: '%s'", command));
        }
        String columnsNameAndType= "";
        String tableName = data[0];
        for(int i = 1; i < data.length; i++){
            if(i % 2 == 0)
                columnsNameAndType += data[i] + ", ";
            else
                columnsNameAndType += data[i] + " ";
        }
        dbManager.createTable(tableName,columnsNameAndType);
        view.type(String.format("Table '%s' was created successfully!", tableName));
    }


}
