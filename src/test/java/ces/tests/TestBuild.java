package ces.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestBuild {
    private static final Logger LOG = LoggerFactory.getLogger(TestBuild.class);

    @Test
    void assertStatusGet() {
        String _url = System.getenv("APP_URL") + "/status";
        LOG.debug("url: " + _url);

        Response response;
        RequestSpecification request = RestAssured.given();
        response = request.get(_url);
        response.then().assertThat().statusCode(200);

    }
}
