package ces.tests.instructor.courses.delete;

import ces.utils.BearerTokenGenerator;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteCourseValidationTests {

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    final String accessToken = bearerTokenGenerator.extractBearerToken("instructor");

    @Test
    void assertErrorDeletingCourse() {
        String appUrl = HOST + COURSE_CONTEXT_PATH + "/test";

        RequestSpecification request = RestAssured.given();

        Response response = request
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("error");

        assertEquals(DELETE_COURSE_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertNoAuthToken() {
        String appUrl = HOST + COURSE_CONTEXT_PATH + "/000000000000000000000000";

        RequestSpecification request = RestAssured.given();

        Response response = request
                .accept("*/*")
                .when()
                .delete(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");

        assertEquals(NO_AUTH_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertNotFound() {
        String appUrl = HOST + COURSE_CONTEXT_PATH + "/000000000000000000000000";

        RequestSpecification request = RestAssured.given();

        Response response = request
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("error");

        assertEquals(NOT_FOUND_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertCannotDeleteDifferentInstructorCourse() {
        String appUrl = HOST + COURSE_CONTEXT_PATH + "/69db938b17414acde244b9fc";

        RequestSpecification request = RestAssured.given();

        Response response = request
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(403)
                .extract()
                .path("error");

        assertEquals(DELETE_FORBIDDEN_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertStudentCannotDeleteCourse() {
        String appUrl = HOST + COURSE_CONTEXT_PATH + "/69db938b17414acde244b9fc";

        String studentAccessToken = bearerTokenGenerator.extractBearerToken("student");

        RequestSpecification request = RestAssured.given();

        Response response = request
                .accept("*/*")
                .header("Authorization", "Bearer " + studentAccessToken)
                .when()
                .delete(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(403)
                .extract()
                .path("message");

        assertEquals(ACCESS_DENIED_INSTRUCTORS_ONLY, responseMessage);
    }

}
