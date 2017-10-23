package server.endpoints;

import com.google.gson.Gson;
import server.authentication.AuthEndpoint;
import server.authentication.Secured;
import server.models.User;
import server.utility.Encryption;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/start")
public class RootEndpoint {
    private AuthEndpoint auth = new AuthEndpoint();
    private Encryption encryption = new Encryption();


    @POST
    @Path("/login")
    public Response login(String userAsJson) {
        //if encryption is true in config file
        //decrypt userAsJson from a Json object containing a encrypted Json object to contain a decrypted Json object
        userAsJson = encryption.decryptXOR(userAsJson);
        // parse json object
        User user = new Gson().fromJson(userAsJson, User.class);
        //Logikken der tjekker, hvorvidt en bruger findes eller ej
        try {
            User loginUser = auth.getMcontroller().authorizeUser(user);
            loginUser.setToken(auth.AuthUser(userAsJson).getEntity().toString());
            String jsonUser = new Gson().toJson(loginUser, User.class);
            if (loginUser == null) {
                return Response.status(401).type("plain/text").entity("User not authorized").build();
            } else {
                //return encrypted object in Json format
                return Response.status(200).type("application/json").entity(encryption.encryptXOR(jsonUser)).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(401).type("plain/text").entity("Bruger ikke godkendt").build();
    }

    @Secured
    @POST
    @Path("/logout")
    public Response logout(String userAsJson) {
        User userFromJson = new Gson().fromJson(userAsJson, User.class);
        boolean deleted = auth.getMcontroller().deleteToken(userFromJson.getUserId());
        if (deleted){
            return Response
                    .status(200)
                    .type("plain/text")
                    .entity("Logged out.")
                    .build();
        }
        else{
            return Response
                    .status(500)
                    .type("plain/text")
                    .entity("Server error, token might not exist.")
                    .build();
        }
    }
}
