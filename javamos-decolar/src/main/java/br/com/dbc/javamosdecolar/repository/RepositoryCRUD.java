package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RepositoryCRUD<T, K> {
    Integer getProximoId(Connection connection) throws SQLException;
    T adicionar(T t) throws DatabaseException;
    List<T> listar() throws DatabaseException;
    boolean editar(K id, T t) throws DatabaseException;
    boolean remover(K id) throws DatabaseException;
}