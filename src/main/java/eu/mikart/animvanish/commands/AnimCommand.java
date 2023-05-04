package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.annonations.CommandAnnotation;
import eu.mikart.animvanish.util.Settings;
import lombok.Getter;
import org.bukkit.command.TabExecutor;

import java.util.Objects;

public abstract class AnimCommand implements TabExecutor {

	private final CommandAnnotation commandAnnotation = getClass().getAnnotation(CommandAnnotation.class);

	@Getter
	private final String name = commandAnnotation.name();

	public void register() {
		if (Settings.DEBUG) Main.getInstance().getLogger().info("Registering command " + name);
		Objects.requireNonNull(Main.getInstance().getCommand(this.name)).setExecutor(this);
	}

}
