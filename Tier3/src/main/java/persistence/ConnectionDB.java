package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static ConnectionDB instance;
    private String url;
    private String schemaName;
    private String username;
    private String password;

    private final static String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=";
    private final static String SCHEMA_NAME = "sep3_tier3";
    private final static String USERNAME = "postgres";
    private final static String PASSWORD = "kubino";



    private ConnectionDB() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized ConnectionDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }


    public Connection getConnection() throws SQLException {
        this.url = URL;
        this.schemaName = SCHEMA_NAME;
        this.username = USERNAME;
        this.password = PASSWORD;

        return DriverManager.getConnection(url + schemaName, username, password);
    }
}
