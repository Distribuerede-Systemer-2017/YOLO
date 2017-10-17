package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import server.config.Config;

/**
 * Class responsible for establishing connection between the database and the server
 */

public class DBConnection {

    private static Connection connection = null;

    /**
     * Attempts to create the connection to database
     * Gets variables from config file
     */
    public DBConnection() {
        try {
            connection = DriverManager.getConnection(("jdbc:mysql://" + Config.getDatabaseHost() + ":"
                            + Config.getDatabasePort() + "/" + Config.getDatabaseName()),
                    Config.getDatabaseUser(), Config.getDatabasePassword());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void close() {
        try {
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    //Begin SQL statements
    
}
