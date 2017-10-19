
package server.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import server.controllers.MainController;
import server.database.DBConnection;
import server.models.User;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Tobias on 10-10-2017.
 */

/**
 * This class is the endpoint for authenticating the user
 */
@Path("/auth")
public class AuthEndpoint {
    MainController mcontroller = new MainController();
    DBConnection dbCon = new DBConnection();
    User tokenUser = new User();

    /**
     * Authenticates the user and returns a token if user exists
     * @param jsonUser
     * @return
     */
    @POST
    public Response AuthUser(String jsonUser) {
        User user = new Gson().fromJson(jsonUser, User.class);
        String token = null;

        try {
            tokenUser = mcontroller.authorizeUser(user);
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                long timevalue;
                timevalue = (System.currentTimeMillis()*1000)+20000205238L;
                Date expDate = new Date(timevalue);

                token = JWT.create().withClaim("username", tokenUser.getUsername()).withKeyId(String.valueOf(tokenUser.getUserId()))
                        .withExpiresAt(expDate).withIssuer("YOLO").sign(algorithm);
                dbCon.createToken(tokenUser, token);
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }catch (JWTCreationException e){
                e.printStackTrace();
            }

            return Response.status(200).type("plain/text").entity(token).build();
        } catch (Exception e) {
            return Response.status(401).type("plain/text").entity("User not authorized").build();
        }

    }
}
