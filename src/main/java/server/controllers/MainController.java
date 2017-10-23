package server.controllers;

import server.database.DBConnection;
import server.endpoints.RootEndpoint;
import server.endpoints.StaffEndpoint;
import server.endpoints.UserEndpoint;
import server.models.User;
import server.utility.Digester;

public class MainController {
    private DBConnection dbConnection;
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

    public User authorizeUser(User user) {
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        User userCheck = dbConnection.authorizeUser(user);
        return userCheck;
    }

    public boolean deleteToken(int id) {
       int result = dbConnection.deleteToken(id);

       if(result>0){
           return true;
       }
       return false;
    }

    public boolean checkTokenOwner(String token) {
        String serverToken = dbConnection.tokenExists(token);
        if(serverToken.equals(token)){
            return true;
        }
        return false;
    }


}