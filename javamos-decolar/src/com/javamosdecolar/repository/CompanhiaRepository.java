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

    public Companhia adicionar(Companhia companhia, Integer idUsuario) throws DatabaseException {
        Connection conexao = null;

        try {
            conexao = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(conexao);
            companhia.setIdCompanhia(proximoId);

            String sql = "INSERT INTO COMPANHIA \n" +
                    "(ID_COMPRADOR, LOGIN, SENHA, NOME, TIPO, CPF, NOME_FANTASIA)\n" +
                    "VALUES(?, ?, ?, ?, 1, ?, ?)";

            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, companhia.getIdCompanhia());
            preparedStatement.setString(2, companhia.getLogin());
            preparedStatement.setString(3, companhia.getSenha());
            preparedStatement.setString(4, companhia.getNome());
            preparedStatement.setString(5, companhia.getCnpj());
            preparedStatement.setString(6, companhia.getNomeFantasia());

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
    public List<Companhia> listar() throws DatabaseException {
        List<Companhia> companhias = new ArrayList<>();
        Connection conexao = null;

        try{
            conexao = ConexaoBancoDeDados.getConnection();
            Statement statement = conexao.createStatement();

            String sql = "SELECT * FROM COMPANHIA";

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Companhia companhia = new Companhia();
                companhia.setIdCompanhia(resultSet.getInt("id_companhia"));
                companhia.setLogin(resultSet.getString("login"));
                companhia.setSenha(resultSet.getString("senha"));
                companhia.setNome(resultSet.getString("nome"));
                companhia.setTipoUsuario(TipoUsuario.ofTipo(resultSet.getInt("tipo")));
                companhia.setCnpj(resultSet.getString("cnpj"));
                companhia.setNomeFantasia(resultSet.getString("nome_fantasia"));
                companhias.add(companhia);
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
        return companhias;
    }

    @Override
    public boolean editar(Integer id, Companhia companhia) throws DatabaseException {
        Connection conexao = null;
        try {
            conexao = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE COMPANHIA SET ");
            sql.append("login = ?,");
            sql.append("senha = ?,");
            sql.append("nome = ?, ");
            sql.append("cnpj = ?");
            sql.append("nome_fantasia = ?");
            sql.append("WHERE id_companhia = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setString(1, companhia.getLogin());
            statement.setString(2, companhia.getSenha());
            statement.setString(3, companhia.getNome());
            statement.setString(4, companhia.getCnpj());
            statement.setString(5, companhia.getNomeFantasia());
            statement.setInt(6, id);

            int result = statement.executeUpdate();
            System.out.println("editarCompanhia.res=" + result);

            return result > 0;
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

    public boolean remover(Integer id) throws DatabaseException {
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM COMPANHIA WHERE id_companhia = ?";

            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, id);

            int result = statement.executeUpdate();
            System.out.println("removerCompanhiaPorId.res=" + result);

            return result > 0;
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

    public Optional<Companhia> buscaCompanhiaPorNome (String nome) throws DatabaseException {
        Companhia companhiaPesquisa = new Companhia();
        Connection conexao = null;
        try{
            conexao = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM COMPANHIA WHERE nome = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setString(1, nome);

            ResultSet resultSet = statement.executeQuery(sql);

            companhiaPesquisa.setIdCompanhia(resultSet.getInt("id_comprador"));
            companhiaPesquisa.setLogin(resultSet.getString("login"));
            companhiaPesquisa.setSenha(resultSet.getString("senha"));
            companhiaPesquisa.setNome(resultSet.getString("nome"));
            companhiaPesquisa.setTipoUsuario(TipoUsuario.ofTipo(resultSet.getInt("tipo")));
            companhiaPesquisa.setCnpj(resultSet.getString("cpf"));
            companhiaPesquisa.setNomeFantasia(resultSet.getString("nome_fantasia"));

            if(resultSet.first()) {
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

            String sql = "SELECT * FROM COMPANHIA WHERE id_usuario = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);

            statement.setInt(1, idUsuario);

            ResultSet resultSet = statement.executeQuery(sql);


            companhiaPesquisa.setIdCompanhia(resultSet.getInt("id_comprador"));
            companhiaPesquisa.setLogin(resultSet.getString("login"));
            companhiaPesquisa.setSenha(resultSet.getString("senha"));
            companhiaPesquisa.setNome(resultSet.getString("nome"));
            companhiaPesquisa.setTipoUsuario(TipoUsuario.ofTipo(resultSet.getInt("tipo")));
            companhiaPesquisa.setCnpj(resultSet.getString("cpf"));
            companhiaPesquisa.setNomeFantasia(resultSet.getString("nome_fantasia"));


            if(resultSet.first()) {
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
}
