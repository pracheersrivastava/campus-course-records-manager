package edu.ccrm.domain;

/**
 * An immutable class representing a course code.
 *
 * DEMONSTRATES:
 * - Immutability: Fields are final and private, no setters.
 * - Overriding equals() and hashCode() for value-based comparison.
 * - Record-like class structure before records were mainstream.
 */
public final class CourseCode {
    private final String code;

    public CourseCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty.");
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CourseCode that = (CourseCode) obj;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
