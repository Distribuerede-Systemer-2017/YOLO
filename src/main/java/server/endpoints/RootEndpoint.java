package server.endpoints;

import com.google.gson.Gson;
import org.glassfish.jersey.server.spi.ResponseErrorMapper;
import server.authentication.AuthEndpoint;
import server.controllers.MainController;
import server.database.DBConnection;
import server.models.User;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/start")
public class RootEndpoint {
    AuthEndpoint auth = new AuthEndpoint();
    MainController mc = new MainController();


    @POST
    @Path("/login")
    public Response login(String userAsJson){
        User user = new Gson().fromJson(userAsJson, User.class);
        //Logikken der tjekker, hvorvidt en bruger findes eller ej
        try {

            User loginUser = mc.authorizeUser(user);
            String loginUserAsJson = new Gson().toJson(loginUser, User.class);
            if (loginUser == null) {
                //Findes ikke
            } else if (loginUser.isPersonel()) {
                auth.AuthUser(userAsJson);
                return Response
                        .status(200)
                        .type("application/json")
                        .entity(loginUserAsJson)
                        .build();
            } else if (!loginUser.isPersonel()){
                auth.AuthUser(userAsJson);
                return Response
                        .status(200)
                        .type("application/json")
                        .entity(loginUserAsJson)
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(401).type("plain/text").entity("User not authorized").build();
    }

    @POST
    @Path("/logout")
    public void logout(String userId){
        int id = new Gson().fromJson(userId, Integer.class);
        mc.deleteToken(id);
    }
}
