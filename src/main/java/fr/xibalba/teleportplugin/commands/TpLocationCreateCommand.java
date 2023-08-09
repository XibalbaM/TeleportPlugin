package fr.xibalba.teleportplugin.commands;

import fr.xibalba.teleportplugin.annotations.CommandHandler;
import fr.xibalba.teleportplugin.factories.LocationRepositoryFactory;
import fr.xibalba.teleportplugin.models.TeleportationTarget;
import fr.xibalba.teleportplugin.repositories.LocationRepository;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@CommandHandler(name = "tplocation-create")
public class TpLocationCreateCommand implements CommandExecutor, TabCompleter {

    /**
     * Handles the command /tplocation-create. Checks all arguments then create the location.
     * @param commandSender The sender of the command.
     * @param command The command.
     * @param commandAlias The alias of the command used.
     * @param args The arguments of the command. Should be a location name, a world name, and coordinates.
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandAlias, @NotNull String[] args) {
        LocationRepository locationRepository = LocationRepositoryFactory.getInstance();
        if (args.length != 5) {
            commandSender.sendMessage("§cUsage: /" + commandAlias + " <locationName> <world> <x> <y> <z>");
            return false;
        }
        if (NamespacedKey.fromString(args[1]) == null || commandSender.getServer().getWorld(NamespacedKey.fromString(args[1])) == null) {
            commandSender.sendMessage("§cInvalid world: " + args[1]);
            return false;
        }
        if (locationRepository.contains(args[0])) {
            commandSender.sendMessage("§cLocation " + args[0] + " already exists.");
            return false;
        }
        if (!args[2].matches("-?\\d+") || !args[3].matches("-?\\d+") || !args[4].matches("-?\\d+")) {
            commandSender.sendMessage("§cInvalid coordinates.");
            return false;
        }
        locationRepository.add(args[0], new TeleportationTarget(
                Integer.parseInt(args[2]),
                Integer.parseInt(args[3]),
                Integer.parseInt(args[4]),
                args[1]
        ));
        commandSender.sendMessage("§aLocation " + args[0] + " created.");
        return true;
    }

    /**
     * Handles the tab completion for the command /tplocation-create.
     * @param commandSender The sender of the command.
     * @param command The command.
     * @param commandAlias The alias of the command used.
     * @param args The arguments of the command. Should be a location name, a world name, and coordinates.
     * @return A list of the worlds for the second argument, or an empty list if the argument is not the second one.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandAlias, @NotNull String[] args) {

        return args.length == 2 ? commandSender.getServer().getWorlds().stream().map(world -> world.getKey().toString()).filter(s -> s.startsWith(args[1])).toList() : List.of();
    }
}
