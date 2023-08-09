package fr.xibalba.teleportplugin.repositories;

import fr.xibalba.teleportplugin.TeleportPlugin;
import fr.xibalba.teleportplugin.models.TeleportationTarget;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link LocationRepository} implementation using a yaml file to persist data.
 */
public class YamlLocationRepository implements LocationRepository {

    /**
     * The path to the yaml file.
     */
    private final String filePath;
    /**
     * The yaml.
     */
    private final YamlConfiguration configuration;
    /**
     * The locations section of the yaml.
     */
    private final ConfigurationSection locations;

    /**
     * Creates a new {@link YamlLocationRepository} instance.
     * @param filePath The path to the yaml file used to persist data.
     */
    public YamlLocationRepository(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                TeleportPlugin.getInstance().getLogger().warning("Error while creating yaml database");
            }
        }
        this.configuration = YamlConfiguration.loadConfiguration(file);
        if (configuration.getConfigurationSection("locations") == null) {
            configuration.createSection("locations");
        }
        this.locations = configuration.getConfigurationSection("locations");
    }

    /**
     * Saves the yaml.
     */
    private void save() {
        try {
            this.configuration.save(new File(this.filePath));
        } catch (IOException e) {
            TeleportPlugin.getInstance().getLogger().warning("Error while updating yaml database");
        }
    }

    /**
     * Gets the locations from the yaml.
     * @return The locations as a map.
     */
    private Map<String, TeleportationTarget> getDatabase() {
        return this.locations.getKeys(false).stream()
                .filter((key) -> this.locations.getConfigurationSection(key) != null)
                .collect(Collectors.toMap((key) -> key, (key) -> {
                    ConfigurationSection section = this.locations.getConfigurationSection(key);
                    return new TeleportationTarget(
                            section.getInt("x"),
                            section.getInt("y"),
                            section.getInt("z"),
                            section.getString("world")
                    );
                }));
    }

    @Override
    public void add(String name, TeleportationTarget location) {
        ConfigurationSection section = this.locations.createSection(name);
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("world", location.getWorld());
        save();
    }

    @Override
    public TeleportationTarget get(String name) {
        return this.getDatabase().get(name);
    }

    @Override
    public Map<String, TeleportationTarget> getAll() {
        return Map.copyOf(this.getDatabase());
    }

    @Override
    public void remove(String name) {
        this.locations.set(name, null);
        save();
    }

    @Override
    public void clear() {
        this.locations.getKeys(false).forEach((key) -> this.locations.set(key, null));
        save();
    }

    @Override
    public boolean contains(String name) {
        return this.getDatabase().containsKey(name);
    }

    @Override
    public Set<String> keys() {
        return Set.copyOf(this.getDatabase().keySet());
    }

    @Override
    public Set<TeleportationTarget> values() {
        return Set.copyOf(this.getDatabase().values());
    }
}
