package kutilities.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
public class DateTimeUtils {

    public static LocalDateTime parseLocalDateTime(String dateTimeString, String formatString, Locale locale) {
        return LocalDateTime.parse(dateTimeString, ofPattern(formatString, locale));
    }

    public static String formatLocalDateTime(String localDateTime, String fromFormat, String toFormat, Locale fromLocale, Locale toLocale) {
        return LocalDateTime.parse(localDateTime, ofPattern(fromFormat, fromLocale)).format(ofPattern(toFormat, toLocale));
    }
}
