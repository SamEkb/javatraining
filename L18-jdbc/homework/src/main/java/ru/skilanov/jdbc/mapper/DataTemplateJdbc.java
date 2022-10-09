package ru.skilanov.jdbc.mapper;

import ru.skilanov.core.repository.DataTemplate;
import ru.skilanov.core.repository.executor.DbExecutor;
import ru.skilanov.jdbc.mapper.api.EntityClassMetaData;
import ru.skilanov.jdbc.mapper.api.EntitySQLMetaData;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                resultSet -> entityClassMetaData.parseResultSet(resultSet)
                        .stream()
                        .findFirst().orElse(null)
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectAllSql(),
                List.of(),
                entityClassMetaData::parseResultSet
        ).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(object),
                entityClassMetaData.getValues(object)
        );
    }

    @Override
    public void update(Connection connection, T object) {
        dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getUpdateSql(),
                entityClassMetaData.getValues(object));
    }
}
