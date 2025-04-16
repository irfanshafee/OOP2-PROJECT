package com.calorietracker;

import java.util.List;

/**
 * Generic repository interface for data access operations.
 * @param <T> The type of entity this repository handles
 */
public interface Repository<T> {
    /**
     * Saves an entity.
     * @param entity The entity to save
     * @return The saved entity
     */
    T save(T entity);

    /**
     * Finds an entity by its ID.
     * @param id The ID to search for
     * @return The found entity, or null if not found
     */
    T findById(String id);

    /**
     * Gets all entities.
     * @return List of all entities
     */
    List<T> findAll();

    /**
     * Deletes an entity by its ID.
     * @param id The ID of the entity to delete
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteById(String id);
}