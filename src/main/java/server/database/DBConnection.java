package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class responsible for establishing connection between the database and the server
 */

public class DBConnection {

    private static Connection connection = null;

    /**
     * Attempts to create the connection
     */
    public DBConnection() {
        try {
            connection = DriverManager.getConnection(System.getenv("jdbc:mysql://" + "DATABASE_HOST" + ":" + "DATABASE_PORT" + "/" + System.getenv("DATABASE_NAME")),
                    System.getenv("DATABASE_USER"), System.getenv("DATABASE_PASSWORD"));
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

    public boolean addUser(){

    }

    public boolean addOrder(){

    }

    public void findOrderById(int id){
    }

    public void getItems(){

    }

    public void authorizeUser(String username, String password){

    }

    //Begin SQL statements
    
}
