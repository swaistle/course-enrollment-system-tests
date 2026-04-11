package ces.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static ces.utils.Helper.CANDIDATE_ID;

public class BaseSetUp {

    String courseId;
    String courseCode;
    final String role = "instructor";

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    private final Logger log = LoggerFactory.getLogger(BaseSetUp.class);

    public Map<String, String> courseRequestBody() {
        final String username = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
        courseCode = generateCourseCode();
        log.debug("Creating request body with courseCode: {}", courseCode);

        LocalDate localDate = LocalDate.now();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", username + " course");
        requestBody.put("instructor", username);
        requestBody.put("courseCode", courseCode);
        requestBody.put("category", "Testing");
        requestBody.put("totalCapacity", String.valueOf(30));
        requestBody.put("startDate", localDate.toString());
        requestBody.put("endDate", localDate.plusMonths(3).toString());

        return requestBody;
    }

    public void setTestSetUp(){
        RequestSpecification request = RestAssured.given();

        final String appUrl = Helper.HOST + "/courses";

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        log.debug("Setting up course test data with courseCode: {}", courseCode);

        Response response = request.body(courseRequestBody())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);

        courseId = response.then().extract().path("_id");
        log.debug("Course id created: {}", courseId);
    }

    public void clearDown(String courseId){
        RequestSpecification request = RestAssured.given();

        final String appUrl = Helper.HOST + "/courses/" + courseId;

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        log.debug("Deleting course test data courseId: {}", courseId);

        Response response = request.body(courseRequestBody())
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete(appUrl);

        String actualCourseCode;
        actualCourseCode = response.then().extract().path("courseCode");

        log.debug("Deleted course test data with courseCode: {}", actualCourseCode);
    }

    private String generateCourseCode(){
        int i = 0;
        courseCode = CANDIDATE_ID + "_" + ++i;
        return courseCode;
    }

}
