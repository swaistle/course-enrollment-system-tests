package ces.tests.e2e;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.courses.SearchCourseRequest;
import ces.utils.enrollments.EnrolCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.CANDIDATE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserBehaviourTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    EnrolCourseRequest enrolCourseRequest = new EnrolCourseRequest();
    SearchCourseRequest searchCourseRequest = new SearchCourseRequest();

    String testDataCourseId;
    String testDataCourseCode;
    int testDataAvailableSlots;

    @BeforeEach
    void setUp() {
        Response response = addCourseRequest.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        testDataCourseId = baseSetUp.extractCourseId(response);
        testDataCourseCode = baseSetUp.extractCourseCode(response);
        testDataAvailableSlots = baseSetUp.extractAvailableSlots(response, "newCourse.availableSlots");
    }

    @AfterEach
    void tearDown() {
        deleteCourseRequest.cleanUp(testDataCourseId);
    }

    @Test
    void assertCourseAvailabilityUpdates() {
        String studentId = "student_" + CANDIDATE_ID + CANDIDATE_ID;
        int originalAvailableSlot = testDataAvailableSlots;

        Response enrolResponse = enrolCourseRequest.enrolCourse(studentId, testDataCourseCode);
        enrolResponse.then()
                .assertThat()
                .statusCode(201);

        Response searchAvailabilityResponse1 = searchCourseRequest.searchByAvailability(testDataCourseCode);
        searchAvailabilityResponse1.then()
                .assertThat()
                .statusCode(200);

        int actualEnrolledAvailableSlots = baseSetUp.extractAvailableSlots(searchAvailabilityResponse1, "availableSlots");

        int expectedInt = originalAvailableSlot - 1;

        assertEquals(expectedInt, actualEnrolledAvailableSlots);

        Response dropCourseResponse = enrolCourseRequest.dropCourse(studentId, testDataCourseCode);
        dropCourseResponse.then()
                .assertThat()
                .statusCode(200);

        Response searchAvailabilityResponse2 = searchCourseRequest.searchByAvailability(testDataCourseCode);
        searchAvailabilityResponse1.then()
                .assertThat()
                .statusCode(200);

        int actualDroppedAvailableSlots = baseSetUp.extractAvailableSlots(searchAvailabilityResponse2, "availableSlots");

        assertEquals(originalAvailableSlot, actualDroppedAvailableSlots);
    }

}
