package server.endpoints;

import com.google.gson.Gson;
import server.authentication.AuthEndpoint;
import server.controllers.MainController;
import server.database.DBConnection;
import server.models.User;

import javax.ws.rs.Path;

@Path("/start")
public class RootEndpoint {
    AuthEndpoint auth = new AuthEndpoint();
    MainController mc = new MainController();

    @Path("/login")
    public void login(String userAsJson){

        User user = new Gson().fromJson(userAsJson, User.class);
        //Logikken der tjekker, hvorvidt en bruger findes eller ej
        try {

            User loginUser = mc.authorizeUser(user);

            if (loginUser == null) {
                //Findes ikke
            } else if (loginUser.isPersonel()) {
                //     staffController.loadUser(currentUser);
                auth.AuthUser(new Gson().toJson(loginUser, User.class));
            } else if (!loginUser.isPersonel()){
                //    userController.loadUser(currentUser);
                auth.AuthUser(new Gson().toJson(loginUser, User.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Path("/logout")
    public void logout(String userId){
        int id = new Gson().fromJson(userId, Integer.class);
        mc.deleteToken(id);
    }
}
