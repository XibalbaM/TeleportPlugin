package fr.xibalba.teleportplugin.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for automatic command handlers' registration.
 * Target class must implement {@link org.bukkit.command.CommandExecutor} and have a constructor with no parameters.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandHandler {
    String name();
}