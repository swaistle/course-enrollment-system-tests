package ces.tests.enrollments.active;

import ces.utils.enrollments.EnrolCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.CANDIDATE_ID;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class ActiveEnrollmentTests {

    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();
    String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
    String role = "student";

    @Test
    void assertStudentViewActiveCoursesStatus() {
        Response response = enrolCourseRequest.viewActiveCourses(role, studentId);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertInstructorViewActiveCoursesStatus() {
        Response response = enrolCourseRequest.viewActiveCourses("instructor", studentId);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertViewActiveCoursesAllSchema() {
        Response response = enrolCourseRequest.viewActiveCourses(role, studentId);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/EnrollmentHistorySchema.json"));
    }

}
