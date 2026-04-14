package ces.utils.courses;

import ces.utils.BaseSetUp;
import ces.utils.BearerTokenGenerator;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ces.utils.Helper.COURSE_CONTEXT_PATH;
import static ces.utils.Helper.HOST;


public class AddCourseRequest {

    final String role = "instructor";
    final String appUrl = HOST + COURSE_CONTEXT_PATH;
    private final Logger log = LoggerFactory.getLogger(AddCourseRequest.class);
    JSONObject payload;
    BaseSetUp baseSetUp = new BaseSetUp();
    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    public Response createCourse() {
        RequestSpecification request = RestAssured.given();

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        payload = baseSetUp.generateDefaultPayload();

        log.debug("Creating default course test data");

        return request.body(payload.toString())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);
    }

    public Response createCourse(JSONObject newPayload) {
        RequestSpecification request = RestAssured.given();

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        payload = newPayload;
        baseSetUp.setCourseCode(payload);

        return request.body(payload.toString())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);
    }

}
