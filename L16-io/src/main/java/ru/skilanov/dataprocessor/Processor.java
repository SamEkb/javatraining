package ru.skilanov.dataprocessor;

import ru.skilanov.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
