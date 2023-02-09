package kutilities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class KUtilitiesApplication {

    private static final String DD_MMM_YYYY_HH_MM_SS = "dd-MMM-yyyy HH:mm:ss";
    private static final String DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy HH:mm:ss";
    private static final String DD_MMM_YYYY = "dd-MMM-yyyy";
    private static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String HHMM = "HH:mm";
    public static final String HHMMSS = "HH:mm:ss";

    public static void main(String[] args) {
        SpringApplication.run(KUtilitiesApplication.class, args);
        System.out.println(KUtilities.formatLocalDateTime("01-Jan-2023 00:00:00"
                , DD_MMM_YYYY_HH_MM_SS
                , DD_MM_YYYY_HH_MM_SS
                , Locale.ENGLISH
                , Locale.ENGLISH));

        System.out.println(KUtilities.formatLocalDate("01-Jan-2023"
                , DD_MMM_YYYY
                , DD_MM_YYYY
                , Locale.ENGLISH
                , Locale.ENGLISH));
        System.out.println(KUtilities.formatLocalTime("00:00"
                , HHMM
                , HHMMSS));
    }


}
