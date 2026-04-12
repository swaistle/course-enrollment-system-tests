package ces.tests.instructor.courses.search.instructor;

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

class SearchByInstructorTests {

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
    void assertSearchByInstructorStatus(){
        String instructorId = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = searchCourseRequest.searchByInstructor(instructorId);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertSearchByInstructorResultsSchema(){
        String instructorId = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
        Response response = searchCourseRequest.searchByInstructor(instructorId);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/SearchCourseResultsSchema.json"));
    }

    @Test
    void assertSearchByInstructorNoResultsSchema(){
        Response response = searchCourseRequest.searchByInstructor("NoResults");

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/SearchCourseResultsSchema.json"));
    }
}
