package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.Set;

public class Tables implements Command {
    private DataBaseManager dbManager;
    private View view;

    public Tables(View view, DataBaseManager dbManager){
        this.view = view;
        this.dbManager = dbManager;
    }
    @Override
    public boolean processAble(String command) {
        return command.equals("tables");
    }

    @Override
    public void execute(String command) {
        Set<String> list = dbManager.getTableNames();
        view.type(list.toString());

    }
}
