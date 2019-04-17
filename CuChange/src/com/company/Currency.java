package com.company;

public class Currency implements Comparable<Currency>{
    public String name;
    public String rate;

    public Currency(Object key, Object value) {
        name = key.toString();
        rate = value.toString();
    }
    @Override
    public int compareTo(Currency other) {
        return name.compareTo(other.name);
    }
    @Override
    public String toString() {
        return name+": "+rate;
    }
}
