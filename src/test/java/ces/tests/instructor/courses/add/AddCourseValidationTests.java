package ces.tests.instructor.courses.add;

import ces.utils.*;
import ces.utils.courses.AddCoursePayloadBuilder;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ces.utils.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCourseValidationTests {

    private final Logger log = LoggerFactory.getLogger(AddCourseValidationTests.class);

    BaseSetUp baseSetUp = new BaseSetUp();
    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    final String appUrl = HOST + COURSE_CONTEXT_PATH;
    String instructorId = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
    String newTitle = instructorId + "'s course";

    @Test
    void assertStartDateValidation() {
        JSONObject incorrectDateRange = new AddCoursePayloadBuilder()
                .withTitle(newTitle)
                .withInstructor(instructorId)
                .withCourseCode(baseSetUp.generateCourseCode())
                .withStartDate(baseSetUp.generateDate(3))
                .withEndDate(baseSetUp.generateDate(0))
                .build();

        log.debug("Creating course tests data with invalid dates");

        Response response = addCourseRequest.createCourse(incorrectDateRange);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("error");

        assertEquals(DATE_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertExistingCourseCode(){
        Response courseSetup = addCourseRequest.createCourse();
        String actualCourseId = baseSetUp.extractCourseId(courseSetup);

        JsonPath jsonPath = courseSetup.jsonPath();
        String getExistingCourseCode= jsonPath.getString("newCourse.courseCode");

        JSONObject existingCourseCode = new AddCoursePayloadBuilder()
                .withTitle(newTitle)
                .withInstructor(instructorId)
                .withCourseCode(getExistingCourseCode)
                .withStartDate(baseSetUp.generateDate(0))
                .withEndDate(baseSetUp.generateDate(3))
                .build();

        log.debug("Creating course tests data with existing course code");

        Response response = addCourseRequest.createCourse(existingCourseCode);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("error");

        assertEquals(COURSE_CODE_ERROR_MESSAGE, responseMessage);

        log.debug("Clearing up set up data");
        deleteCourseRequest.cleanUp(actualCourseId);
    }

    @Test
    void assertRequiredFields() {
        JSONObject missingRequiredFields = new JSONObject();
        missingRequiredFields.put("courseCode", "requireFieldTest");

        log.debug("Creating course with missing required fields");

        Response response = addCourseRequest.createCourse(missingRequiredFields);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("error");

        assertEquals(COURSE_REQUIRED_FIELDS_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertNoAuthToken() {
        RequestSpecification request = RestAssured.given();

        Response response = request
                .accept("*/*")
                .contentType("application/json")
                .when()
                .post(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");

        assertEquals(NO_AUTH_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertFailedAuthToken() {
        RequestSpecification request = RestAssured.given();

        Response response = request
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer 123")
                .when()
                .post(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");

        assertEquals(FAILED_AUTH_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertStudentCannotAddCourse() {
        RequestSpecification request = RestAssured.given();

        final String studentAccessToken = bearerTokenGenerator.extractBearerToken("student");


        Response response = request
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + studentAccessToken)
                .when()
                .post(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");

        assertEquals(ACCESS_DENIED_INSTRUCTORS_ONLY, responseMessage);
    }

}
