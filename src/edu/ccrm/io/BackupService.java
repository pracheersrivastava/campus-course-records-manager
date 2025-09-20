package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service for handling data backups.
 *
 * DEMONSTRATES:
 * - NIO.2 API for file operations (copy, create directories).
 * - Date/Time API for creating timestamped folder names.
 */
public class BackupService {

    private final Path dataDir;
    private final Path backupDir;

    public BackupService() {
        this.dataDir = AppConfig.getInstance().getDataPath();
        this.backupDir = AppConfig.getInstance().getBackupPath();
        try {
            if (Files.notExists(backupDir)) {
                Files.createDirectories(backupDir);
            }
        } catch (IOException e) {
            System.err.println("Error creating backup directory: " + e.getMessage());
        }
    }

    public void performBackup() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path newBackupFolder = backupDir.resolve("backup_" + timestamp);
        Files.createDirectory(newBackupFolder);

        Path studentFile = dataDir.resolve(AppConfig.getInstance().getProperty("students.csv.name"));
        Path courseFile = dataDir.resolve(AppConfig.getInstance().getProperty("courses.csv.name"));

        if (Files.exists(studentFile)) {
            Files.copy(studentFile, newBackupFolder.resolve(studentFile.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
        if (Files.exists(courseFile)) {
            Files.copy(courseFile, newBackupFolder.resolve(courseFile.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }

        System.out.println("Backup created successfully at: " + newBackupFolder);
    }
}
