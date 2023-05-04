package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.commands.impl.*;

import java.util.ArrayList;

public class AnimCommandManager {

	private static final ArrayList<AnimCommand> commands = new ArrayList<>();

	public AnimCommandManager() {
		commands.add(new AnimVanishCommand());
		commands.add(new InvisCommand());
	}

	/**
	 * Returns all the effects
	 * @return ArrayList of Commands
	 * @since 1.0.8
	 * @see AnimCommand
	 */
	public ArrayList<AnimCommand> getCommands() {
		return commands;
	}

	public void registerAll() {
		for (AnimCommand command : commands) command.register();
	}

}
