package ces.utils.courses;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ces.utils.Helper.*;

public class SearchCourseRequest {

    private final Logger log = LoggerFactory.getLogger(SearchCourseRequest.class);

    public Response searchByTitle(String title){
        RequestSpecification request = RestAssured.given();

        String appUrl = HOST + COURSE_CONTEXT_PATH + COURSE_TITLE_CONTEXT_PATH + title;

        log.debug("Searching via title: {}", title);

        return request
                .accept("*/*")
                .when()
                .get(appUrl);
    }

    public Response searchByInstructor(String instructor){
        RequestSpecification request = RestAssured.given();

        String appUrl = HOST + COURSE_CONTEXT_PATH + COURSE_INSTRUCTOR_CONTEXT_PATH + instructor;

        log.debug("Searching via instructor: {}", instructor);

        return request
                .accept("*/*")
                .when()
                .get(appUrl);
    }

    public Response searchByAvailability(String courseCode){
        RequestSpecification request = RestAssured.given();

        String appUrl = HOST + COURSE_CONTEXT_PATH + COURSE_AVAILABILITY_CONTEXT_PATH + courseCode;

        log.debug("Searching via availability: {}", courseCode);

        return request
                .accept("*/*")
                .when()
                .get(appUrl);
    }

}
