package server.endpoints;

import com.google.gson.Gson;
import server.database.UserTable;
import server.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/users")
public class UserEndpoint {

    UserTable userTable = UserTable.getInstance();
    ArrayList<User> users = userTable.getUsers();

    @GET
    public Response getUsers(){
        return Response
                .status(200)
                .type("application/json")
                .entity("[]")
                .build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") int id){

        //Lidt hjælpe
        //
        //UserTable userTable = UserTable.getInstance();
        //User foundUser = userTable.findById(id);
        //Husk at returnere som JSON

        return Response
                .status(200)
                .type("application/json")
                .entity("{}")
                .build();
    }

    @POST
    public Response createUser(String jsonUser) {


        return Response
                .status(200)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

}
