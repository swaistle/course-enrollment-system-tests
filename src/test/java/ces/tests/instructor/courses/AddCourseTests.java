package ces.tests.instructor.courses;

import ces.utils.BaseSetUp;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class AddCourseTests {

    private final Logger log = LoggerFactory.getLogger(AddCourseTests.class);
    BaseSetUp baseSetUp = new BaseSetUp();
    String actualCourseId;


    @AfterEach
    void tearDown() {
        log.debug("Running clear down");
        baseSetUp.deleteCourse(actualCourseId);
    }

    @Test
    void assertAddCourseStatus() {
        Response response = baseSetUp.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractCourseId(response);
    }

        @Test
    void assertAddCourseSchema() {
        Response response = baseSetUp.createCourse();
        response.then()
                .assertThat()
                    .body(matchesJsonSchemaInClasspath("schemas/AddNewCourseSchema.json"));

        actualCourseId = baseSetUp.extractCourseId(response);
    }

}
