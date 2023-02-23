package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Comprador;
import javamos_decolar.com.javamosdecolar.model.Venda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompradorRepository implements Repository<Comprador, Integer> {


    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        //TO-DO
        return null;
    }

    @Override
    public Comprador adicionar(Comprador comprador) throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public List<Comprador> listar() throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public boolean editar(Integer id, Comprador comprador) throws DatabaseException {
        //TO-DO
        return false;
    }

    @Override
    public boolean remover(Integer id) throws DatabaseException {
        //TO-DO
        return false;
    }

    public Optional<Comprador> acharCompradorPorIdUsuario(Integer idUsuario) throws DatabaseException {
        //TO-DO
        return null;
    }
}
