package tolga.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tolga.entity.User;

import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/me")
    @RolesAllowed("admin")
    public Response me() {
        String name = jwt.getClaim("name");  // login'de eklediğin custom claim
        String sub  = jwt.getSubject();      // "sub"
        return Response.ok(Map.of("userId", sub, "name", name)).build();
    }

    @GET
    @Path("/mee")
    @RolesAllowed("admin")
    public Response me2() {
        String email = jwt.getName();        // preferred_username -> upn -> sub
        String userId = jwt.getSubject();    // "sub"
        String name   = jwt.getClaim("name");
        String upn    = jwt.getClaim("upn"); // mail adresini dönüyor yine.
        return Response.ok(Map.of(
                "userId", userId, "email", email, "name", name, "upn", upn
        )).build();
    }

    @GET
    public Response getAllUsers() {
        List<User> users = User.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        User user = User.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @POST
    @Transactional
    public Response createUser(User user) {
        user.persist();
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, User updatedUser) {
        User user = User.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.name = updatedUser.name;
        user.email = updatedUser.email;
        user.password = updatedUser.password;
        user.persist();
        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        boolean deleted = User.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
