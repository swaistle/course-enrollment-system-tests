package ces.tests.student;

import ces.utils.BearerTokenGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class StudentAuthenticationTests {

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    private final Response response = bearerTokenGenerator
            .generateBearerToken("student");

    @Test
    void assertLoginStatus() {
        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertLoginSchema() {
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/AuthenticationSchema.json"));
    }

}
