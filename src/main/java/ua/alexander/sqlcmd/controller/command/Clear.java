package ua.alexander.sqlcmd.controller.command;

import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.View;

import java.util.Scanner;

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
        if(clearVerification())
            processClearing(command);
        else
            view.type("Table wasn't cleared.");
    }


    public boolean clearVerification(){
        view.type("Are you sure you want to delete all information from the table? Type 'y' to confirm, 'n' to discard");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String command = scanner.nextLine();
            if (command.equals("y")) {
                return true;
            } else if (command.equals("n")) {
                return false;
            } else if (command.equals("exit")) {
                throw new ExitException();
            }
            view.type("Wrong format!");
        }
    }
    public void processClearing(String command){
        String[] data = command.split("[:]");

        if (data.length  != getParameterLength()) {
            throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length +
                    " ,but you need " + getParameterLength());
        }
        String tableName = data[1];
        dbManager.clearTable(tableName);
        view.type(String.format("Table '%s' was cleared successfully!", tableName));
    }

    private int getParameterLength() {
        return COMMAND_SAMPLE.split("[:]").length ;
    }
}
