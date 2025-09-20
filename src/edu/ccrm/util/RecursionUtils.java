package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for recursive operations.
 *
 * DEMONSTRATES:
 * - Recursion (implicitly via Files.walkFileTree).
 * - NIO.2 API for file system traversal.
 */
public class RecursionUtils {

    /**
     * Recursively calculates the total size of a directory.
     *
     * @param path The path to the directory.
     * @return The total size in bytes.
     * @throws IOException If an I/O error occurs.
     */
    public static long calculateDirectorySize(Path path) throws IOException {
        final AtomicLong size = new AtomicLong(0);

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                size.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                // Log the error and continue
                System.err.println("Failed to access file: " + file + " - " + exc.getMessage());
                return FileVisitResult.CONTINUE;
            }
        });

        return size.get();
    }
}
