package iesfranciscodelosrios.dam.imageconsulting.utils;

import java.util.regex.Pattern;

public class ValidatorUtils {
    private static PasswordAuthentication pa = new PasswordAuthentication();
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

    public static boolean isValidSocialSecurityNumber(String socialSecurityNumber) {
        // Eliminar espacios en blanco y guiones del número de seguridad social
        socialSecurityNumber = socialSecurityNumber.replaceAll("[ -]", "");

        // Comprobar la longitud del número de seguridad social
        if (socialSecurityNumber.length() != 12) {
            return false;
        }

        // Comprobar que todos los caracteres son dígitos
        if (!socialSecurityNumber.matches("\\d{12}")) {
            return false;
        }

        // Calcular el dígito de control
        int controlDigit = Integer.parseInt(socialSecurityNumber.substring(10, 12));
        String numberPart = socialSecurityNumber.substring(0, 10);
        int sum = 0;
        for (int i = 0; i < numberPart.length(); i++) {
            int digit = Character.getNumericValue(numberPart.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        int calculatedControlDigit = (sum % 10 == 0) ? 0 : (10 - (sum % 10));

        // Comparar el dígito de control calculado con el dígito de control proporcionado
        return controlDigit == calculatedControlDigit;
    }
}
