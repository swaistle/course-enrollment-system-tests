package ces.tests.student.enrolments.enrol;

import ces.utils.courses.SearchCourseRequest;
import ces.utils.enrolments.EnrolCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnrolCourseValidationTests {

    SearchCourseRequest searchCourseRequest = new SearchCourseRequest();
    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();

    @Test
    void assertCourseFull() {
        String courseCode = "TESTB4B5D0";
        Response dataSetup = searchCourseRequest.searchByAvailability(courseCode);
        String actualAvailability = dataSetup.then()
                .assertThat()
                .extract()
                .path("availableSlots");

        assertEquals(COURSE_FULL_MESSAGE, actualAvailability);

        String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = enrolCourseRequest.enrolCourse(studentId, courseCode);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("error");

        assertEquals(COURSE_FULL_ERROR_MESSAGE, responseMessage);


    }

}
