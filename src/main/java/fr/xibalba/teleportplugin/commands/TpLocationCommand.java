package fr.xibalba.teleportplugin.commands;

import fr.xibalba.teleportplugin.annotations.CommandHandler;
import fr.xibalba.teleportplugin.factories.LocationRepositoryFactory;
import fr.xibalba.teleportplugin.repositories.LocationRepository;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@CommandHandler(name = "tplocation")
public class TpLocationCommand implements CommandExecutor, TabCompleter {

    /**
     * Handles the /tplocation command. Checks all arguments and if the sender is a player, then teleport him to the location.
     * @param commandSender The sender of the command. Should be a player.
     * @param command The command.
     * @param commandAlias The alias of the command used.
     * @param args The arguments of the command. Should be only a valid location.
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandAlias, @NotNull String[] args) {
        LocationRepository locationRepository = LocationRepositoryFactory.getInstance();
        if (args.length != 1) {
            commandSender.sendMessage("§cUsage: /" + commandAlias + " <location>");
            return false;
        }
        if (!locationRepository.contains(args[0])) {
            commandSender.sendMessage("§cUnknown location: " + args[0]);
            return false;
        }
        if (commandSender instanceof Player player) {
            commandSender.sendMessage("§aTeleporting to " + args[0] + "...");
            player.teleport(new Location(
                    player.getServer().getWorld(
                            locationRepository.get(args[0]).getWorld() == null ?
                                    new NamespacedKey("minecraft", "overworld") :
                                    NamespacedKey.fromString(locationRepository.get(args[0]).getWorld())
                    ),
                    locationRepository.get(args[0]).getX(),
                    locationRepository.get(args[0]).getY(),
                    locationRepository.get(args[0]).getZ()
            ), PlayerTeleportEvent.TeleportCause.COMMAND);
            return true;
        } else {
            commandSender.sendMessage("§cOnly players can use this command.");
            return false;
        }
    }

    /**
     * Handles the tab completion of the /tplocation command.
     * @param commandSender The sender of the command.
     * @param command The command.
     * @param commandAlias The alias of the command used.
     * @param args The arguments of the command. The first argument should be the start of a location.
     * @return A list of all locations that start with the first argument.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandAlias, @NotNull String[] args) {
        LocationRepository locationRepository = LocationRepositoryFactory.getInstance();
        if (args.length != 1)
            return List.of();
        return locationRepository.keys().stream().filter(s -> s.startsWith(args[0])).toList();
    }
}
