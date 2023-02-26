package javamos_decolar.com.javamosdecolar.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {
    private static final String SERVER = "vemser-dbc.dbccompany.com.br";
    private static final String PORT = "25000";
    private static final String DATABASE = "xe";

    // Configuração dos parâmetros de autenticação
    private static final String USER = "AVIACAO";
    private static final String PASS = "YKvrXrIlwWsz";
    private static final String SCHEMA = "AVIACAO";

    public static Connection getConnection() throws SQLException {
        // Formatação do Oracle
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;

        // Abre-se a conexão com o Banco de Dados
        Connection connection = DriverManager.getConnection(url, USER, PASS);

        // Seleciona o schema
        connection.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return connection;
    }
}
