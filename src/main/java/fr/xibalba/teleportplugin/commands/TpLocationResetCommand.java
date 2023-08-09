package fr.xibalba.teleportplugin.commands;

import fr.xibalba.teleportplugin.annotations.CommandHandler;
import fr.xibalba.teleportplugin.factories.LocationRepositoryFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@CommandHandler(name = "tplocation-reset")
public class TpLocationResetCommand implements CommandExecutor, TabCompleter {

    /**
     * Handles the /tplocation-reset command. Clears the location repository.
     * @param commandSender The sender of the command.
     * @param command The command.
     * @param commandAlias The alias of the command used.
     * @param args The arguments of the command. Should be empty.
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandAlias, @NotNull String[] args) {
        LocationRepositoryFactory.getInstance().clear();
        commandSender.sendMessage("§aLocations cleared.");
        return true;
    }

    /**
     * Handles the tab completion of the /tplocation-reset command.
     * @param commandSender The sender of the command.
     * @param command The command.
     * @param commandAlias The alias of the command used.
     * @param args The arguments of the command.
     * @return An empty list because there is no argument needed.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String commandAlias, @NotNull String[] args) {
        return List.of();
    }
}
