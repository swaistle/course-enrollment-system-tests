package ces.tests.courses.search.title;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchByTitleValidationTests {

    @Test
    void assertBadRequest() {
        RequestSpecification request = RestAssured.given();
        String appUrl = HOST + COURSE_CONTEXT_PATH + COURSE_TITLE_CONTEXT_PATH + "?foo";

        Response response = request
                .accept("*/*")
                .when()
                .get(appUrl);

        String responseMessage = response.then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("error");

        assertEquals(FETCH_COURSE_ERROR_MESSAGE, responseMessage);
    }

}
