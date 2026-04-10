package ces.tests.student;

import ces.utils.BearerTokenGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class StudentAuthenticationTests {

    private static final Logger LOG = LoggerFactory.getLogger(StudentAuthenticationTests.class);
    private final Response response = BearerTokenGenerator
            .generateBearerToken("student");

    @Test
    void assertLoginStatus() {
        LOG.debug("Executing assertLoginStatus test");
        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertLoginSchema() {
        LOG.debug("Executing assertLoginSchema test");
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/AuthenticationSchema.json"));
    }

}
