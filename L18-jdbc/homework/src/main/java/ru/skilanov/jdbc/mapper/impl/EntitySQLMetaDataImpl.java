package ru.skilanov.jdbc.mapper.impl;

import ru.skilanov.jdbc.mapper.api.EntityClassMetaData;
import ru.skilanov.jdbc.mapper.api.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    public static final String SELECT_QUERY = "SELECT %s FROM %s";
    public static final String SELECT_WHERE_QUERY = "SELECT %s FROM %s where %s = ?";
    public static final String INSERT_QUERY = "INSERT INTO %s(%s) VALUES(%s)";
    public static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s = ?";
    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format(SELECT_QUERY, getAllEntityFields(), entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(SELECT_WHERE_QUERY,
                getAllEntityFields(),
                entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName()
        );
    }

    @Override
    public String getInsertSql(T object) {
        return String.format(INSERT_QUERY,
                entityClassMetaData.getName(),
                getAllEntityFieldsWithoutId(object),
                getAllFieldsForInsertQuery(object));
    }

    @Override
    public String getUpdateSql() {
        return String.format(UPDATE_QUERY,
                entityClassMetaData.getName(),
                getAllFieldsForUpdateQuery(),
                entityClassMetaData.getIdField().getName()
        );
    }

    private String getAllFieldsForUpdateQuery() {
        return entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName() + "=?")
                .collect(Collectors.joining(","));
    }

    private String getAllFieldsForInsertQuery(T object) {
        return entityClassMetaData.getFieldsWithoutId().stream().filter(field -> {
                    field.setAccessible(true);
                    try {
                        return Objects.nonNull(field.get(object));
                    } catch (IllegalAccessException ignored) {
                        return false;
                    }
                }).map(field -> "?")
                .collect(Collectors.joining(","));
    }

    private String getAllEntityFieldsWithoutId(T object) {
        return entityClassMetaData.getFieldsWithoutId().stream().filter(field -> {
                    field.setAccessible(true);
                    try {
                        return Objects.nonNull(field.get(object));
                    } catch (IllegalAccessException ignored) {
                        return false;
                    }
                })
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }

    private String getAllEntityFields() {
        return entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }
}
