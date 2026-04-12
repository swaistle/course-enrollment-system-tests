package ces.tests.instructor.courses.add;

import ces.utils.AddCoursePayloadBuilder;
import ces.utils.BaseSetUp;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ces.utils.Helper.CANDIDATE_ID;
import static ces.utils.Helper.DATE_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCourseValidationTests {

    private final Logger log = LoggerFactory.getLogger(AddCourseValidationTests.class);

    BaseSetUp baseSetUp = new BaseSetUp();

    @Test
    void assertStartDateValidation() {
        String instructorId = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
        String newTitle = instructorId + "'s course";

        JSONObject incorrectDateRange = new AddCoursePayloadBuilder()
                .withTitle(newTitle)
                .withInstructor(instructorId)
                .withCourseCode(baseSetUp.generateCourseCode())
                .withStartDate(baseSetUp.generateDate(3))
                .withEndDate(baseSetUp.generateDate(0))
                .build();

        log.debug("Creating course tests data with invalid dates");

        Response response = baseSetUp.createCourse(incorrectDateRange);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("error");

        assertEquals(DATE_ERROR_MESSAGE, responseMessage);
    }

}
