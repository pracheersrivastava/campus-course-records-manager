package edu.ccrm.exception;

/**
 * An unchecked exception thrown when an enrollment would cause a student
 * to exceed the maximum allowed credits for a semester.
 *
 * DEMONSTRATES:
 * - Custom Unchecked Exception: Extends RuntimeException, allowing for optional handling.
 */
public class MaxCreditLimitExceededException extends RuntimeException {

    public MaxCreditLimitExceededException(String message) {
        super(message);
    }
}
