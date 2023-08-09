package fr.xibalba.teleportplugin;

import fr.xibalba.teleportplugin.annotations.CommandHandler;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Objects;
import java.util.logging.Level;

public final class TeleportPlugin extends JavaPlugin {

    /**
     * A static instance of the plugin used to access it from anywhere
     */
    private static TeleportPlugin instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        registerCommands();
    }

    public static TeleportPlugin getInstance() {
        return instance;
    }

    /**
     * Register all the command handlers annotated with {@link CommandHandler}
     */
    private void registerCommands() {
        Reflections reflections = new Reflections("fr.xibalba.teleportplugin.commands");
        reflections.getTypesAnnotatedWith(CommandHandler.class).forEach(commandHandler -> {
            if (!CommandExecutor.class.isAssignableFrom(commandHandler))
                getLogger().log(Level.WARNING, "Class " + commandHandler.getName() + " must implement CommandExecutor");
            try {
                PluginCommand command = Objects.requireNonNull(getCommand(commandHandler.getAnnotation(CommandHandler.class).name()));
                CommandExecutor executor = (CommandExecutor) commandHandler.getConstructors()[0].newInstance();
                command.setExecutor(executor);
            } catch (Exception e) {
                getLogger().log(Level.WARNING, "Error while registering command " + commandHandler.getAnnotation(CommandHandler.class).name());
            }
        });
    }
}
