package ru.skilanov.dataprocessor;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (var writer = new BufferedWriter(new FileWriter(this.fileName))) {
            writer.write(new Gson().toJson(data));
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
