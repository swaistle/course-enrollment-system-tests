package ces.tests.instructor.courses.delete;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteCourseTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();

    String actualCourseId;
    String expectedCourseCode;

    @BeforeEach
    void setUp(){
        Response response = addCourseRequest.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractActualCourseId(response);
        expectedCourseCode = baseSetUp.extractActualCourseCode(response);
    }

    @Test
    void assertDeleteStatus(){
        Response response = deleteCourseRequest.deleteCourse(actualCourseId);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertDeleteSchema(){
        Response response = deleteCourseRequest.deleteCourse(actualCourseId);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/DeleteCourseSchema.json"));
    }

    @Test
    void assertCourseCodeResponse(){
        Response response = deleteCourseRequest.deleteCourse(actualCourseId);

        String responseMessage = response.then()
                .assertThat()
                .extract()
                .path("courseCode");

        assertEquals(expectedCourseCode, responseMessage);
    }

}
