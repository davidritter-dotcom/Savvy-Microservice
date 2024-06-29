package ch.ffhs;

import ch.ffhs.model.DiaryEntry;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExternalServiceClientTest {

    @Inject
    @RestClient
    ExternalServiceClient externalServiceClient;

    private static int entryId;

    //a low int that does not get close to the actual google user id.
    private static final String userId = "1";

    @Test
    @Order(1)
    public void testCreateEntry() {
        DiaryEntry entry = new DiaryEntry("Test Title", "Test Content", userId);
        Response response = externalServiceClient.createData(entry);

        assertEquals(201, response.getStatus());
        entryId = response.readEntity(DiaryEntry.class).getEntryId();
    }

    @Test
    @Order(2)
    public void testUpdateEntry() {
        DiaryEntry updatedEntry = new DiaryEntry("Updated Title", "Updated Content", userId);
        Response response = externalServiceClient.updateData(entryId, updatedEntry);

        // Validate response contains the updated fields
        assertTrue(response.readEntity(DiaryEntry.class).getTitle().contains("Updated Title"));
        assertTrue(response.readEntity(DiaryEntry.class).getContent().contains("Updated Content"));
    }

    @Test
    @Order(3)
    public void testGetDataOfUser() {
        // Abrufen der Daten vom externen Service
        Response response = externalServiceClient.getDataOfUser(userId);
        assertTrue(response.getLength() > 2);
    }

    @Test
    @Order(4)
    public void testDeleteEntry() {
        Response response = externalServiceClient.deleteData(entryId);
        assertEquals(204, response.getStatus());
    }
}
