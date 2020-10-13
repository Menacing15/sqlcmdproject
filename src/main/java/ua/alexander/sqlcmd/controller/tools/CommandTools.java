package ua.alexander.sqlcmd.controller.tools;

import ua.alexander.sqlcmd.controller.command.Command;
import ua.alexander.sqlcmd.controller.command.ExitException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntFunction;

public class CommandTools {

     public boolean verification(){
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

    public String[] refactorCommandWithMultipleParam(String command) {
        String[] comma = command.split("[,]");
        if(comma.length != 1) {
            String[] colon = comma[0].split("[:]");
            List<String> list = new ArrayList<>(Arrays.asList(comma));
            list.set(0, colon[0]);
            list.add(1, colon[1]);
            return list.toArray(new String[0]);
        }else{
            return comma[0].split("[:]");
        }
    }

    public void validateCommandWithFixedSize(String [] data, String sample) throws IllegalArgumentException {
        if (data.length != getParameterLength(sample)) {
            throw new IllegalArgumentException("Something is missing... Quantity of parameters is " + data.length +
                    " ,but you need " + getParameterLength(sample));
        }
    }

    public void validateCommandWithCustomSize(String [] data, String command) throws IllegalArgumentException {
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Some parameters are missing. " +
                    "The command should look like that: \n" +
                    "'" + data[0] + ":tableName,column1,value1,column2,value2...columnN,valueN', " +
                    "but your is: '%s'", command));
        }
    }

    public int getParameterLength(String command) {
        return command.split("[:]").length + command.split("[,]").length - 1;
    }
}