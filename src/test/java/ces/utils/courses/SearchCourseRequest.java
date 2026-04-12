package ces.utils.courses;

import ces.utils.BearerTokenGenerator;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ces.utils.Helper.*;


public class SearchCourseRequest {

    final String role = "instructor";
    private final Logger log = LoggerFactory.getLogger(SearchCourseRequest.class);


    BearerTokenGenerator bearerTokenGenerator = new BearerTokenGenerator();
    String accessToken = bearerTokenGenerator.extractBearerToken(role);

    public Response searchTitle(String title){
        RequestSpecification request = RestAssured.given();

        String appUrl = HOST + COURSE_CONTEXT_PATH + COURSE_TITLE_CONTEXT_PATH + title;

        log.debug("Creating default course test data");

        return request
                .accept("*/*")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(appUrl);
    }

}
