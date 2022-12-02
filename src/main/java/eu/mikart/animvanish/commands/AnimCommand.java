package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.annonations.CommandAnnotation;
import org.bukkit.command.TabExecutor;

public abstract class AnimCommand implements TabExecutor {

	private final CommandAnnotation commandAnnotation = getClass().getAnnotation(CommandAnnotation.class);
	private final String name = commandAnnotation.name();


	public String getName() {
		return name;
	}

}
