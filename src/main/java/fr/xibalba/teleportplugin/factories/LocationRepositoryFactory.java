package fr.xibalba.teleportplugin.factories;

import fr.xibalba.teleportplugin.TeleportPlugin;
import fr.xibalba.teleportplugin.repositories.LocationRepository;
import fr.xibalba.teleportplugin.repositories.MemoryLocationRepository;
import fr.xibalba.teleportplugin.repositories.YamlLocationRepository;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationRepositoryFactory {

    /**
     * The instance of the repository, used as a singleton.
     */
    private static LocationRepository instance;

    /**
     * If the instance isn't already created, create it. Choose the type of repository to use depending on the config.
     * @return The instance of the repository.
     */
    public static LocationRepository getInstance() {
        if (instance == null) {
            FileConfiguration config = TeleportPlugin.getInstance().getConfig();
            String type = config.getString("database.type");
            instance = switch (type == null ? "memory" : type) {
                case "memory" -> new MemoryLocationRepository();
                case "yaml" -> new YamlLocationRepository(TeleportPlugin.getInstance().getDataFolder() + "/locations.yml");
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
        }
        return instance;
    }
}
