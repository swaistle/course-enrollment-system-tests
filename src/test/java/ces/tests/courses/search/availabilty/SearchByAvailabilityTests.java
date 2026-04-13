package ces.tests.courses.search.availabilty;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.courses.SearchCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class SearchByAvailabilityTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();
    SearchCourseRequest searchCourseRequest = new SearchCourseRequest();

    String actualCourseId;
    String actualCourseCode;

    @BeforeEach
    void setUp(){
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
    void assertSearchByAvailabilityStatus(){
        Response response = searchCourseRequest.searchByAvailability(actualCourseCode);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertSearchByAvailabilitySchema(){
        Response response = searchCourseRequest.searchByAvailability(actualCourseCode);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/SearchCourseAvailabilitySchema.json"));
    }

}
