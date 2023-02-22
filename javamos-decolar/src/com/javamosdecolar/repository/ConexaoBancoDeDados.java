package javamos_decolar.com.javamosdecolar.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {
    private static final String SERVER = "vemser-dbc.dbccompany.com.br";
    private static final String PORT = "25000";
    private static final String DATABASE = "xe";

    // Configuração dos parâmetros de autenticação
    private static final String USER = "system";
    private static final String PASS = "YKvrXrIlwWsz";
    private static final String SCHEMA = "AVIACAO";

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;

        // Abre-se a conexão com o Banco de Dados
        Connection con = DriverManager.getConnection(url, USER, PASS);

        // sempre usar o schema vem_ser
        con.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return con;
    }
}
