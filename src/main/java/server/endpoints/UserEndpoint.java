package server.endpoints;

import com.google.gson.Gson;
import server.database.UserTable;
import server.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserEndpoint {

    UserTable userTable = UserTable.getInstance();

    @GET
    public Response getUsers(){
        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(userTable.getUsers()))
                .build();
    }

    @POST
    public Response createUser(String jsonUser) {

        User user = new Gson().fromJson(jsonUser, User.class);
        UserTable userTable = UserTable.getInstance();
        userTable.addUser(user);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

}
