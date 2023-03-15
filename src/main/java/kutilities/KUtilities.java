package kutilities;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ofPattern;

public class KUtilities {
    public static String convertBigDecimalToString(BigDecimal obj) {
        if (ObjectUtils.isNotEmpty(obj)) {
            DecimalFormat df = new DecimalFormat("0.00########");
            return df.format(obj);
        }
        return "";
    }

    public static String capitalizeString(String str) {
        return StringUtils.isBlank(str) ? "" : StringUtils.capitalize(str.toLowerCase());
    }

    public static String convertSnakeCaseToCamelCase(String str) {
        return Pattern.compile("_([a-z])")
                .matcher(str)
                .replaceAll(m -> m.group(1).toUpperCase());
    }

    private static String convertCamelCaseToSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    public static boolean isNullOrEmpty(String inpString) {
        return inpString == null || inpString.isEmpty() || inpString.equals("null");
    }

    /**
     * LocalDateTime Converter
     * eg:  "01-Jan-2023 00:00:00" => "01-01-2023 00:00:00"
     */
    public static String formatLocalDateTime(String localDateTime, String fromFormat, String toFormat, Locale fromLocale, Locale toLocale) {
        return LocalDateTime.parse(localDateTime, ofPattern(fromFormat, fromLocale)).format(ofPattern(toFormat, toLocale));
    }

    /**
     * LocalDate Converter
     * eg:  "01-Jan-2023" => "01-01-2023"
     */
    public static String formatLocalDate(String localDate, String fromFormat, String toFormat, Locale fromLocale, Locale toLocale) {
        return LocalDate.parse(localDate, ofPattern(fromFormat, fromLocale)).format(ofPattern(toFormat, toLocale));
    }

    /**
     * LocalTime Converter
     * eg:  "00:00" => "00:00:00"
     */
    public static String formatLocalTime(String timeString, String fromFormat, String toFormat) {
        var time = LocalTime.parse(timeString, ofPattern(fromFormat));
        return time.format(ofPattern(toFormat));
    }

    public static LocalDate parseLocalDate(String dateString, String formatString, Locale locale) {
        return LocalDate.parse(dateString, ofPattern(formatString, locale));
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeString, String formatString, Locale locale) {
        return LocalDateTime.parse(dateTimeString, ofPattern(formatString, locale));
    }

    public static Pageable initPageable(String sortBy, String sortDirection, String defaultSortBy,
                                        String defaultSortDirection, List<String> sortAbleList,
                                        String pageNumber, String pageSize) {
        Pageable pageable;

        if (null == sortBy || sortBy.isEmpty() || !sortAbleList.contains(sortBy)) {
            sortBy = defaultSortBy;
        }

        if (null == sortDirection || sortDirection.isEmpty()) {
            sortDirection = defaultSortDirection;
        }

        if (sortDirection.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, Integer.parseInt(pageSize), Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(Integer.parseInt(pageNumber) - 1, Integer.parseInt(pageSize), Sort.by(sortBy).ascending());
        }

        return pageable;
    }

}
