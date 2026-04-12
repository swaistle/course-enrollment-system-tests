package ces.utils;


public class Helper {

    public static final String HOST = System.getenv("APP_URL");
    public static final String CANDIDATE_ID = System.getenv("CANDIDATE_ID");
    public static final String PASSWORD = System.getenv("PASSWORD");

    public static final String DATE_ERROR_MESSAGE = "startDate must be before endDate";

}
