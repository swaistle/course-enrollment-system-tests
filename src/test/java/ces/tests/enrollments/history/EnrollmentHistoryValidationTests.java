package ces.tests.enrollments.history;

import ces.utils.enrollments.EnrolCourseRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnrollmentHistoryValidationTests {

    final String appUrl = HOST + ENROLLMENTS_CONTEXT_PATH + ENROLLMENTS_HISTORY_CONTEXT_PATH;
    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();

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
    void assertUserDoesNotExist() {
        String studentId = "student_notreal";
        Response response = enrolCourseRequest.viewHistory("student", studentId);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("error");

        assertEquals(USER_NOT_FOUND_ERROR_MESSAGE, responseMessage);
    }

}
