package ces.tests.instructor.courses.update;

import ces.utils.BaseSetUp;
import ces.utils.BearerTokenGenerator;
import ces.utils.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

class UpdateCourseTests {
    private final Logger log = LoggerFactory.getLogger(UpdateCourseTests.class);

    BaseSetUp baseSetUp = new BaseSetUp();
    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();
    String actualCourseId;
    String appUrl;

    @BeforeEach
    void setUp(){
        Response response = baseSetUp.createCourse();

        response.then()
                .assertThat()
                .statusCode(201);

        actualCourseId = baseSetUp.extractCourseId(response);

        appUrl = Helper.HOST + "/courses/" + actualCourseId;
    }

    @AfterEach
    void tearDown() {
        log.debug("Running clear down");
        baseSetUp.deleteCourse(actualCourseId);
    }

    @Test
    void assertUpdateCourseStatus(){
        RequestSpecification request = RestAssured.given();

        LocalDate newDate = LocalDate.now().plusMonths(5);

        JSONObject updatePayload = new JSONObject();
        updatePayload.put("title", "Updated Title");
        updatePayload.put("totalCapacity", 35);
        updatePayload.put("availableSlots", 30);
        updatePayload.put("endDate", newDate.toString());

        final String accessToken = bearerTokenGenerator.extractBearerToken("instructor");

        Response response = request
                .body(updatePayload.toString())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .put(appUrl);

        response.then()
                .assertThat()
                .statusCode(200);
    }

}
