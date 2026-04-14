package ces.tests.instructor.courses.add;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class AddCourseTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    String actualCourseId;

    @AfterEach
    void tearDown() {
        deleteCourseRequest.cleanUp(actualCourseId);
    }

    @Test
    void assertAddCourseStatus() {
        Response response = addCourseRequest.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractCourseId(response);
    }

    @Test
    void assertAddCourseSchema() {
        Response response = addCourseRequest.createCourse();
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/AddNewCourseSchema.json"));

        actualCourseId = baseSetUp.extractCourseId(response);
    }

}
