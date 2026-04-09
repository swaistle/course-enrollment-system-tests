package ces.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class StatusTests {
    private static final Logger LOG = LoggerFactory.getLogger(StatusTests.class);
    private static final String APP_URL = System.getenv("APP_URL") + "/status";

    private final RequestSpecification request = RestAssured.given();
    private Response response;

    @Test
    void assertStatus() {
        LOG.debug("Asserting status of url: {}", APP_URL);

        response = request.get(APP_URL);
        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertStatusBody() {
        LOG.debug("Asserting body of url: {}", APP_URL);

        response = request.get(APP_URL);
        response.then()
                .assertThat()
                .body("status", equalTo("Server is running"));
    }

    @Test
    void assertStatusSchema() {
        LOG.debug("Asserting schema of url: {}", APP_URL);

        response = request.get(APP_URL);
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/StatusSchema.json"));
    }
}
