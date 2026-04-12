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
    void assertErrorDeletingCourse(){
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

}
