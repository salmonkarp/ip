package taskbot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A utility class containing helper methods for the TaskBot application.
 */
public class Utility {

    /**
     * Helper method to parse times in different formats.
     * @param input the raw date/time string
     * @return LocalDateTime parsed deadline
     */
    public static LocalDateTime parseTime(String input) {
        String[] patterns = {
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd",
            "MMM d yyyy HH:mm",
            "MMM d yyyy"
        };

        for (String p : patterns) {
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern(p);
                if (p.contains("HH")) {
                    return LocalDateTime.parse(input, format);
                } else {
                    return LocalDate.parse(input, format).atStartOfDay();
                }
            } catch (DateTimeParseException ignored) {
                // ignored as we try the next pattern,
                // and still throw an error if none match
                // hopefully this is enough justification for ignoring exceptions
                // as this is not ignoring the error, just not handling it
                // until we find a match or exhaust all options
            }
        }
        throw new IllegalArgumentException("Unrecognized deadline format: " + input);
    }
}
