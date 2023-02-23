package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Venda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VendaRepository implements Repository<Venda, Integer> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        //TO-DO
        return null;
    }

    @Override
    public Venda adicionar(Venda venda) throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public List<Venda> listar() throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public boolean editar(Integer id, Venda venda) throws DatabaseException {
        //TO-DO
        return false;
    }

    @Override
    public boolean remover(Integer id) throws DatabaseException {
        //TO-DO
        return false;
    }

    public Optional<Venda> buscaVendaPorCodigo(String codigo) throws DatabaseException {
        // TO-DO
        return null;
    }

    public boolean cancelarVenda(Integer idVenda, Venda venda) {
        //TO-DO
        return false;
    }
}
