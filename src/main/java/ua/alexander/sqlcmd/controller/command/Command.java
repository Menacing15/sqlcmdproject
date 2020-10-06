package ua.alexander.sqlcmd.controller.command;

import java.util.Scanner;

public interface Command {
    boolean processAble(String command);
    void execute(String command);

    default String[] refactorCommandWithMultipleParam(String command) { //TODO вынести default методы из инферфейса
        String[] refactored = command.split("[,]");
        String[] buffer = refactored[0].split("[:]");
        if (buffer.length == 2) {
            refactored[0] = buffer[1];
        } else {
            return new String[0];
        }
        return refactored;
    }

    default boolean verification(){
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
        }
    }

    default int getParameterLength(String command) {
        return command.split("[:]").length ;
    }

}
