package server.controllers;

import server.endpoints.RootEndpoint;
import server.endpoints.StaffEndpoint;
import server.endpoints.UserEndpoint;
import server.models.User;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {

    private User currentUser;
    private StaffController staffController;
    private UserController userController;
    private UserEndpoint userEndpoint;
    private StaffEndpoint staffEndpoint;
    private RootEndpoint rootEndpoint;


    public MainController() {
        currentUser = null;
        staffController = new StaffController();
        userController = new UserController();
        userEndpoint = new UserEndpoint();
        staffEndpoint = new StaffEndpoint();
        rootEndpoint = new RootEndpoint();
        
    }


    public void login(String username, String password) {

        //Klienten rammer userendpoint


        //tjek om objekt = is personel eller ej

        if (currentUser.isPersonel()) {

        } else {

        }

    }

}

