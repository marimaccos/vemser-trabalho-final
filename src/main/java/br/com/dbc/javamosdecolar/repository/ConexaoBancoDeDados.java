package br.com.dbc.javamosdecolar.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConexaoBancoDeDados {

    @Value("${db.oracle.server}")
    private String server;

    @Value("${db.oracle.port}")
    private String port;
   
    @Value("${db.oracle.database}")
    private String database;

    // Configuração dos parâmetros de autenticação
    @Value("${db.oracle.schema}")
    private String schema;

    @Value("${db.oracle.user}")
    private String user;

    @Value("${db.oracle.password}")
    private String password;

    public Connection getConnection() throws SQLException {
        // Formatação do Oracle
        String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database + "?serverTimezone=UTC" ;

        // Abre-se a conexão com o Banco de Dados
        Connection connection = DriverManager.getConnection(url, user, password);

        // Seleciona o schema
        connection.createStatement().execute("alter session set current_schema=" + schema);

        return connection;
    }
}
