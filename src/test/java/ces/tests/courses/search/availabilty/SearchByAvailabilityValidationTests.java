package ces.tests.courses.search.availabilty;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.courses.SearchCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.NOT_FOUND_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchByAvailabilityValidationTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    SearchCourseRequest searchCourseRequest = new SearchCourseRequest();

    String actualCourseId;

    @BeforeEach
    void setUp(){
        Response response = addCourseRequest.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractActualCourseId(response);
    }

    @AfterEach
    void tearDown() {
        deleteCourseRequest.cleanUp(actualCourseId);
    }

    @Test
    void assertNotFound(){
        Response response = searchCourseRequest.searchByAvailability("NoResults");

        String responseMessage = response.then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("error");

        assertEquals(NOT_FOUND_ERROR_MESSAGE, responseMessage);
    }

    //TODO Add 500 Internal Error Test

}
