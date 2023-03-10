package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RepositoryCRUD<T, K> {
    Integer getProximoId(Connection connection) throws SQLException;
    List<T> getAll() throws DatabaseException;
    T create(T t) throws DatabaseException;
    boolean update(K id, T t) throws DatabaseException;
    boolean delete(K id) throws DatabaseException;
}