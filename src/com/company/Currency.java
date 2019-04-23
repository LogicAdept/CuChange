package com.company;

import java.io.Serializable;
import java.math.BigDecimal;

public class Currency implements Comparable<Currency>, Serializable {
    public String name; //Название(аббревиатура) валюты
    public BigDecimal rate;//Отношение обменного курса

    public Currency(Object key, Object value) {
        name = key.toString();
        rate = new BigDecimal(value.toString());
    }

    @Override
    public int compareTo(Currency other) {
        return name.compareTo(other.name);
    } //Реализация метода compareTo, для возможности сортировки списка объектов Currency

    @Override
    public String toString() {
        return name + ": " + rate.setScale(5, BigDecimal.ROUND_HALF_UP);
    }
}
