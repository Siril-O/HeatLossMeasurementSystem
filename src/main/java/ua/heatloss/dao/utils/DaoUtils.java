package ua.heatloss.dao.utils;


public class DaoUtils {

    public final static int DEFAULT_START_POSITION = 0;
    public final static int DEFAULT_MAX_RESULTS = 10;

    public static int checkStartPosition(final Integer startPosition) {
        if (startPosition == null) {
            return DEFAULT_START_POSITION;
        }
        return startPosition;
    }

    public static int checkMaxResults(final Integer maxResults) {
        if (maxResults == null) {
            return DEFAULT_MAX_RESULTS;
        }
        return maxResults;
    }

}
