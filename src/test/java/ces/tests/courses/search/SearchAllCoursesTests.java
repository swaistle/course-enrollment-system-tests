package ces.tests.courses.search;

import ces.utils.courses.SearchCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

class SearchAllCoursesTests {

    SearchCourseRequest searchCourseRequest = new SearchCourseRequest();

    @Test
    void assertSearchAllStatus(){
        Response response = searchCourseRequest.searchAll();

        response.then()
                .assertThat()
                .statusCode(200);
    }
}
