package ch.ffhs;

import ch.ffhs.model.DiaryEntry;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("")
@RegisterRestClient(baseUri = "http://localhost:8084/diary")
public interface ExternalServiceClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getData(@PathParam("id") int id);

    @GET
    @Path("/user/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDataOfUser(@PathParam("user_id") String user_id);

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createData(DiaryEntry entry);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateData(@PathParam("id") int id, DiaryEntry entry);

    @DELETE
    @Path("/{id}")
    Response deleteData(@PathParam("id") int id);
}
