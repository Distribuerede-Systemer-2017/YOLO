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

    /**
     *
     * @param userAsJson
     * @return Response with entity as a userAsJson that includes a token to be used for authorization
     * Gives user access to endpoint methods through assigning them a token.
     */
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
            loginUser.setToken(auth.AuthUser(userAsJson));
            String jsonUser = new Gson().toJson(loginUser, User.class);
            if (loginUser == null) {
                return Response
                        .status(401)
                        .type("application/json")
                        //encrypt response to clien before sending
                        .entity(encryption.encryptXOR("{\"Login result\":\"false\"}"))
                        .build();
            } else {
                //return encrypted object in Json format
                return Response
                        .status(200)
                        .type("application/json")
                        .entity(encryption.encryptXOR(jsonUser))
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response
                .status(401)
                .type("application/json")
                //encrypt response to clien before sending
                .entity(encryption.encryptXOR("{\"Authorize result\":\"false\"}"))
                .build();
    }

    /**
     *
     * @param userAsJson
     * @return Plain text based on whether or not logout was successful.
     * Logout for users, deletes token in database (as well as all previous ones if they forgot to logout in an earlier visit).
     */
    @Secured
    @POST
    @Path("/logout")
    public Response logout(String userAsJson) {
        User userFromJson = new Gson().fromJson(userAsJson, User.class);
        boolean deleted = auth.getMcontroller().deleteToken(userFromJson.getUserId());
        if (deleted){
            return Response
                    .status(200)
                    .type("application/json")
                    //encrypt response to clien before sending
                    .entity(encryption.encryptXOR("{\"Attempt to log out\":\"" + deleted + "\"}"))
                    .build();
        }
        else{
            return Response
                    .status(500)
                    .type("application/json")
                    //encrypt response to clien before sending
                    .entity(encryption.encryptXOR("{\"Attempt to log out\":\"" + deleted + "\"}"))
                    .build();
        }
    }
}
