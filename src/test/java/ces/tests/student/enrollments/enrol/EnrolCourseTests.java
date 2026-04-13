package ces.tests.student.enrollments.enrol;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.enrollments.EnrolCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.CANDIDATE_ID;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class EnrolCourseTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();

    String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;

    String actualCourseId;
    String actualCourseCode;

    @BeforeEach
    void setUp() {
        Response response = addCourseRequest.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractCourseId(response);
        actualCourseCode = baseSetUp.extractCourseCode(response);
    }

    @AfterEach
    void tearDown() {
        deleteCourseRequest.cleanUp(actualCourseId);
    }

    @Test
    void assertEnrolStatus() {
        Response response = enrolCourseRequest.enrolCourse(studentId, actualCourseCode);

        response.then()
                .assertThat()
                .statusCode(201);

    }

    @Test
    void assertEnrolSchema() {
        Response response = enrolCourseRequest.enrolCourse(studentId, actualCourseCode);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/EnrolCourseSchema.json"));
    }

}
