package ces.utils.courses;

import org.json.JSONObject;

public class AddCoursePayloadBuilder {

    private final String title;
    private final String instructor;
    private final String courseCode;
    private final String category;
    private final int totalCapacity;
    private final String startDate;
    private final String endDate;

    public AddCoursePayloadBuilder() {
        this("instructor course",
                "instructor_123",
                "TST_XXX",
                "Testing",
                30,
                "2026-04-01",
                "2026-08-01");
    }

    private AddCoursePayloadBuilder(
            String title,
            String instructor,
            String courseCode,
            String category,
            int totalCapacity,
            String startDate,
            String endDate
    ) {
        this.title = title;
        this.instructor = instructor;
        this.courseCode = courseCode;
        this.category = category;
        this.totalCapacity = totalCapacity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AddCoursePayloadBuilder withTitle(String newValue) {
        return new AddCoursePayloadBuilder(newValue,
                instructor,
                courseCode,
                category,
                totalCapacity,
                startDate,
                endDate);
    }

    public AddCoursePayloadBuilder withInstructor(String newValue) {
        return new AddCoursePayloadBuilder(title,
                newValue,
                courseCode,
                category,
                totalCapacity,
                startDate,
                endDate);
    }

    public AddCoursePayloadBuilder withCourseCode(String newValue) {
        return new AddCoursePayloadBuilder(title,
                instructor,
                newValue,
                category,
                totalCapacity,
                startDate,
                endDate);
    }

    public AddCoursePayloadBuilder withCategory(String newValue) {
        return new AddCoursePayloadBuilder(title,
                instructor,
                courseCode,
                newValue,
                totalCapacity,
                startDate,
                endDate);
    }

    public AddCoursePayloadBuilder withTotalCapacity(int newValue) {
        return new AddCoursePayloadBuilder(title,
                instructor,
                courseCode,
                category,
                newValue,
                startDate,
                endDate);
    }

    public AddCoursePayloadBuilder withStartDate(String newValue) {
        return new AddCoursePayloadBuilder(title,
                instructor,
                courseCode,
                category,
                totalCapacity,
                newValue,
                endDate);
    }

    public AddCoursePayloadBuilder withEndDate(String newValue) {
        return new AddCoursePayloadBuilder(title,
                instructor,
                courseCode,
                category,
                totalCapacity,
                startDate,
                newValue);
    }

    public JSONObject build() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("instructor", instructor);
        json.put("courseCode", courseCode);
        json.put("category", category);
        json.put("totalCapacity", totalCapacity);
        json.put("startDate", startDate);
        json.put("endDate", endDate);
        return json;
    }

}
