package ces.tests.instructor.courses.update;

import ces.utils.courses.AddCourseRequest;
import ces.utils.BaseSetUp;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.courses.UpdateCourseRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateCourseValidationTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    UpdateCourseRequest updateCourseRequest = new UpdateCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();

    String actualCourseId;

    @BeforeEach
    void setUp(){
        Response response = addCourseRequest.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractCourseId(response);
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

        assertEquals(BAD_REQUEST_MESSAGE, extractedMessage);
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

}
