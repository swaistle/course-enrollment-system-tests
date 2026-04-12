package ces.utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static ces.utils.Helper.CANDIDATE_ID;

public class BaseSetUp {

    final String role = "instructor";
    private final Logger log = LoggerFactory.getLogger(BaseSetUp.class);

    String courseId;
    String courseCode;
    JSONObject payload;

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    public String generateCourseCode(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomInt = random.nextInt(1,999);
        String generatedCode;
        generatedCode = "TST"+ CANDIDATE_ID + "_" + randomInt;
        return generatedCode;
    }

    public String generateDate(int months){
        LocalDate localDate = LocalDate.now();

        if (months != 0) {
            return localDate.plusMonths(months).toString();
        }
        else {
            return localDate.toString();
        }
    }

    public Response createCourse(){
        RequestSpecification request = RestAssured.given();

        final String appUrl = Helper.HOST + "/courses";

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        payload = generateDefaultPayload();

        log.debug("Creating default course tests data");

        return request.body(payload.toString())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);
    }

    public Response createCourse(JSONObject newPayload){
        RequestSpecification request = RestAssured.given();

        final String appUrl = Helper.HOST + "/courses";

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        payload = newPayload;
        setCourseCode(payload);

        return request.body(payload.toString())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);
    }

    public String extractCourseId(Response response) {
        JsonPath jsonPath = response.jsonPath();
        courseId = jsonPath.getString("newCourse._id");
        log.debug("actualCourse id: {}", courseId);

        return courseId;
    }

    public void deleteCourse(String courseId){
        RequestSpecification request = RestAssured.given();

        final String appUrl = Helper.HOST + "/courses/" + courseId;

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        log.debug("Deleting course test data with courseId: {}", courseId);

        Response response = request.body(payload.toString())
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete(appUrl);

        String actualCourseCode;
        actualCourseCode = response.then().extract().path("courseCode");

        log.debug("Successfully deleted course test data with courseCode: {}", actualCourseCode);
    }

    private JSONObject generateDefaultPayload(){
        String instructorId = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
        String newTitle = instructorId + "'s course";

        JSONObject generatedPayload = new AddCoursePayloadBuilder()
                .withTitle(newTitle)
                .withInstructor(instructorId)
                .withCourseCode(generateCourseCode())
                .withStartDate(generateDate(0))
                .withEndDate(generateDate(3))
                .build();

        setCourseCode(generatedPayload);

        return generatedPayload;
    }

    private void setCourseCode(JSONObject generatedPayload){
        courseCode = generatedPayload.get("courseCode").toString();
        log.debug("Setting up course test data with courseCode: {}", courseCode);
    }

}
