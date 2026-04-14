package ces.tests.courses.search.availabilty;

import ces.utils.courses.SearchCourseRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.COURSE_FULL_MESSAGE;
import static ces.utils.Helper.NOT_FOUND_ERROR_MESSAGE;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchByAvailabilityValidationTests {

    SearchCourseRequest searchCourseRequest = new SearchCourseRequest();

    @Test
    void assertNotFound() {
        Response response = searchCourseRequest.searchByAvailability("NoResults");

        String responseMessage = response.then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("error");

        assertEquals(NOT_FOUND_ERROR_MESSAGE, responseMessage);
    }

    @Test
    void assertCourseFullSchema() {
        Response response = searchCourseRequest.searchByAvailability("TESTB4B5D0");

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/SearchCourseAvailabilitySchema.json"));

        JsonPath jsonPath = response.jsonPath();
        String actualAvailableSlots = jsonPath.getString("availableSlots");
        assertEquals(COURSE_FULL_MESSAGE, actualAvailableSlots);
    }

}
