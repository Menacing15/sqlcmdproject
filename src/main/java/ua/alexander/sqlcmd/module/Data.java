package ua.alexander.sqlcmd.module;

import java.util.List;
import java.util.Set;

public interface Data {
    void put(String name, Object value);

    List<Object> getValues();

    Set<String> getNames();

    Object get(String name);
}
