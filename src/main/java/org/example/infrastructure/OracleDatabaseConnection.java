package org.example.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm553427";
    private static final String PASSWORD = "280603";
    public Connection getConnection(){
        Connection connection = null;
        try {
             connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

}
