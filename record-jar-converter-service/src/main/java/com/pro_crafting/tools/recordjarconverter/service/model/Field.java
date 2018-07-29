package com.pro_crafting.tools.recordjarconverter.service.model;

import java.util.Objects;

public class Field<KEY_TYPE, VALUE_TYPE> {
    private KEY_TYPE key;
    private VALUE_TYPE value;

    public Field() {

    }

    public Field(KEY_TYPE key, VALUE_TYPE value) {
        this.key = key;
        this.value = value;
    }

    public KEY_TYPE getKey() {
        return key;
    }

    public void setKey(KEY_TYPE key) {
        this.key = key;
    }

    public VALUE_TYPE getValue() {
        return value;
    }

    public void setValue(VALUE_TYPE value) {
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
