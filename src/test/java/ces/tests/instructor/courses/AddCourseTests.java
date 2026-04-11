package ces.tests.instructor.courses;

import ces.utils.BaseSetUp;
import ces.utils.BearerTokenGenerator;
import ces.utils.Helper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class AddCourseTests {

    private final Logger log = LoggerFactory.getLogger(AddCourseTests.class);
    BaseSetUp baseSetUp = new BaseSetUp();
    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    final String role = "instructor";
    String actualCourseId;


    @AfterEach
    void tearDown() {
        log.debug("Running clear down");
        baseSetUp.clearDown(actualCourseId);
    }

    @Test
    void assertAddCourseStatus() {
        RequestSpecification request = RestAssured.given();

        final String appUrl = Helper.HOST + "/courses";

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        Response response = request.body(baseSetUp.courseRequestBody())
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);

        response.then()
                .assertThat()
                .statusCode(201);

        JsonPath jsonPath = response.jsonPath();
        actualCourseId = jsonPath.getString("newCourse._id");
        log.debug("actualCourse id: {}", actualCourseId);
    }

}
