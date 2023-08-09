package fr.xibalba.teleportplugin.repositories;

import fr.xibalba.teleportplugin.models.TeleportationTarget;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple implementation of {@link LocationRepository} that stores all locations in memory.
 */
public class MemoryLocationRepository implements LocationRepository {
    /**
     * The storage of all locations.
     */
    private final Map<String, TeleportationTarget> locations = new HashMap<>();

    @Override
    public void add(String name, TeleportationTarget location) {
        locations.put(name, location);
    }

    @Override
    public TeleportationTarget get(String name) {
        return locations.get(name);
    }

    @Override
    public Map<String, TeleportationTarget> getAll() {
        return locations;
    }

    @Override
    public void remove(String name) {
        locations.remove(name);
    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public boolean contains(String name) {
        return locations.containsKey(name);
    }

    @Override
    public Set<String> keys() {
        return locations.keySet();
    }

    @Override
    public Set<TeleportationTarget> values() {
        return Set.copyOf(locations.values());
    }
}