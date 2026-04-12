package ces.tests.instructor.courses.search;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.courses.SearchCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ces.utils.Helper.CANDIDATE_ID;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class SearchTitleTests {

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

        actualCourseId = baseSetUp.extractCourseId(response);
    }

    @AfterEach
    void tearDown() {
        deleteCourseRequest.cleanUp(actualCourseId);
    }

    @Test
    void assertSearchTitleStatus(){
        String instructorId = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = searchCourseRequest.searchTitle(instructorId);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertSearchTitleResultsSchema(){
        String instructorId = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = searchCourseRequest.searchTitle(instructorId);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/SearchCourseTitleSchema.json"));
    }

    @Test
    void assertSearchTitleNoResultsSchema(){
        Response response = searchCourseRequest.searchTitle("NoResults");

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/SearchCourseTitleSchema.json"));
    }
}
