package formatter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author fabiomazzone
 */
public class DateFormatter {
    public static String fromNow(LocalDateTime then) {
        LocalDateTime now = LocalDateTime.now();

        Long seconds    = ChronoUnit.SECONDS.between(then, now);
        Long minutes    = ChronoUnit.MINUTES.between(then, now);
        Long hours      = ChronoUnit.HOURS.between(then, now);
        Long days       = ChronoUnit.DAYS.between(then, now);
        Long months     = ChronoUnit.MONTHS.between(then, now);
        Long years      = ChronoUnit.YEARS.between(then, now);

        if(seconds < 45) {
            return "a few seconds ago";
        } else if( seconds < 90 ) {
            return "a minute ago";
        } else if( minutes <=  45 ) {
            return minutes + " minutes ago";
        } else if( minutes <= 90 ) {
            return "an hour ago";
        } else if( hours <= 22 ) {
            return hours + " hours ago";
        } else if( hours < 36) {
            return "a day ago";
        } else if( days <= 25) {
            return days + " days ago";
        } else if( days <= 45 ) {
            return "a month ago";
        } else if( months <= 11) {
            return months + " months ago";
        } else if( years < 2) {
            return "a year ago";
        }
        return years + " years ago";
    }
}
