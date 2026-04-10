package ces.utils;

import ces.tests.StatusTests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BearerTokenGenerator {

    static final RequestSpecification request = RestAssured.given();
    static Response response;

    public static Response generateBearerToken(String role) {
        final Logger LOG = LoggerFactory.getLogger(StatusTests.class);

        //Environment variables
        final String APP_URL = System.getenv("APP_URL") + "/" + role + "/login";
        final String CANDIDATE_ID = System.getenv("CANDIDATE_ID");
        final String PASSWORD = System.getenv("PASSWORD");

        final String username = role + "_" + CANDIDATE_ID + CANDIDATE_ID;

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", PASSWORD);

        LOG.debug("Generating Bearer Token for: {}", role);


        return response = request.body(requestBody)
                .accept("application/json")
                .contentType("application/json")
                .when()
                .post(APP_URL);
    }

    public static String extractBearerToken(String role) {
        return generateBearerToken(role).then().extract().path("token");
    }
}
