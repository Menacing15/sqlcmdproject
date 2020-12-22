package ua.alexander.sqlcmd.service;

import java.util.List;

public interface Service {
    List<String> commandsList();

    void connect(String dbName, String user, String password);
}
