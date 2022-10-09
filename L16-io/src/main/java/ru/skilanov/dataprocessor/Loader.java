package ru.skilanov.dataprocessor;

import ru.skilanov.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
