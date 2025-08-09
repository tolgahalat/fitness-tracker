package tolga.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tolga.dto.AuthRequest;
import tolga.dto.AuthResponse;
import tolga.entity.User;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @POST
    @Path("/login")
    @Transactional
    public Response login(AuthRequest req) {
        if (req == null || req.email == null || req.password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage("Email and password are required"))
                    .build();
        }

        User user = User.findByEmail(req.email);
        if (user == null || !Objects.equals(req.password, user.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorMessage("Invalid credentials"))
                    .build();
        }

        Duration ttl = Duration.ofHours(8);
        String token = Jwt.subject(String.valueOf(user.id))
                .upn(user.email)
                .issuer("fitness-tracker")
                .groups(Set.of("admin"))         // şimdilik sabit
                .expiresIn(ttl)
                .claim("name", user.name)        // enum yerine string key
                .sign();

        AuthResponse res = new AuthResponse();
        res.token = token;
        res.expiresAt = Instant.now().plus(ttl).getEpochSecond();
        res.name = user.name;
        res.role = "admin";                      // şimdilik sabit
        res.userId = user.id;

        return Response.ok(res).build();
    }

    // Basit hata DTO'su
    public static class ErrorMessage {
        public String error;
        public ErrorMessage(String error) { this.error = error; }
    }
}
