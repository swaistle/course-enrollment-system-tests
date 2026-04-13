package ces.tests.student.enrollments.drop;

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

class DropCourseTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();

    String testDataCourseId;
    String testDataCourseCode;

    String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;

    @BeforeEach
    void setUp() {

        Response response = addCourseRequest.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        testDataCourseId = baseSetUp.extractCourseId(response);
        testDataCourseCode = baseSetUp.extractCourseCode(response);

        Response initialEnrollResponse = enrolCourseRequest.enrolCourse(studentId, testDataCourseCode);

        initialEnrollResponse.then()
                .assertThat()
                .statusCode(201);

    }

    @AfterEach
    void tearDown() {
        deleteCourseRequest.cleanUp(testDataCourseId);
    }

    @Test
    void assertDropCourseStatus() {
        Response response = enrolCourseRequest.dropCourse(studentId, testDataCourseCode);

        response.then()
                .assertThat()
                .statusCode(200);

    }

    @Test
    void assertDropCourseSchema() {
        Response response = enrolCourseRequest.dropCourse(studentId, testDataCourseCode);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/EnrolCourseSchema.json"));

    }

}
