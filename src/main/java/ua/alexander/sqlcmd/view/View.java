package ua.alexander.sqlcmd.view;

import ua.alexander.sqlcmd.module.Data;

import java.util.List;
import java.util.Set;

public interface View {
     String read();
     void type(String message);
     void drawTable(Set<String> tableColumns, List<Data> tableData);
}
