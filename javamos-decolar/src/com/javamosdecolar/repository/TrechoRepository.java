package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Trecho;
import javamos_decolar.com.javamosdecolar.model.Companhia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrechoRepository implements Repository<Trecho, Integer> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        //TO-DO
        return null;
    }

    @Override
    public Trecho adicionar(Trecho trecho) throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public List<Trecho> listar() throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public boolean editar(Integer id, Trecho trecho) throws DatabaseException {
        //TO-DO
        return false;
    }

    @Override
    public boolean remover(Integer id) throws DatabaseException {
        //TO-DO
        return false;
    }

    public Optional<Trecho> buscarTrecho(String origem, String destino, Companhia companhia) throws DatabaseException {
        // TO-DO
        return null;
    }

    public Optional<Trecho> buscarTrechoPorId(Integer idTrecho) throws DatabaseException {
        //TO-DO
        return null;
    }
}
