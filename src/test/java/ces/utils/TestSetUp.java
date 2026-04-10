package ces.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TestSetUp {

    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    public String createCourse(String role, Map<String, String> requestBody) {

        final Logger log = LoggerFactory.getLogger(TestSetUp.class);
        RequestSpecification request = RestAssured.given();

        //Environment variables
        final String appUrl = System.getenv("APP_URL") + "/courses";

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        log.debug("Setting up course test data");

        Response response = request.body(requestBody)
                .accept("*/*")
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post(appUrl);

        String courseId = response.then().extract().path("_id");
        log.debug("Created course with id: {}", courseId);

        return courseId;
    }

}
