package ces.tests.instructor.courses.update;

import ces.utils.BearerTokenGenerator;
import ces.utils.courses.AddCourseRequest;
import ces.utils.BaseSetUp;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.courses.UpdateCourseRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateCourseValidationTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    UpdateCourseRequest updateCourseRequest = new UpdateCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    @Test
    void assetNotFound(){
        String appUrl = HOST + COURSE_CONTEXT_PATH + "/000000000000000000000000";
        final String accessToken = bearerTokenGenerator.extractBearerToken("instructor");

        RequestSpecification request = RestAssured.given();

        Response response = request
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .put(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("error");

        assertEquals(NOT_FOUND_ERROR_MESSAGE, responseMessage);
    }

    @Nested
    class NestedValidationTests {
        String actualCourseId;

        @BeforeEach
        void setUp(){
            Response response = addCourseRequest.createCourse();

            response.then()
                    .assertThat()
                    .statusCode(201);

            actualCourseId = baseSetUp.extractActualCourseId(response);
        }

        @AfterEach
        void tearDown() {
            deleteCourseRequest.cleanUp(actualCourseId);
        }

        @Test
        void assertBadRequest(){
            Response updateCourseResponse = updateCourseRequest.updateCourseRequest(actualCourseId, "~");

            String htmlResponse = updateCourseResponse.then()
                    .assertThat()
                    .statusCode(400)
                    .extract()
                    .body().asPrettyString();

            String extractedMessage = htmlResponse.substring(
                    htmlResponse.indexOf("<pre>") + 5, htmlResponse.indexOf("</pre>"));

            assertEquals(BAD_REQUEST_ERROR_MESSAGE, extractedMessage);
        }

        @Test
        void assertNoAuthToken() {
            String appUrl = HOST + COURSE_CONTEXT_PATH + "/" + actualCourseId;

            RequestSpecification request = RestAssured.given();

            Response response = request
                    .accept("*/*")
                    .contentType("application/json")
                    .when()
                    .put(appUrl);

            String responseMessage = response.then()
                    .assertThat()
                    .statusCode(401)
                    .extract()
                    .path("message");

            assertEquals(NO_AUTH_ERROR_MESSAGE, responseMessage);
        }

        @Test
        void assertFailedAuthToken() {
            String appUrl = HOST + COURSE_CONTEXT_PATH + "/" + actualCourseId;

            RequestSpecification request = RestAssured.given();

            Response response = request
                    .accept("*/*")
                    .contentType("application/json")
                    .header("Authorization", "Bearer 123")
                    .when()
                    .put(appUrl);

            String responseMessage = response.then()
                    .assertThat()
                    .statusCode(403)
                    .extract()
                    .path("message");

            assertEquals(FAILED_AUTH_ERROR_MESSAGE, responseMessage);
        }
    }

}
