package fr.xibalba.teleportplugin.repositories;

import fr.xibalba.teleportplugin.models.TeleportationTarget;

import java.util.Map;
import java.util.Set;

public interface LocationRepository {

    /**
     * Save a new location. Overwrite if the name already exists.
     * @param name The name of the location.
     * @param location The location.
     */
    void add(String name, TeleportationTarget location);

    /**
     * Get a location by its name.
     * @param name The name of the location.
     * @return The location.
     */
    TeleportationTarget get(String name);

    /**
     * Get all locations.
     * @return A map of all locations.
     */
    Map<String, TeleportationTarget> getAll();

    /**
     * Remove a location by its name.
     * @param name The name of the location.
     */
    void remove(String name);

    /**
     * Remove all locations.
     */
    void clear();

    /**
     * Check if a location exists by its name.
     * @param name The name of the location.
     * @return True if the location exists.
     */
    boolean contains(String name);

    /**
     * Get all location names.
     * @return A set of all location names.
     */
    Set<String> keys();

    /**
     * Get all location entries.
     * @return A set of all location entries.
     */
    Set<TeleportationTarget> values();
}