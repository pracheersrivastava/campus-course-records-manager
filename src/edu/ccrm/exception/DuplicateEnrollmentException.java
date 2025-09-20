package edu.ccrm.exception;

/**
 * A checked exception thrown when an attempt is made to enroll a student
 * in a course they are already enrolled in.
 *
 * DEMONSTRATES:
 * - Custom Checked Exception: Extends Exception, forcing compile-time handling.
 */
public class DuplicateEnrollmentException extends Exception {

    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}
