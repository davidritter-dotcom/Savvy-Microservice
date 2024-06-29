package ch.ffhs;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ExternalServiceClientTest {

    @Inject
    @RestClient
    ExternalServiceClient externalServiceClient;

    @Test
    public void testGetData() {
        // Abrufen der Daten vom externen Service
        String data = externalServiceClient.getData();

        // Überprüfen, ob die Daten wie erwartet sind
        assertNotNull(data, "Data should not be null");
        // Weitere Assertions, um den Inhalt der Daten zu überprüfen
        // Beispiel:
        // assertTrue(data.contains("expectedValue"), "Data should contain expectedValue");
    }
}
