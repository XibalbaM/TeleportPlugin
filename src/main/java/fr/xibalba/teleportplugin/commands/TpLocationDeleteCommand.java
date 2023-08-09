package fr.xibalba.teleportplugin.commands;

import fr.xibalba.teleportplugin.annotations.CommandHandler;
import fr.xibalba.teleportplugin.factories.LocationRepositoryFactory;
import fr.xibalba.teleportplugin.repositories.LocationRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@CommandHandler(name = "tplocation-delete")
public class TpLocationDeleteCommand implements CommandExecutor, TabCompleter {

    /**
     * Handles the /tplocation-delete command. Checks all arguments then delete the location.
     * @param commandSender The sender of the command.
     * @param command The command.
     * @param commandAlias The alias of the command used.
     * @param args The arguments of the command. Should be only a valid location.
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandAlias, @NotNull String[] args) {
        LocationRepository locationRepository = LocationRepositoryFactory.getInstance();
        if (args.length != 1) {
            commandSender.sendMessage("§cUsage: /" + commandAlias + " <locationName>");
            return false;
        }
        if (!locationRepository.contains(args[0])) {
            commandSender.sendMessage("§cLocation " + args[0] + " doesn't exist.");
            return false;
        }
        locationRepository.remove(args[0]);
        commandSender.sendMessage("§aLocation " + args[0] + " deleted.");
        return true;
    }

    /**
     * Handles the tab completion for the /tplocation-delete command.
     * @param commandSender The sender of the command.
     * @param command The command.
     * @param commandAlias The alias of the command used.
     * @param args The arguments of the command. Should be only a valid location.
     * @return A list of all locations that starts with the first argument and an empty list for the others.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandAlias, @NotNull String[] args) {
        LocationRepository locationRepository = LocationRepositoryFactory.getInstance();
        if (args.length != 1)
            return List.of();
        return locationRepository.keys().stream().filter(s -> s.startsWith(args[0])).toList();
    }
}
