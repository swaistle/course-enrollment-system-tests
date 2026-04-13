package ces.utils;


public class Helper {

    public static final String HOST = System.getenv("APP_URL");
    public static final String CANDIDATE_ID = System.getenv("CANDIDATE_ID");
    public static final String PASSWORD = System.getenv("PASSWORD");

    public static final String COURSE_CONTEXT_PATH = "/courses";
    public static final String COURSE_ALL_CONTEXT_PATH = "/all";
    public static final String COURSE_TITLE_CONTEXT_PATH = "/title/";
    public static final String COURSE_INSTRUCTOR_CONTEXT_PATH = "/instructor/";
    public static final String COURSE_AVAILABILITY_CONTEXT_PATH = "/availability/";

    public static final String ENROLLMENTS_CONTEXT_PATH = "/enrolments";
    public static final String ENROLLMENTS_ENROL_CONTEXT_PATH = "/enrol";
    public static final String ENROLLMENTS_DROP_CONTEXT_PATH = "/drop";
    public static final String ENROLLMENTS_HISTORY_CONTEXT_PATH = "/history";
    public static final String ENROLLMENTS_ACTIVE_CONTEXT_PATH = "/active";

    public static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Invalid credentials";
    public static final String DATE_ERROR_MESSAGE = "startDate must be before endDate";
    public static final String COURSE_CODE_ERROR_MESSAGE = "Course code already exists";
    public static final String COURSE_REQUIRED_FIELDS_ERROR_MESSAGE = "All fields are required: title, instructor, courseCode, category, totalCapacity, startDate, endDate";
    public static final String NO_AUTH_ERROR_MESSAGE = "No token provided";
    public static final String FAILED_AUTH_ERROR_MESSAGE = "Failed to authenticate token";
    public static final String BAD_REQUEST_ERROR_MESSAGE = "Bad Request";
    public static final String NOT_FOUND_ERROR_MESSAGE = "Course not found";
    public static final String DELETE_COURSE_ERROR_MESSAGE = "Error deleting course";
    public static final String DELETE_FORBIDDEN_ERROR_MESSAGE = "Forbidden: you can only delete your own courses";
    public static final String ACCESS_DENIED_INSTRUCTORS_ONLY = "Access denied: instructors only";
    public static final String ACCESS_DENIED_STUDENTS_ONLY = "Access denied: students only";
    public static final String FETCH_COURSE_ERROR_MESSAGE = "Error fetching course";
    public static final String COURSE_FULL_MESSAGE = "Course full";
    public static final String COURSE_FULL_ERROR_MESSAGE = "Course is full";
    public static final String COURSE_ALREADY_ENROLLED_ERROR_MESSAGE = "Already enrolled in this course";
    public static final String USER_NOT_FOUND_ERROR_MESSAGE = "User does not exist";
    public static final String NO_ACTIVE_ENROLLMENT_ERROR_MESSAGE = "No active enrolment found for this course";

}