package server.database;

import server.models.User;

import java.sql.*;

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

    //Begin SQL statements
    public User getUser(String username, String password) throws Exception {
        ResultSet rs = null;
        User userDTO = null;

        try {

            PreparedStatement getUser = connection.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?");

            getUser.setString(1, username);
            getUser.setString(2, password);

            rs = getUser.executeQuery();

            while(rs.next()) {
                userDTO = new User();
                userDTO.setUserid(rs.getInt("id"));
                userDTO.setUsername(rs.getString("username"));
                userDTO.setPassword(rs.getString("password"));
                //Virker boolean og tinyInt på sammme vis?
                userDTO.setPersonel(rs.getBoolean("isPersonel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userDTO;
    }

}
