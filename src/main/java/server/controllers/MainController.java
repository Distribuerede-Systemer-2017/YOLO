package server.controllers;

import server.database.DBConnection;
import server.endpoints.RootEndpoint;
import server.endpoints.StaffEndpoint;
import server.endpoints.UserEndpoint;
import server.models.User;
import server.utility.Digester;

public class MainController {
    private Digester dig;

    public MainController() {
        dig = new Digester();
    }

    public User authorizeUser(User user) {
        DBConnection dbConnection = new DBConnection();
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        User userCheck = dbConnection.authorizeUser(user);
        return userCheck;
    }

    public boolean deleteToken(int id) {
        DBConnection dbConnection = new DBConnection();
       int result = dbConnection.deleteToken(id);

       if(result>0){
           return true;
       }
       return false;
    }

    public boolean checkTokenOwner(String token) {
        DBConnection dbConnection = new DBConnection();
        String serverToken = dbConnection.tokenExists(token);
        if(serverToken.equals(token)){
            return true;
        }
        return false;
    }

    public void createToken(User tokenUser, String token){
        DBConnection dbConnection = new DBConnection();
        dbConnection.createToken(tokenUser, token);
    }

}