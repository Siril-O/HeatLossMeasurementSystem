package ua.heatloss.dao.utils;


public class DaoUtils {

    public final static int DEFAULT_START_POSITION = 0;
    public final static int DEFAULT_LIMIT = 5;
    public final static String DEFAULT_LIMIT_STRING = "5";

    public static int checkStartPosition(final Integer startPosition) {
        if (startPosition == null) {
            return DEFAULT_START_POSITION;
        }
        return startPosition;
    }

    public static int checkMaxResults(final Integer maxResults) {
        if (maxResults == null) {
            return DEFAULT_LIMIT;
        }
        return maxResults;
    }

}
