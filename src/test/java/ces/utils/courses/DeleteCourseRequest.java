package ces.utils.courses;

import ces.utils.BearerTokenGenerator;
import ces.utils.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteCourseRequest {

    final String role = "instructor";
    private final Logger log = LoggerFactory.getLogger(DeleteCourseRequest.class);


    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();

    public Response deleteCourse(String actualCourseId){
        RequestSpecification request = RestAssured.given();

        final String appUrl = Helper.HOST + "/courses/" + actualCourseId;

        final String accessToken = bearerTokenGenerator.extractBearerToken(role);

        log.debug("Deleting course test data with courseId: {}", actualCourseId);

        return request
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete(appUrl);

        }

    public void cleanUp(String courseId) {
        log.debug("Running clear down");

        Response response = deleteCourse(courseId);

        String actualCourseCode;
        actualCourseCode = response.then().extract().path("courseCode");

        log.debug("Successfully deleted course test data with courseCode: {}", actualCourseCode);

    }
}
