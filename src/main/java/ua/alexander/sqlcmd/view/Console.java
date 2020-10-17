package ua.alexander.sqlcmd.view;

import ua.alexander.sqlcmd.module.Data;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class Console implements View {
    public void type(String message) {
        System.out.println(message);
    }

    @Override
    public void drawTable(Set<String> tableColumns, List<Data> tableData) {
        drawHeader(tableColumns);
        drawInnerData(tableData);
    }

    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

     void drawInnerData(List<Data> tableData) {
        for (Data row : tableData) {
            printRow(row);
        }
        System.out.println("|-----------------------|");
    }

    void printRow(Data row) {
        String result = "|";
        for (Object value : row.getValues()) {
            result += value + "|";
        }
        System.out.println(result);
    }

    void drawHeader(Set<String> tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        System.out.println("|-----------------------|");
        System.out.println(result);
        System.out.println("|-----------------------|");
    }
}
