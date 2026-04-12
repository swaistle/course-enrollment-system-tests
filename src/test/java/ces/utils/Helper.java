package ces.utils;


public class Helper {

    public static final String HOST = System.getenv("APP_URL");
    public static final String CANDIDATE_ID = System.getenv("CANDIDATE_ID");
    public static final String PASSWORD = System.getenv("PASSWORD");

    public static final String DATE_ERROR_MESSAGE = "startDate must be before endDate";
    public static final String COURSE_CODE_ERROR_MESSAGE = "Course code already exists";
    public static final String COURSE_REQUIRED_FIELDS_ERROR_MESSAGE = "All fields are required: title, instructor, courseCode, category, totalCapacity, startDate, endDate";
    public static final String NO_AUTH_ERROR_MESSAGE = "No token provided";

}
