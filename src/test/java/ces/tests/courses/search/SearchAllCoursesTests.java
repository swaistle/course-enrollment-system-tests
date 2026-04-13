package ces.tests.courses.search;

import ces.utils.courses.SearchCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class SearchAllCoursesTests {

    SearchCourseRequest searchCourseRequest = new SearchCourseRequest();

    @Test
    void assertSearchAllStatus(){
        Response response = searchCourseRequest.searchAll();

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertSearchAllSchema(){
        Response response = searchCourseRequest.searchAll();

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/SearchCourseResultsSchema.json"));
    }

}
