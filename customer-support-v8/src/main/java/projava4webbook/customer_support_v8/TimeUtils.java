package projava4webbook.customer_support_v8;

public class TimeUtils {
    private static final int ONE_SECOND = 1_000;
    private static final int ONE_MINUTE = 60_000;

    public static String intervalToString(long timeIntervalMilliseconds) {
        if(timeIntervalMilliseconds < ONE_SECOND) {
            return "less than one second";
        } else if(timeIntervalMilliseconds < ONE_MINUTE) {
            return (timeIntervalMilliseconds / ONE_SECOND) + " seconds";
        } else {
            return "about " + (timeIntervalMilliseconds / ONE_MINUTE) + " minutes";
        }
    }
}
