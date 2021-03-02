package ua.alexander.sqlcmd.controller.runner;

import ua.alexander.sqlcmd.controller.command.*;
import ua.alexander.sqlcmd.module.DataBaseManager;
import ua.alexander.sqlcmd.view.Console;
import ua.alexander.sqlcmd.view.View;

public class MainController {
    private View view;
    private Command[] commands;

    public MainController(View view, DataBaseManager dbManager) {
        this.view = new Console();
        this.commands = new Command[]{
                new Connect(view, dbManager),
                new Exit(),
                new Help(view),
                new IsConnected(view, dbManager),
                new Insert(view, dbManager),
                new Update(view, dbManager),
                new Clear(view, dbManager),
                new Create(view, dbManager),
                new Drop(view, dbManager),
                new Delete(view, dbManager),
                new Unsupported(view)
        };
    }

    public void run() {
        try {
            work();
        } catch (ExitException e) {
            view.type("See ya!");
        }
    }

    private void work() {
        view.type("Hi, friend! Please insert database name, username and password. Format: connect:database,username,password");
        while (true) {
            String input = view.read();
            for (Command command : commands) {
                try {
                    if (command.processAble(input)) {
                        command.execute(input);
                        break;
                    }
                } catch (Exception ex) {
                    if (ex instanceof ExitException) {
                        throw ex;
                    }
                    printError(ex);
                    break;
                }
            }
        }

    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        System.out.println(("\u001B[31m" + "Failed, the reason is: " + message + "\u001B[0m" + "\nTry again!"));
    }
}
