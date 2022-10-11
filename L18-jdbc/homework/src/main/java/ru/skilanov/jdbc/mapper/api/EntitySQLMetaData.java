package ru.skilanov.jdbc.mapper.api;

/**
 * Создает SQL - запросы
 */
public interface EntitySQLMetaData<T> {
    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSql(T object);

    String getUpdateSql();
}
