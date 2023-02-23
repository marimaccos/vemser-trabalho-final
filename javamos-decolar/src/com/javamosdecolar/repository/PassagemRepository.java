package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Companhia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PassagemRepository implements Repository<Passagem, Integer> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        //TO-DO
        return null;
    }

    @Override
    public Passagem adicionar(Passagem passagem) throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public List<Passagem> listar() throws DatabaseException {
        //TO-DO
        return null;
    }

    @Override
    public boolean editar(Integer id, Passagem passagem) throws DatabaseException {
        //TO-DO
        return false;
    }

    @Override
    public boolean remover(Integer id) throws DatabaseException {
        //TO-DO
        return false;
    }

    public void mudarStatusDaPassagem(Integer status, Passagem passagem) throws DatabaseException {
        // fazer uma edição na passagem pra trocar o status pelo do código passado
        // consultar o enum?
    }

    public List<Passagem> pegarPassagemPorDataPartida(LocalDate data) throws DatabaseException {
        // TO-DO
        return null;
    }

    public List<Passagem> pegarPassagemPorDataChegada(LocalDate data) throws DatabaseException {
        // TO-DO
        return null;
    }

    public Optional<Passagem> pegarPassagemPorValor(BigDecimal valorMaximo) throws DatabaseException {
        //TO-DO
        return null;
    }

    public List<Passagem> pegarPassagemPorCompanhia(Integer idCompanhia) throws DatabaseException {
        //TO-DO
        return null;
    }
}
