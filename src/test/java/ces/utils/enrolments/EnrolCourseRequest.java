package ces.utils.enrolments;

import ces.utils.BearerTokenGenerator;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ces.utils.Helper.*;

public class EnrolCourseRequest {

    private final Logger log = LoggerFactory.getLogger(EnrolCourseRequest.class);

    final String role = "student";
    JSONObject payload;
    final String appPath = HOST + ENROLMENTS_CONTEXT_PATH;

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    public Response enrolCourse(String studentId, String courseCode){
        RequestSpecification request = RestAssured.given();
        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        String appUrl = appPath + ENROLMENTS_ENROL_CONTEXT_PATH;

        payload = new JSONObject();
        payload.put("username", studentId);
        payload.put("courseCode", courseCode);

        log.debug("Enrolling student {} on course {}", studentId, courseCode);

        return request.body(payload.toString())
                .contentType("application/json")
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);
    }

    public Response dropCourse(String studentId, String courseCode){
        RequestSpecification request = RestAssured.given();
        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        String appUrl = appPath + ENROLMENTS_DROP_CONTEXT_PATH;

        payload = new JSONObject();
        payload.put("username", studentId);
        payload.put("courseCode", courseCode);

        log.debug("Dropping student {} from course {}", studentId, courseCode);

        return request.body(payload.toString())
                .contentType("application/json")
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);
    }

}
