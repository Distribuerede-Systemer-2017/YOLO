package server.controllers;
import server.database.DBConnection;
import server.endpoints.RootEndpoint;
import server.endpoints.StaffEndpoint;
import server.endpoints.UserEndpoint;
import server.models.User;
import server.utility.Digester;

public class MainController {
private DBConnection dbConnection;

import server.utility.Globals;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

    private StaffController staffController;
    private UserController userController;
    private UserEndpoint userEndpoint;
    private StaffEndpoint staffEndpoint;
    private Digester dig;
    private RootEndpoint rootEndpoint;

    public MainController() {
        dbConnection = new DBConnection();
        staffController = new StaffController(dbConnection);
        userController = new UserController(dbConnection);
        userEndpoint = new UserEndpoint();
        staffEndpoint = new StaffEndpoint();
        dig = new Digester();
    }
  
    public User authorizeUser(User user){
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        User userCheck = dbConnection.authorizeUser(user);
        return userCheck;
    }

    public void deleteToken(int id){
        dbConnection.deleteToken(id);
    }

    public boolean checkTokenOwner(String token){
        Boolean check = dbConnection.tokenExists(token);
        return check;
    }


}