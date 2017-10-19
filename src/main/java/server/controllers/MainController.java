package server.controllers;

import server.database.DBConnection;
import server.endpoints.RootEndpoint;
import server.endpoints.StaffEndpoint;
import server.endpoints.UserEndpoint;
import server.models.User;
import server.utility.Globals;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {

    private User currentUser;
    DBConnection dbConnection;
    private StaffController staffController;
    private UserController userController;
    private UserEndpoint userEndpoint;
    private StaffEndpoint staffEndpoint;
    private RootEndpoint rootEndpoint;

    public MainController() {
        currentUser = null;
        dbConnection = new DBConnection();
        staffController = new StaffController(dbConnection);
        userController = new UserController(dbConnection);
        userEndpoint = new UserEndpoint();
        staffEndpoint = new StaffEndpoint();
        rootEndpoint = new RootEndpoint();

    }

    /**
     * Use authUser syntax from MainController instead???
     * @param username
     * @param password
     */
    public void login(String username, String password) {

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        //Logikken der tjekker, hvorvidt en bruger findes eller ej

        try {
               currentUser = dbConnection.authorizeUser(user);
            if (currentUser == null) {
                //User doesn't exist or username/password is wrong
                Globals.log.writeLog(getClass().getName(), this, "Log-in attempt by" + username + " failed", 0);
            } else if (!currentUser.isPersonel()) {
                //Log-in as user
                Globals.log.writeLog(getClass().getName(), this, "Log-in attempt by" + username + " (user) successful", 0);
                userController.setCurrentUser(user);
            } else {
                //Log-in as staff
                Globals.log.writeLog(getClass().getName(), this, "Log-in attempt by" + username + " (staff) successful", 0);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

