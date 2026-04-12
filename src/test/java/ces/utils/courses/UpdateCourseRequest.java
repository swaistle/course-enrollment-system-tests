package ces.utils.courses;

import ces.utils.BearerTokenGenerator;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ces.utils.Helper.COURSE_CONTEXT_PATH;
import static ces.utils.Helper.HOST;


public class UpdateCourseRequest {

    private final Logger log = LoggerFactory.getLogger(UpdateCourseRequest.class);

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    public Response updateCourseRequest(String actualCourseId, JSONObject payload) {
        RequestSpecification request = RestAssured.given();
        final String accessToken = bearerTokenGenerator.extractBearerToken("instructor");

        String appUrl = HOST + COURSE_CONTEXT_PATH + "/" + actualCourseId;

        log.debug("Updating course for: {}", actualCourseId);

        return request
                .body(payload.toString())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .put(appUrl);
    }

    public Response updateCourseRequest(String actualCourseId, String payload) {
        RequestSpecification request = RestAssured.given();
        final String accessToken = bearerTokenGenerator.extractBearerToken("instructor");

        String appUrl = HOST + COURSE_CONTEXT_PATH + "/" + actualCourseId;

        log.debug("Updating course for: {} with a string", actualCourseId);

        return request
                .body(payload)
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .put(appUrl);
    }
}
