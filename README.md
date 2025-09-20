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
Java has evolved significantly since its first release in 1996. Created by James Gosling at Sun Microsystems, its "write once, run anywhere" philosophy was revolutionary.

- **Early Years (JDK 1.0 - 1.4):** Focused on establishing the core language, AWT, and basic APIs. Java 2 (J2SE 1.2) was a major step, introducing the Collections Framework.
- **The Tiger Era (Java 5, 2004):** A landmark release that introduced Generics, Enums, Annotations, and the `for-each` loop, fundamentally changing how Java code was written.
- **Maturity (Java 6-8):** Java 6 improved performance. Java 7 brought the NIO.2 API and the "diamond operator". Java 8 (2014) was another massive release, introducing **Lambda Expressions**, the **Streams API**, and a new **Date/Time API**, which are heavily used in this project.
- **Modern Java (Java 9+):** Starting with Java 9, Oracle moved to a six-month release cadence with Long-Term Support (LTS) versions every two years. This project targets **Java 17 (LTS)**, which includes features like Records, Sealed Classes, and Pattern Matching for `instanceof`.

---

## 4. Java ME vs. SE vs. EE

Java is not a single entity but a platform with different editions tailored for different environments.

- **Java SE (Standard Edition):** This is the core Java platform. It provides the fundamental APIs and the Java Virtual Machine (JVM) for developing desktop, server, and console applications. **CCRM is a Java SE application.**

- **Java EE (Enterprise Edition) / Jakarta EE:** Built on top of Java SE, this edition provides a rich set of APIs for building large-scale, multi-tiered, and reliable enterprise applications. It includes specifications for technologies like servlets (for web servers), JPA (for database persistence), and messaging queues.

- **Java ME (Micro Edition):** A lightweight subset of Java SE designed for resource-constrained devices like early mobile phones and embedded systems. Its use has largely been superseded by modern mobile operating systems like Android (which uses Java) and iOS.

---

## 5. JDK vs. JRE vs. JVM

These three acronyms are at the heart of the Java platform.

- **JVM (Java Virtual Machine):** The JVM is an abstract computing machine that enables a computer to run a Java program. It interprets the compiled Java bytecode and executes it. The JVM is platform-dependent, meaning you need a specific JVM for Windows, macOS, or Linux, which is the key to Java's "write once, run anywhere" capability.

- **JRE (Java Runtime Environment):** The JRE is the software package that provides the necessary components to *run* Java applications. It consists of the JVM and the Java Class Library (core APIs like `java.lang`, `java.util`, etc.). You need the JRE to run a `.jar` file.

- **JDK (Java Development Kit):** The JDK is a full-featured software development kit for creating Java applications. It includes everything in the JRE, plus development tools like the `javac` compiler, the `jdb` debugger, and the `javadoc` documentation generator. You need the JDK to write and compile Java code.

---

## 6. Windows + Eclipse Setup

Here are the steps to import and run this project in the Eclipse IDE on a Windows machine.

1.  **Prerequisites:** Ensure you have **JDK 17 or higher** installed and configured on your system.
2.  **Install Eclipse:** Download and install the "Eclipse IDE for Java Developers" from the official Eclipse website.
3.  **Clone the Project:** Clone this repository to a location on your computer.
4.  **Import Project into Eclipse:**
    *   Open Eclipse and go to `File -> Import...`.
    *   In the import wizard, select `General -> Existing Projects into Workspace` and click `Next`.
    *   Click `Browse...` next to "Select root directory" and navigate to the folder where you cloned the repository.
    *   The project should appear in the "Projects" list. Ensure it is checked and click `Finish`.
5.  **Configure JDK:**
    *   Right-click the project in the "Package Explorer" and select `Properties`.
    *   Go to `Java Build Path -> Libraries`.
    *   Ensure "JRE System Library" is set to `[JavaSE-17]` or a compatible version. If not, click `Edit...` and select the correct JRE.
6.  **Run the Application:**
    *   In the "Package Explorer", navigate to `src/edu.ccrm.cli`.
    *   Right-click on `MainMenu.java`.
    *   Select `Run As -> Java Application`.
    *   The CCRM menu will appear in the console view at the bottom of the Eclipse window.

*(Placeholder: Add screenshots of the import and run process to the `screenshots/` folder and link them here.)*

---

## 7. Mapping of Syllabus Topics to Code
This project is designed to demonstrate a wide range of Java SE features. Here is a map of key concepts to their implementation in the codebase:

| # | Syllabus Topic | Location in Code |
|---|---|---|
| 1 | **OOP: Abstraction** | `edu.ccrm.domain.Person` (abstract class with abstract method `getDetails()`) |
| 2 | **OOP: Inheritance** | `Student` and `Instructor` classes extend `Person`. |
| 3 | **OOP: Encapsulation** | All domain classes use `private` fields with `public` getters/setters. |
| 4 | **OOP: Polymorphism** | The `getDetails()` method is overridden in `Student` and `Instructor`. |
| 5 | **Data Structures** | `ArrayList` and `HashMap` are used throughout the services (e.g., `StudentService`). |
| 6 | **Enums** | `edu.ccrm.domain.Grade` (with fields) and `Semester` (simple). |
| 7 | **Immutable Class** | `edu.ccrm.domain.CourseCode` is an immutable value object. |
| 8 | **Nested & Inner Classes** | `Student.RegistrationNumberGenerator` (static nested), `Course.Builder` (inner). |
| 9 | **Interfaces** | `Searchable<T>` and `Persistable<T>` in the `service` package. |
| 10 | **Lambda Expressions** | `edu.ccrm.util.Comparators` for sorting; used with Streams in services. |
| 11 | **Streams API** | `CourseService` (for filtering), `TranscriptService` (for GPA calculation). |
| 12 | **NIO.2 API** | `ImportExportService` and `BackupService` use `Path`, `Files`, and `Paths`. |
| 13 | **Date/Time API** | `BackupService` uses `LocalDateTime` for timestamped folders. |
| 14 | **Custom Exceptions** | `DuplicateEnrollmentException` (checked), `MaxCreditLimitExceededException` (unchecked). |
| 15 | **Design Patterns** | `AppConfig` (Singleton), `Course.Builder` (Builder). |
| 16 | **Recursion** | `RecursionUtils.calculateDirectorySize()` uses `Files.walkFileTree` for recursive traversal. |
| 17 | **Control Flow** | `MainMenu` demonstrates `do-while`, `switch`, `if-else`, and loops. |

---

## 8. A Note on Assertions
Assertions are used in this project to validate assumptions about the program's state. For example, `assert student != null;` could be used to check that a student object is not null before proceeding.

**Important:** Assertions are disabled by default at runtime. To enable them, you must use the `-ea` (or `-enableassertions`) flag when running the application:
```bash
# From the command line
java -cp bin -ea edu.ccrm.cli.MainMenu

# In Eclipse, you can add this to the VM arguments in the Run Configuration.
```

---
<!--
## 9. Acknowledgements
This project was generated with the assistance of GitHub Copilot, an advanced AI programming assistant.
-->
