package ces.tests.enrollments.history;

import ces.utils.enrollments.EnrolCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.CANDIDATE_ID;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class EnrollmentHistoryTests {

    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();
    String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
    String role = "student";

    @Test
    void assertStudentViewHistoryStatus() {
        Response response = enrolCourseRequest.viewHistory(role, studentId);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertInstructorViewHistoryStatus() {
        Response response = enrolCourseRequest.viewHistory("instructor", studentId);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertViewHistorySchema() {
        Response response = enrolCourseRequest.viewHistory(role, studentId);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/EnrollmentHistorySchema.json"));
    }

}
