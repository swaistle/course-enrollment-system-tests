package ces.tests.instructor;

import io.restassured.response.Response;

import ces.utils.BearerTokenGenerator;
import org.junit.jupiter.api.Test;


public class InstructorAuthenticationTests {

    private final Response response = BearerTokenGenerator
            .generateBearerToken("instructor");
    @Test
    void assertLoginStatus() {
        response.then()
                .assertThat()
                .statusCode(200);
    }

}
