package ces.utils;

import ces.utils.courses.AddCoursePayloadBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static ces.utils.Helper.CANDIDATE_ID;

public class BaseSetUp {

    private final Logger log = LoggerFactory.getLogger(BaseSetUp.class);

    String courseId;
    String courseCode;
    int availableSlots;

    public String generateCourseCode() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomInt = random.nextInt(1, 999);
        String generatedCode;
        generatedCode = "TST" + CANDIDATE_ID + "_" + randomInt;
        return generatedCode;
    }

    public String generateDate(int months) {
        LocalDate localDate = LocalDate.now();

        if (months != 0) {
            return localDate.plusMonths(months).toString();
        } else {
            return localDate.toString();
        }
    }

    public String extractCourseId(Response response) {
        JsonPath jsonPath = response.jsonPath();
        courseId = jsonPath.getString("newCourse._id");
        log.debug("Extracted courseId: {}", courseId);

        return courseId;
    }

    public String extractCourseCode(Response response) {
        JsonPath jsonPath = response.jsonPath();
        courseCode = jsonPath.getString("newCourse.courseCode");
        log.debug("Extracted courseCode: {}", courseCode);

        return courseCode;
    }

    public int extractAvailableSlots(Response response, String path) {
        JsonPath jsonPath = response.jsonPath();
        availableSlots = jsonPath.getInt(path);
        log.debug("Extracted availableSlots: {}", availableSlots);

        return availableSlots;
    }

    public JSONObject generateDefaultPayload() {
        String instructorId = "instructor_" + CANDIDATE_ID + CANDIDATE_ID;
        String newTitle = instructorId + "'s course";

        JSONObject generatedPayload = new AddCoursePayloadBuilder()
                .withTitle(newTitle)
                .withInstructor(instructorId)
                .withCourseCode(generateCourseCode())
                .withStartDate(generateDate(0))
                .withEndDate(generateDate(3))
                .build();

        setCourseCode(generatedPayload);

        return generatedPayload;
    }

    public void setCourseCode(JSONObject generatedPayload) {
        courseCode = generatedPayload.get("courseCode").toString();
        log.debug("Setting up course test data with courseCode: {}", courseCode);
    }

}
