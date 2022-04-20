package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnimVanishCommand implements TabExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
			return true;
		}

		Player player = (Player) sender;

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("animvanish.reload")) {
					Main.instance.reloadConfig();
					player.sendMessage(Main.instance.getPrefix() + "Successfully reloaded");
					return true;
				} else {
					player.sendMessage(Main.instance.getPrefix() + ChatColor.RED + "You don't have required permissions to run this command. " + ChatColor.GREEN + "(animvanish.reload)");
				}
			} else if (args[0].equalsIgnoreCase("author")) {
				player.sendMessage(Main.instance.getPrefix() + "Plugin author is " + Main.instance.getDescription().getAuthors());
			}
		} else {
			player.sendMessage(Main.instance.getPrefix() + ChatColor.GREEN + "Version 1.0");
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			List<String> arguments = new ArrayList<>();
			arguments.add("reload");
			arguments.add("author");

			return arguments;
		}

		return null;
	}
}
