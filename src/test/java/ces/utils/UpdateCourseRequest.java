package ces.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateCourseRequest {

    private final Logger log = LoggerFactory.getLogger(UpdateCourseRequest.class);

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    public Response updateCourseRequest(String actualCourseId, JSONObject payload) {
        RequestSpecification request = RestAssured.given();
        final String accessToken = bearerTokenGenerator.extractBearerToken("instructor");

        String appUrl = Helper.HOST + "/courses/" + actualCourseId;

        log.debug("Updating course for: {}", actualCourseId);

        return request
                .body(payload.toString())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .put(appUrl);
    }
}
