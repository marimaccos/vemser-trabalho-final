package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Comprador;
import javamos_decolar.com.javamosdecolar.model.TipoUsuario;
import javamos_decolar.com.javamosdecolar.model.Passagem;
import javamos_decolar.com.javamosdecolar.model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompradorRepository implements Repository<Comprador, Integer> {


    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_comprador.nextval mysequence from DUAL";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        }
    }

    @Override
    public Comprador adicionar(Comprador comprador) throws DatabaseException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(conexao);
            comprador.setIdComprador(proximoId);

            String sql = "INSERT INTO PESSOA \n" +
                    "(ID_COMPRADOR, LOGIN, SENHA, NOME, TIPO, CPF)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, comprador.getIdComprador());
            preparedStatement.setString(2, comprador.getLogin());
            preparedStatement.setString(3, comprador.getSenha());
            preparedStatement.setString(4, comprador.getNome());
            preparedStatement.setInt(5,2);
            preparedStatement.setString(6, comprador.getCpf());

            int res = preparedStatement.executeUpdate();
            System.out.println("adicionarPessoa.res=" + res);
            return comprador;

        } catch (SQLException e) {
            throw new DatabaseException(e.getCause());
        } finally {
            try{
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Comprador> listar() throws DatabaseException {
        List<Comprador> compradores = new ArrayList<>();
        Connection conexao = null;

        try{
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();

            String sql = "SELECT * FROM COMPRADOR";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Comprador comprador = new Comprador();
                comprador.setIdComprador(resultSet.getInt("id_comprador"));
                comprador.setLogin(resultSet.getString("login"));
                comprador.setSenha(resultSet.getString("senha"));
                comprador.setNome(resultSet.getString("nome"));
                comprador.setTipo(TipoUsuario.ofTipo(resultSet.getInt("tipo")));
                comprador.setCpf(resultSet.getString("cpf"));
                compradores.add(comprador);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getCause());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return compradores;
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
