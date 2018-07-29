package com.pro_crafting.tools.recordjarconverter.service.model;

import java.util.Objects;

public class Field<K, V> {
    private K key;
    private V value;

    public Field() {

    }

    public Field(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field<?, ?> field = (Field<?, ?>) o;
        return Objects.equals(key, field.key) &&
                Objects.equals(value, field.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "Field{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
