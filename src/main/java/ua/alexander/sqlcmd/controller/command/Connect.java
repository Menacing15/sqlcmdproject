package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.controller.tools.CommandTool;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

public class Connect implements Command {
    private static final String COMMAND_SAMPLE = "connect:sqlcmd,postgres,1234";

    private View view;
    private DataBaseManager dbManager;
    private CommandTool tool;

    public Connect(View view, DataBaseManager dbManager) {
        tool = CommandTool.getCommandTool();
        this.dbManager = dbManager;
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.startsWith("connect:");
    }

    @Override
    public void execute(String command) {
        String[] data = tool.refactorCommand(command);
        tool.validateCommandWithFixedSize(data, COMMAND_SAMPLE);
        String database = data[1];
        String username = data[2];
        String password = data[3];
        dbManager.connect(database, username, password);
        view.type("\u001B[34m" + "Success!" + "\u001B[0m");
        view.type("Please enter your command! Type 'help' to see available commands.");
    }
}
