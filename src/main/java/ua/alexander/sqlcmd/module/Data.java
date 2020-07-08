package ua.alexander.sqlcmd.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Data {
    static class MetaData {
        private String name;
        private Object value;

        public MetaData(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

    List<MetaData> metaData = new ArrayList<>();
    int index = 0;

    public void put(String name, Object value){
        metaData.add(new MetaData(name, value));
        index++;
    }


    public Object[] getValues() {
        Object[] result = new Object[index];
        int index = 0;
        for (MetaData element : metaData) {
            result[index++] = element.getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[index];
        int index = 0;
        for (MetaData element : metaData) {
            result[index++] = element.getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return
                "names:" + Arrays.toString(getNames()) + "\n" +
                "values:" + Arrays.toString(getValues());

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Data) {
            Data passedData = (Data) o;
            if(metaData.size() == passedData.metaData.size()) {
                Iterator<MetaData> passedIter = passedData.metaData.iterator();
                for (MetaData md : metaData) {
                    MetaData next = passedIter.next();
                    if (!(md.getName().equals(next.getName()) &&
                           md.getValue().equals(next.getValue()))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(metaData.toArray());
    }
}
