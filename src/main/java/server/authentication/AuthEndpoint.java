
        package server.endpoints;

        import com.auth0.jwt.JWT;
        import com.auth0.jwt.algorithms.Algorithm;
        import com.auth0.jwt.exceptions.JWTCreationException;
        import com.google.gson.Gson;
        import server.controllers.UserController;
        import server.models.User;
        import server.utility.Digester;

        import javax.ws.rs.PATCH;
        import javax.ws.rs.POST;
        import javax.ws.rs.Path;
        import javax.ws.rs.core.Response;
        import java.io.DataInput;
        import java.io.UnsupportedEncodingException;
        import java.util.ArrayList;
        import java.util.Date;

/**
 * Created by Tobias on 10-10-2017.
 */

@Path("/auth")
public class AuthEndpoint {
    UserController ucontroller = new UserController();
    Digester dig = new Digester();
    ArrayList<String> tokenArray = new ArrayList<String>();
    User foundUser = new User();

    String checkHashed;

    @POST
    public Response AuthUser(String jsonUser) {
        User authUser = new Gson().fromJson(jsonUser, User.class);
        String token = null;

        try {
            foundUser = ucontroller.authorizeUser(authUser);
        } catch (Exception e) {
            return Response.status(401).type("plain/text").entity("feil # 1").build();
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            long timevalue;
            timevalue = (System.currentTimeMillis()*1000)+20000205238L;
            Date expDate = new Date(timevalue);

            token = JWT.create().withClaim("username",foundUser.getUsername()).withKeyId(String.valueOf(foundUser.getUserId()))
                    .withExpiresAt(expDate).withIssuer("YOLO").sign(algorithm);
            // tokenArray.add(token);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (JWTCreationException e){
            e.printStackTrace();
        }

        return Response.status(200).type("plain/text").entity(token).build();
    }
}
