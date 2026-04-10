package ces.tests.instructor.courses;

import ces.utils.TestSetUp;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class AddCourseTests {

    private final Logger log = LoggerFactory.getLogger(AddCourseTests.class);

    private final TestSetUp testSetUp = new TestSetUp();

    final String role = "instructor";
    String courseId;

    @BeforeEach
    void setTestSetUp(){
        final String CANDIDATE_ID = System.getenv("CANDIDATE_ID");

        final String username = role + "_" + CANDIDATE_ID + CANDIDATE_ID;

        LocalDate localDate = LocalDate.now();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", username + " course");
        requestBody.put("instructor", username);
        requestBody.put("courseCode", "TST123");
        requestBody.put("category", "Testing");
        requestBody.put("totalCapacity", String.valueOf(30));
        requestBody.put("startDate", localDate.toString());
        requestBody.put("endDate", localDate.plusMonths(3).toString());

        courseId = testSetUp.createCourse(role, requestBody);
        log.debug("Setting course id {}", courseId);

    }

}
