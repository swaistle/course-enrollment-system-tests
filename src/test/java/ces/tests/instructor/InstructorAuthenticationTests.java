package ces.tests.instructor;

import io.restassured.response.Response;

import ces.utils.BearerTokenGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


class InstructorAuthenticationTests {

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    private static final Logger log = LoggerFactory.getLogger(InstructorAuthenticationTests.class);
    private final Response response = bearerTokenGenerator
            .generateBearerToken("instructor");

    @Test
    void assertLoginStatus() {
        log.debug("Executing assertLoginStatus test");

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertLoginSchema() {
        log.debug("Executing assertLoginSchema test");
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/AuthenticationSchema.json"));
    }

}
