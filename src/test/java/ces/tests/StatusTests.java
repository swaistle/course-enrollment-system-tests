package ces.tests;

import ces.utils.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

class StatusTests {
    private final String appUrl = Helper.HOST + "/status";

    private final RequestSpecification request = RestAssured.given();
    private Response response;

    @Test
    void assertStatus() {
        response = request.get(appUrl);
        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertStatusBody() {
        response = request.get(appUrl);
        response.then()
                .assertThat()
                .body("status", equalTo("Server is running"));
    }

    @Test
    void assertStatusSchema() {
        response = request.get(appUrl);
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/StatusSchema.json"));
    }
}
