package ces.tests.student.enrollments.drop;

import ces.utils.BaseSetUp;
import ces.utils.BearerTokenGenerator;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.enrollments.EnrolCourseRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.*;
import static ces.utils.Helper.ACCESS_DENIED_STUDENTS_ONLY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DropCourseValidationTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();

    final String appUrl = HOST + ENROLLMENTS_CONTEXT_PATH + ENROLLMENTS_DROP_CONTEXT_PATH;

    String actualCourseId;
    String actualCourseCode;

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
    void assertInstructorCannotDropCourse() {
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
    void assertNoActiveEnrollment() {
        String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = enrolCourseRequest.dropCourse(studentId, "JAVA101");

        String responseMessage = response.then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("error");

        assertEquals(NO_ACTIVE_ENROLLMENT_ERROR_MESSAGE, responseMessage);

    }

    @Nested
    class NestedDropValidationTests {
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
        void assertUserDoesNotExist() {
            String studentId = "student_notreal";
            Response response = enrolCourseRequest.dropCourse(studentId, actualCourseCode);

            String responseMessage = response.then()
                    .assertThat()
                    .statusCode(404)
                    .extract()
                    .path("error");

            assertEquals(USER_NOT_FOUND_ERROR_MESSAGE, responseMessage);
        }

    }
}
