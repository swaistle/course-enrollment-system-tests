package ces.tests.student.enrolments.enrol;

import ces.utils.BaseSetUp;
import ces.utils.BearerTokenGenerator;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.courses.SearchCourseRequest;
import ces.utils.enrolments.EnrolCourseRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnrolCourseValidationTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    SearchCourseRequest searchCourseRequest = new SearchCourseRequest();
    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();

    final String appUrl = HOST + ENROLMENTS_CONTEXT_PATH + ENROLMENTS_ENROL_CONTEXT_PATH;

    String actualCourseId;
    String actualCourseCode;

    @Test
    void assertCourseFull() {
        String courseCode = "TESTB4B5D0";
        Response dataSetup = searchCourseRequest.searchByAvailability(courseCode);
        String actualAvailability = dataSetup.then()
                .assertThat()
                .extract()
                .path("availableSlots");

        assertEquals(COURSE_FULL_MESSAGE, actualAvailability);

        String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = enrolCourseRequest.enrolCourse(studentId, courseCode);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("error");

        assertEquals(COURSE_FULL_ERROR_MESSAGE, responseMessage);

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
    void assertInstructorCannotEnrolCourse() {
        RequestSpecification request = RestAssured.given();

        final String studentAccessToken = bearerTokenGenerator.extractBearerToken("instructor");

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

        assertEquals(ACCESS_DENIED_STUDENTS_ONLY, responseMessage);
    }

    @Test
    void assertCourseDoesNotExist() {
        String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = enrolCourseRequest.enrolCourse(studentId, "notReal");

        String responseMessage = response.then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("error");

        assertEquals(NOT_FOUND_ERROR_MESSAGE, responseMessage);
    }


    @Nested
    class NestedEnrolValidationTests {
        @BeforeEach
        void setUp() {
            Response response = addCourseRequest.createCourse();

            response.then()
                    .assertThat()
                    .statusCode(201);

            actualCourseId = baseSetUp.extractActualCourseId(response);
            actualCourseCode = baseSetUp.extractActualCourseCode(response);
        }

        @AfterEach
        void tearDown() {
            deleteCourseRequest.cleanUp(actualCourseId);
        }

        @Test
        void assertAlreadyEnrolled() {
            String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
            Response initialEnrollResponse = enrolCourseRequest.enrolCourse(studentId, actualCourseCode);

            initialEnrollResponse.then()
                    .assertThat()
                    .statusCode(201);


            Response tryEnrollAgainResponse = enrolCourseRequest.enrolCourse(studentId, actualCourseCode);

            String responseMessage = tryEnrollAgainResponse.then()
                    .assertThat()
                    .statusCode(400)
                    .extract()
                    .path("error");

            assertEquals(COURSE_ALREADY_ENROLLED_ERROR_MESSAGE, responseMessage);
        }

        @Test
        void assertUserDoesNotExist() {
            String studentId = "student_notreal";
            Response response = enrolCourseRequest.enrolCourse(studentId, actualCourseCode);

            String responseMessage = response.then()
                    .assertThat()
                    .statusCode(404)
                    .extract()
                    .path("error");

            assertEquals(USER_NOT_FOUND_ERROR_MESSAGE, responseMessage);
        }

    }

}
