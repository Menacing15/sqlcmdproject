package ua.alexander.sqlcmd.service;

public class ServiceFactory {

    private Service service;

    public Service getService(){
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
