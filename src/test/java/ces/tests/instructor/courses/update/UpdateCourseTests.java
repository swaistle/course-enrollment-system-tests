package ces.tests.instructor.courses.update;

import ces.utils.*;
import ces.utils.courses.AddCourseRequest;
import ces.utils.courses.DeleteCourseRequest;
import ces.utils.courses.UpdateCourseRequest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class UpdateCourseTests {

    BaseSetUp baseSetUp = new BaseSetUp();
    AddCourseRequest addCourseRequest = new AddCourseRequest();
    UpdateCourseRequest updateCourseRequest = new UpdateCourseRequest();
    DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest();

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
    void assertUpdateCourseStatus(){
        LocalDate newDate = LocalDate.now().plusMonths(5);

        JSONObject updatePayload = new JSONObject();
        updatePayload.put("title", "Updated Title");
        updatePayload.put("totalCapacity", 35);
        updatePayload.put("availableSlots", 30);
        updatePayload.put("endDate", newDate.toString());

        Response response = updateCourseRequest.updateCourseRequest(actualCourseId, updatePayload);

        response.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void assertUpdateCourseSchema(){
        LocalDate newDate = LocalDate.now().plusMonths(5);

        JSONObject updatePayload = new JSONObject();
        updatePayload.put("title", "Updated Title");
        updatePayload.put("totalCapacity", 35);
        updatePayload.put("availableSlots", 30);
        updatePayload.put("endDate", newDate.toString());

        Response response = updateCourseRequest.updateCourseRequest(actualCourseId, updatePayload);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/UpdateCourseSchema.json"));

    }

}
