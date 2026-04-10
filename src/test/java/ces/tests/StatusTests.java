package ces.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

class StatusTests {
    private final Logger log = LoggerFactory.getLogger(StatusTests.class);
    private final String appUrl = System.getenv("APP_URL") + "/status";

    private final RequestSpecification request = RestAssured.given();
    private Response response;

    @Test
    void assertStatus() {
        log.debug("Asserting status of url: {}", appUrl);

        response = request.get(appUrl);
        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertStatusBody() {
        log.debug("Asserting body of url: {}", appUrl);

        response = request.get(appUrl);
        response.then()
                .assertThat()
                .body("status", equalTo("Server is running"));
    }

    @Test
    void assertStatusSchema() {
        log.debug("Asserting schema of url: {}", appUrl);

        response = request.get(appUrl);
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/StatusSchema.json"));
    }
}
