package server.api;

import com.google.gson.Gson;
import server.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserEndpoint {

    @GET
    public Response getUsers(){
        return Response
                .status(200)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String jsonUser) {

        User user = new Gson().fromJson(jsonUser, User.class);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

}
