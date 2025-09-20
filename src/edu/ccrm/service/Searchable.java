package edu.ccrm.service;

import java.util.List;
import java.util.function.Predicate;

/**
 * A generic interface for searchable services.
 *
 * DEMONSTRATES:
 * - Generic Interface: Can be used for any type T.
 * - Functional Interface Usage: Takes a Predicate for filtering.
 * - Default Method: Provides a default implementation for a method.
 */
public interface Searchable<T> {
    List<T> search(Predicate<T> filter);

    /**
     * A default method to find the first item that matches a predicate.
     * @param filter The predicate to test elements against.
     * @return The first matching element, or null if not found.
     */
    default T findFirst(Predicate<T> filter) {
        List<T> results = search(filter);
        return results.isEmpty() ? null : results.get(0);
    }
}
