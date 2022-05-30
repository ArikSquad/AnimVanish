package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.Main;
import eu.mikart.animvanish.config.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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

					Main.instance.adventure().sender(sender).sendMessage(messages.getMessage("reload"));
					Main.instance.adventure().console().sendMessage(messages.getMessage("reload"));
					return true;
				} else {
					Main.instance.adventure().sender(sender).sendMessage(messages.getMessage("animvanish.reload.no_permission"));
				}
			} else if (args[0].equalsIgnoreCase("author")) {
				Main.instance.adventure().sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(messages.getMessage("prefix") + "The plugin author is <gold>" + Main.instance.getDescription().getAuthors() + "</gold>"));
			} else if (args[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("animvanish.help")) {
					Component message = MiniMessage.miniMessage().deserialize("<color:#5f5f5>Available commands:</color> <color:#212121>| <hover:show_text:'Like this!'>Hover to see permissions</hover></color>\n<green><hover:show_text:'animvanish.reload'><click:suggest_command:'/animvanish reload'>/animvanish reload</click></hover></green> <gray>- Reloads the plugin config</gray>\n<green><click:suggest_command:'/animvanish author'>/animvanish author</click></green> <gray>- Shows the plugin author</gray>\n<green><hover:show_text:'animvanish.help'><click:suggest_command:'/animvanish help'>/animvanish help</click></hover></green> <gray>- Shows this help message</gray>\n\n<green><hover:show_text:'animvanish.invis.[effect]'><click:suggest_command:'/invis'>/invis [effect]</click></hover></green> <gray>- Vanishes using an effect</gray>");
					Main.instance.adventure().sender(sender).sendMessage(message);
				} else {
					Main.instance.adventure().sender(sender).sendMessage(messages.getMessage("animvanish.help.no_permission"));
				}
			} else {
				Main.instance.adventure().sender(sender).sendMessage(messages.getMessage("invalid_args"));
			}
		} else {
			Main.instance.adventure().sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(messages.getMessage("prefix") + "The plugin version is " + Main.instance.getDescription().getVersion() + "</gold>"));
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
