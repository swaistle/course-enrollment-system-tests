package ces.tests.student;

import ces.utils.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static ces.utils.Helper.INVALID_CREDENTIALS_ERROR_MESSAGE;
import static ces.utils.Helper.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentAuthenticationValidationTests {

    @Test
    void assertInvalidCredentials() {
        String appUrl = Helper.HOST + "/student/login";

        RequestSpecification request = RestAssured.given();


        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "username");
        requestBody.put("password", PASSWORD);

        Response response = request.body(requestBody)
                .accept("application/json")
                .contentType("application/json")
                .when()
                .post(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("error");

        assertEquals(INVALID_CREDENTIALS_ERROR_MESSAGE, responseMessage);
    }

}
