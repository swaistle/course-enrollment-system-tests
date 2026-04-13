package ces.tests.student.enrolments.enrol;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.enrolments.EnrolCourseRequest;
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

    String actualCourseId;
    String actualCourseCode;

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
    void assertEnrolStatus() {
        String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = enrolCourseRequest.enrolCourse(studentId, actualCourseCode);

        response.then()
                .assertThat()
                .statusCode(201);

    }

    @Test
    void assertEnrolSchema() {
        String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = enrolCourseRequest.enrolCourse(studentId, actualCourseCode);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/EnrolCourseSchema.json"));
    }

}
