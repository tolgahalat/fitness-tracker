package tolga.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tolga.entity.Exercise;

import java.util.List;

@Path("/exercises")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExerciseResource {

    @GET
    public Response getAllExercises() {
        List<Exercise> exercises = Exercise.listAll();
        return Response.ok(exercises).build();
    }

    @GET
    @Path("/{id}")
    public Response getExerciseById(@PathParam("id") Long id) {
        Exercise exercise = Exercise.findById(id);
        if (exercise == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(exercise).build();
    }

    @POST
    public Response createExercise(Exercise exercise) {
        exercise.persist();
        return Response.status(Response.Status.CREATED).entity(exercise).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateExercise(@PathParam("id") Long id, Exercise updatedExercise) {
        Exercise exercise = Exercise.findById(id);
        if (exercise == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        exercise.name = updatedExercise.name;
        exercise.description = updatedExercise.description;
        exercise.muscleGroup = updatedExercise.muscleGroup;
        exercise.persist();
        return Response.ok(exercise).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteExercise(@PathParam("id") Long id) {
        boolean deleted = Exercise.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

//    @GET
//    public List<Exercise> getAllExercises() {
//        return Exercise.listAll();
//    }
//
//    @POST
//    public Exercise createExercise(Exercise exercise) {
//        exercise.persist();
//        return exercise;
//    }
}

