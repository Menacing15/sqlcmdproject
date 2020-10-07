package ua.alexander.sqlcmd.view;

import ua.alexander.sqlcmd.module.Data;

import java.util.List;
import java.util.Set;

public interface View {
     String read();
     void type(String message);
     void drawTable(List<Data> tableData);
     void printRow(Data row);
     void drawHeader(Set<String> tableColumns);
}
