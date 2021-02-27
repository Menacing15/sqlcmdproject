package ua.alexander.sqlcmd.service;

import org.springframework.stereotype.Component;
import ua.alexander.sqlcmd.module.DataBaseManager;

@Component
public interface DataBaseManagerFactory {
    DataBaseManager createManager();
}
