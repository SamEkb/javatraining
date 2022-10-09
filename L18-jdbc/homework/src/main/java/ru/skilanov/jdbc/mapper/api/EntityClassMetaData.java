package ru.skilanov.jdbc.mapper.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

/**
 * "Разбирает" объект на составные части
 */
public interface EntityClassMetaData<T> {
    String getName();

    Constructor<T> getConstructor();

    //Поле Id должно определять по наличию аннотации Id
    //Аннотацию @Id надо сделать самостоятельно
    Field getIdField();

    List<Field> getAllFields();

    List<Field> getFieldsWithoutId();

    List<T> parseResultSet(ResultSet rs);

    List<Object> getValues(T object);
}

