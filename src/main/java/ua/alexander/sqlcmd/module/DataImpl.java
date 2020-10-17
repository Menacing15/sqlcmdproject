package ua.alexander.sqlcmd.module;

import java.util.*;

public class DataImpl implements Data {
    private Map<String,Object> data = new LinkedHashMap<>();

    @Override
    public void put(String name, Object value){
        data.put(name,value);

    }

    @Override
    public List<Object> getValues() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Set<String> getNames() {
        return data.keySet();

    }

    @Override
    public Object get(String name) {
        return data.get(name);
    }

    @Override
    public String toString() {
        return
                "names:" + getNames().toString() + "\n" +
                "values:" + getValues().toString();

    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof DataImpl)) {
            return false;
        }

        DataImpl c = (DataImpl) o;

        return this.data.equals(c.data);
    }

    @Override
    public int hashCode(){
        return data.keySet().hashCode();
    }
}
