package edu.ccrm.service;

import java.io.IOException;
import java.util.List;

/**
 * An interface for services that can persist and load data.
 *
 * DEMONSTRATES:
 * - Interface defining a contract for I/O operations.
 */
public interface Persistable<T> {
    void saveData(List<T> data) throws IOException;
    List<T> loadData() throws IOException;
}
