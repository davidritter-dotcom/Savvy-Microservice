package ch.ffhs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class JakartaServiceTest {

    @BeforeAll
    public static void setup() {
        // Setze die Basis-URL der Jakarta EE-Anwendung
        RestAssured.baseURI = "http://localhost:8084";
    }

    @Test
    public void testGetEndpoint() {
        // Teste den GET-Endpoint der Jakarta EE-Anwendung
        given()
                .when()
                .get("/diary/17")
                .then()
                .statusCode(200)
                .body("title", equalTo("19.06.2024"));
    }
}
