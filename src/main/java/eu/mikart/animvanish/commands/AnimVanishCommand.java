package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.config.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnimVanishCommand implements TabExecutor {

	MessageManager messages = Main.instance.messages;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("animvanish.reload")) {

					// Reload configs
					Main.instance.reloadConfig();
					Main.instance.messages.reloadConfig();

					sender.sendMessage(Main.instance.getPrefix() + messages.getMessage("reload"));
					Main.instance.getLogger().info("Configs have been reloaded!");
					return true;
				} else {
					sender.sendMessage(Main.instance.getPrefix() + messages.getMessage("no_permission") + ChatColor.GREEN + " (animvanish.reload)");
				}
			} else if (args[0].equalsIgnoreCase("author")) {
				sender.sendMessage(Main.instance.getPrefix() + "Plugin author is " + ChatColor.GOLD + Main.instance.getDescription().getAuthors());
			} else if (args[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("animvanish.help")) {
					sender.sendMessage(Main.instance.getPrefix() + ChatColor.GREEN + "Available commands:");

					// AnimVanish
					sender.sendMessage(ChatColor.GREEN + "/animvanish reload" + ChatColor.GRAY + " - Reloads the plugin config");
					sender.sendMessage(ChatColor.GREEN + "/animvanish author" + ChatColor.GRAY + " - Shows the plugin author");

					// Invis
					sender.sendMessage(ChatColor.GREEN + "/invis [effect]" + ChatColor.GRAY + " - Vanishes with an effect");
				} else {
					sender.sendMessage(Main.instance.getPrefix() + messages.getMessage("no_permission") + ChatColor.GREEN + " (animvanish.help)");
				}
			} else {
				sender.sendMessage(Main.instance.getPrefix() + messages.getMessage("invalid_args"));
			}
		} else {
			sender.sendMessage(Main.instance.getPrefix() + ChatColor.GREEN + "AnimVanish version " + ChatColor.GOLD + Main.instance.getDescription().getVersion());
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> arguments = new ArrayList<>();
			if (sender.hasPermission("animvanish.reload")) {
				arguments.add("reload");
			}
			arguments.add("author");
			if (sender.hasPermission("animvanish.help")) {
				arguments.add("help");
			}

			return arguments;
		}

		return null;
	}
}
