package br.com.dbc.javamosdecolar.repository;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.Comprador;
import br.com.dbc.javamosdecolar.model.TipoUsuario;
import br.com.dbc.javamosdecolar.model.dto.CompanhiaDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
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

    private Companhia getCompanhiaPorResultSet(ResultSet resultSet) throws SQLException {
        Companhia companhia = new Companhia();

        companhia.setIdCompanhia(resultSet.getInt("idComprador"));
        companhia.setCnpj(resultSet.getString("cpf"));
        companhia.setNomeFantasia(resultSet.getString("nome_fantasia"));

        return companhia;
    }

    public List<Companhia> listaCompanhias() throws DatabaseException {
        List<Companhia> companhias = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();

            String sql = "SELECT * FROM COMPANHIA";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Companhia companhia = getCompanhiaPorResultSet(resultSet);
                companhias.add(companhia);
            }

            return companhias;
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

    public boolean editarCompanhia(Integer idCompanhia, Companhia companhia) throws DatabaseException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql2 = new StringBuilder();

            sql2.append("UPDATE COMPANHIA SET ");
            sql2.append("nome_fantasia = ? ");
            sql2.append("WHERE ID_COMPANHIA = ?");

            PreparedStatement statement = conexao.prepareStatement(sql2.toString());

            statement.setString(1, companhia.getNomeFantasia());
            statement.setInt(2, idCompanhia);

            StringBuilder sql3 = new StringBuilder();

            sql3.append("UPDATE USUARIO SET");
            sql3.append("senha = ?, ")  ;
            sql3.append("nome = ? ");
            sql3.append("WHERE id_usuario = ?");

            PreparedStatement statement2 = conexao.prepareStatement(sql3.toString());

            statement2.setString(1, companhia.getSenha());
            statement2.setString(2, companhia.getNome());
            statement2.setInt(3, companhia.getIdUsuario());

            int result = statement.executeUpdate();
            int result2 = statement2.executeUpdate();

            return result > 0 && result2 > 0;

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
}