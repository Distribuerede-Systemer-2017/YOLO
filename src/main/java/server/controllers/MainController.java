package server.controllers;

import server.models.User;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {

    private User currentUser;

    public MainController(User currentUser) {
        this.currentUser = currentUser;
    }


    public void Login(String username, String password) {

        //kald DBConnection metode (find objekt ud fra username og password)

        //tjek om objekt = is personel eller ej

        if (currentUser.isPersonel()) {

        } else {

        }

    }

}

