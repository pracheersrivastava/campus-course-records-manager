package edu.ccrm.util;

import java.util.regex.Pattern;

/**
 * Utility class for validation tasks.
 *
 * DEMONSTRATES:
 * - Static methods for common validation logic.
 * - Use of regular expressions for email validation.
 */
public class Validators {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Validates an email address against a standard regex.
     *
     * @param email The email string to validate.
     * @return true if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Checks if a string is not null and not empty.
     *
     * @param str The string to check.
     * @return true if the string is not null and not empty.
     */
    public static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
