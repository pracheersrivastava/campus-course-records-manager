# Campus Course & Records Manager (CCRM)

## 1. Project Overview
CCRM is a comprehensive, menu-driven command-line application built with Java SE 17+. It serves as a robust platform for academic administrators to manage student information, course catalogs, enrollments, and academic records efficiently.

The system demonstrates a wide array of Java features, from core object-oriented principles and advanced language constructs to modern APIs like Streams, NIO.2, and the Date/Time API. It is designed to be a practical showcase of Java SE capabilities in a real-world-like scenario.

### Core Features:
- **Student Management:** Add, update, list, and deactivate student profiles.
- **Course Management:** Manage course details, including search and filtering.
- **Enrollment & Grading:** Handle student enrollments, grade assignments, and GPA calculations.
- **Data Persistence:** Import and export data from/to CSV files.
- **System Utilities:** Create timestamped backups and perform recursive calculations.
- **Reporting:** Generate student transcripts and system-wide academic reports.

---

## 2. How to Run the Application

### Prerequisites
- **Java Development Kit (JDK) 17 or higher.**
- An IDE like **Eclipse**, **IntelliJ IDEA**, or **VS Code** (optional but recommended).
- **Git** for cloning the repository.

### Steps:
1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd CCRM
   ```

2. **Compile the project from the command line:**
   Navigate to the `src` directory and run:
   ```bash
   javac edu/ccrm/cli/MainMenu.java
   ```

3. **Run the application:**
   From the `src` directory, execute the main class:
   ```bash
   java edu.ccrm.cli.MainMenu
   ```

---

## 3. Evolution of Java
*(Placeholder for content on the evolution of Java, from its inception to the latest LTS version.)*

---

## 4. Java ME vs. SE vs. EE
*(Placeholder for a detailed comparison of Java Micro Edition, Standard Edition, and Enterprise Edition.)*

---

## 5. JDK vs. JRE vs. JVM
*(Placeholder for an explanation of the Java Development Kit, Java Runtime Environment, and Java Virtual Machine.)*

---

## 6. Windows + Eclipse Setup
*(Placeholder for screenshots and instructions on setting up the project in Eclipse on a Windows machine.)*

---

## 7. Mapping of Syllabus Topics to Code
*(This section will be filled out to map specific Java syllabus topics to their implementation within the CCRM project.)*

| # | Syllabus Topic | Location in Code |
|---|---|---|
| 1 | `switch` Statements | `edu.ccrm.cli.MainMenu.java` |
| 2 | Streams API | `edu.ccrm.service.CourseService.java` |
| 3 | NIO.2 API | `edu.ccrm.io.BackupService.java` |
| 4 | Singleton Pattern | `edu.ccrm.config.AppConfig.java` |
| 5 | Builder Pattern | `edu.ccrm.domain.Course.java` |
|...| ... | ... |

---

## 8. A Note on Assertions
Assertions are used in this project to validate assumptions about the program's state. For example, `assert student != null;` checks that a student object is not null before proceeding.

**Important:** Assertions are disabled by default at runtime. To enable them, you must use the `-ea` (or `-enableassertions`) flag when running the application:
```bash
java -ea edu.ccrm.cli.MainMenu
```

---

## 9. Acknowledgements
This project was generated with the assistance of an advanced AI programming assistant.
