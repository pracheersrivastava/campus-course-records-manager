package edu.ccrm.domain;

/**
 * Enum representing academic grades with associated grade points.
 *
 * DEMONSTRATES:
 * - Enum with fields and a constructor.
 * - Storing constant, related values.
 */
public enum Grade {
    S(10.0), // Outstanding
    A(9.0),  // Excellent
    B(8.0),  // Good
    C(7.0),  // Average
    D(6.0),  // Pass
    E(5.0),  // Fail
    F(0.0);  // Fail

    private final double gradePoint;

    /**
     * Constructor for the enum.
     * @param gradePoint The numerical point value for the grade.
     */
    Grade(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public double getGradePoint() {
        return gradePoint;
    }
}
