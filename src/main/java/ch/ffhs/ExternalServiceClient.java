package ch.ffhs;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("")
@RegisterRestClient(baseUri = "http://localhost:8084")
public interface ExternalServiceClient {

    @GET
    @Path("/diary/17")
    @Produces(MediaType.APPLICATION_JSON)
    String getData();
}
