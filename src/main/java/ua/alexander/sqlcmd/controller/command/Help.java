package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.view.View;

public class Help implements Command {

    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean processAble(String command) {
        return command.equals("help");
    }

    @Override
    public void execute(String command) {
        view.type("There are such commands:");
        view.type("\thelp - to see all commands available.");
        view.type("\tconnect:database,username,password  - to connect to a certain database");
        view.type("\ttables -  to get all table names of the database you are connected to.");
        view.type("\tcreate:tableName,column1,column2,...,columnN -  to create a table with a given name and columns");
        view.type("\tinsert:tableName,column1,value1,column2,value2,...,columnN,valueN - \n" +
                "\t\tto make a new record in the table");
        view.type("\tupdate:tableName,column1,value1,column2,value2,...,columnN,valueN - \n" +
                "\t\tto update a record in a table , where there is a record column1 = value1");
        view.type("\tfind:tableName - to draw the table");
        view.type("\tdrop:tableName - to drop the table");
        view.type("\tclear:tableName - to clear table's content");
        view.type("\tdelete:tableName,column,value - to delete a record in a table");
        view.type("\texit - to shut down the program.");

    }
}
