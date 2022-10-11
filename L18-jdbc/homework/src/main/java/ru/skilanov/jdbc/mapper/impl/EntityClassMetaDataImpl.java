package ru.skilanov.jdbc.mapper.impl;

import ru.skilanov.crm.annotation.Id;
import ru.skilanov.exceptions.NoSuchConstructorException;
import ru.skilanov.jdbc.mapper.api.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final String className;
    private final Constructor<T> constructor;
    private final List<Field> fields;

    public List<Field> getFieldsWithoutAnnotation() {
        return fieldsWithoutAnnotation;
    }

    public Field getAnnotatedField() {
        return annotatedField;
    }

    private final List<Field> fieldsWithoutAnnotation;
    private final Field annotatedField;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.className = clazz.getSimpleName();
        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new NoSuchConstructorException("Constructor does not exist");
        }
        this.fields = List.of(clazz.getDeclaredFields());
        this.fieldsWithoutAnnotation = getFieldsWithoutId();
        this.annotatedField = getIdField();

    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return fields.stream()
                .filter(it -> it.isAnnotationPresent(Id.class))
                .findFirst().orElse(null);
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fields.stream()
                .filter(it -> !it.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<T> parseResultSet(ResultSet rs) {
        List<T> objects = new ArrayList<>();
        try {
            while (rs.next()) {
                T object = getConstructor().newInstance();
                for (var field : getAllFields()) {
                    field.setAccessible(true);
                    field.set(object, rs.getObject(field.getName()));
                }
                objects.add(object);
            }
        } catch (SQLException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return objects;
    }

    @Override
    public List<Object> getValues(T object) {
        return getAllFields().stream()
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(object);
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
