package ces.tests.instructor.courses.delete;

import ces.utils.BaseSetUp;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteCourseTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();

    String actualCourseId;

    @BeforeEach
    void setUp(){
        Response response = addCourseRequest.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractCourseId(response);
    }

    @Test
    void assertDeleteStatus(){
        Response response = deleteCourseRequest.deleteCourse(actualCourseId);

        response.then()
                .assertThat()
                .statusCode(200);
    }

}
