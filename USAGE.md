# CCRM Application Usage Guide

This guide provides examples of how to interact with the Campus Course & Records Manager (CCRM) CLI.

## Main Menu
When you launch the application, you will see the main menu:

```
===================================================
 Campus Course & Records Manager (CCRM)
===================================================
1. Manage Students
2. Manage Courses
3. Manage Enrollments & Grades
4. Import / Export Data
5. System Utilities & Reports
0. Exit
===================================================
Choose an option:
```

---

## Example Workflows

### 1. Adding a New Student
- Select `1` from the Main Menu for **Manage Students**.
- Select `1` to **Add a new Student**.
- Enter the required information at the prompts:
  - **Full Name:** `John Doe`
  - **Email:** `john.doe@example.com`

### 2. Listing All Courses
- Select `2` from the Main Menu for **Manage Courses**.
- Select `2` to **List all Courses**.
- The application will display a formatted table of all available courses.

### 3. Enrolling a Student in a Course
- Select `3` from the Main Menu for **Manage Enrollments & Grades**.
- Select `1` to **Enroll a Student in a Course**.
- Enter the **Student Registration Number** and the **Course Code** when prompted.

### 4. Printing a Student's Transcript
- Select `1` from the Main Menu for **Manage Students**.
- Select `5` to **Print Student Transcript**.
- Enter the **Student Registration Number** of the student whose transcript you wish to view.

### 5. Exporting Data
- Select `4` from the Main Menu for **Import / Export Data**.
- Select `2` to **Export all data to CSV**.
- The system will save the current student and course data to `students_export.csv` and `courses_export.csv` in the configured data directory.

### 6. Creating a Backup
- Select `5` from the Main Menu for **System Utilities & Reports**.
- Select `1` to **Create a new backup**.
- The application will create a timestamped folder (e.g., `backup_2025-09-21_14-30-00`) and copy the exported data files into it.

### 7. Searching for Courses by Department
- Select `2` from the Main Menu for **Manage Courses**.
- Select `5` to **Search/Filter Courses**.
- Select `2` to **Filter by Department**.
- Enter the department name (e.g., `Computer Science`) to see all courses offered by that department.

---
