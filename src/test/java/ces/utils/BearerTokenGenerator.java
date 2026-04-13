package ces.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static ces.utils.Helper.*;

public class BearerTokenGenerator {

    static final Logger log = LoggerFactory.getLogger(BearerTokenGenerator.class);
    final RequestSpecification request = RestAssured.given();

    public Response generateBearerToken(String role) {
        //Environment variables
        final String appUrl = HOST + "/" + role + "/login";
        final String candidateId = CANDIDATE_ID;

        final String username = role + "_" + candidateId + candidateId;

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", PASSWORD);

        log.debug("Generating Bearer Token for: {}", role);

        return request.body(requestBody)
                .accept("application/json")
                .contentType("application/json")
                .when()
                .post(appUrl);
    }

    public String extractBearerToken(String role) {
        return generateBearerToken(role).then().extract().path("token");
    }
}
