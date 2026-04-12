package ces.tests.instructor.courses.update;

import ces.utils.AddCourseRequest;
import ces.utils.BaseSetUp;
import ces.utils.DeleteCourseRequest;
import ces.utils.UpdateCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.BAD_REQUEST_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateCourseValidationTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    UpdateCourseRequest updateCourseRequest = new UpdateCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();

    String actualCourseId;

    @AfterEach
    void tearDown() {
        deleteCourseRequest.cleanUp(actualCourseId);
    }

    @Test
    void assertBadRequest(){
        Response addCourseResponse = addCourseRequest.createCourse();
        addCourseResponse.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractCourseId(addCourseResponse);


        Response updateCourseResponse = updateCourseRequest.updateCourseRequest(actualCourseId, "~");

        String htmlResponse = updateCourseResponse.then()
                .assertThat()
                .statusCode(400)
                .extract()
                        .body().asPrettyString();

        String extractedMessage = htmlResponse.substring(
                htmlResponse.indexOf("<pre>") + 5, htmlResponse.indexOf("</pre>"));

        assertEquals(BAD_REQUEST_MESSAGE, extractedMessage);
    }

}
