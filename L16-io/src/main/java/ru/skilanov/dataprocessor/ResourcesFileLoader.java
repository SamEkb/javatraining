package ru.skilanov.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ru.skilanov.model.Measurement;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {
    private final ObjectMapper mapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
    }

    @Override
    public List<Measurement> load() {
        Gson gson = new Gson();
        var js = ResourcesFileLoader.class.getClassLoader().getResourceAsStream("inputData.json");
        JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(js)));
        return gson.fromJson(reader, new TypeToken<List<Measurement>>(){}.getType());
    }
}
