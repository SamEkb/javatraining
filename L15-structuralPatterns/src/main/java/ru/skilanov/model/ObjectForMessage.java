package ru.skilanov.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage copy() {
        var copy = new ObjectForMessage();
        copy.setData(new ArrayList<>(this.data));
        return copy;
    }
}
