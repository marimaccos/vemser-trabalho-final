package javamos_decolar.com.javamosdecolar.repository;

import javamos_decolar.com.javamosdecolar.exceptions.DatabaseException;
import javamos_decolar.com.javamosdecolar.model.Companhia;
import javamos_decolar.com.javamosdecolar.model.Comprador;
import javamos_decolar.com.javamosdecolar.model.TipoUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanhiaRepository {

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_companhia.nextval mysequence from DUAL";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException(e.getCause());
        }
    }

    public Companhia adicionar(Companhia companhia) throws DatabaseException {
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(conexao);
            companhia.setIdCompanhia(proximoId);

            String sql = "INSERT INTO COMPANHIA \n" +
                    "(ID_COMPANHIA, CNPJ, NOME_FANTASIA, ID_USUARIO)\n" +
                    "VALUES(?, ?, LOWER(?), ?)";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, companhia.getIdCompanhia());
            preparedStatement.setString(2, companhia.getCnpj());
            preparedStatement.setString(3, companhia.getNomeFantasia());
            preparedStatement.setInt(4, companhia.getIdUsuario());

            int result = preparedStatement.executeUpdate();
            System.out.println("adicionarCompanhia.res=" + result);
            return companhia;

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

    public Optional<Companhia> buscaCompanhiaPorNome (String nome) throws DatabaseException {
        Companhia companhiaPesquisa = new Companhia();
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT ID_COMPANHIA, CNPJ, NOME_FANTASIA FROM COMPANHIA c \n" +
                    "WHERE c.NOME_FANTASIA LIKE ?";

            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setString(1, "%" + nome + "%".toLowerCase());

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                companhiaPesquisa.setIdCompanhia(resultSet.getInt("id_companhia"));
                companhiaPesquisa.setCnpj(resultSet.getString("cnpj"));
                companhiaPesquisa.setNomeFantasia(resultSet.getString("nome_fantasia"));
                return Optional.of(companhiaPesquisa);
            } else {
                return Optional.empty();
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
    }

    public Optional<Companhia> buscaCompanhiaPorIdUsuario(Integer idUsuario) throws DatabaseException {
        Companhia companhiaPesquisa = new Companhia();
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT ID_COMPANHIA, CNPJ, NOME_FANTASIA, ID_USUARIO FROM COMPANHIA c \n" +
                    "WHERE c.ID_USUARIO = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, idUsuario);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                companhiaPesquisa.setIdCompanhia(resultSet.getInt("id_companhia"));
                companhiaPesquisa.setCnpj(resultSet.getString("cnpj"));
                companhiaPesquisa.setNomeFantasia(resultSet.getString("nome_fantasia"));
                companhiaPesquisa.setIdUsuario(resultSet.getInt("id_usuario"));
                return Optional.of(companhiaPesquisa);

            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
    }
}
