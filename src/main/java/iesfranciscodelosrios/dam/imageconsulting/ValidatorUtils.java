package iesfranciscodelosrios.dam.imageconsulting;

import java.util.regex.Pattern;

public class ValidatorUtils {
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final String DNI_REGEX = "\\d{8}[a-zA-Z]";

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isValidDni(String dni) {
        return Pattern.matches(DNI_REGEX, dni);
    }

    public static boolean isValidPhone(String phone) {
        // Regular expression for phone numbers without country prefix (9 digits)
        String phoneRegexWithoutPrefix = "\\d{9}";

        // Regular expression for phone numbers with country prefix (+ followed by 1 or more digits and then 9 digits)
        String phoneRegexWithPrefix = "\\+\\d{1,}\\d{9}";

        return Pattern.matches(phoneRegexWithoutPrefix, phone) || Pattern.matches(phoneRegexWithPrefix, phone);
    }
}
